package com.example.roomdatabasedemo.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabasedemo.R

import com.example.roomdatabasedemo.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var mUserViewModel: UserViewModel
    val adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list,container,false)
        //RecyclerView

        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //User View Model
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            user->
            adapter.setdata(user)
        })
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val searchItem = menu.findItem(R.id.menu_search_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(this)


//        searchView.onQueryTextChange {
//            mUserViewModel.searchData(it)
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_search_delete -> {
                deleteAllUser()
            }

        }

        return super.onOptionsItemSelected(item)
    }


    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _->
            mUserViewModel.deleteAll()
            Toast.makeText(requireContext(),"Successfully Deleted All",
                Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("No"){_, _->}
        builder.setTitle("Delete All?")
        builder.setMessage("Are you Sure You want Delete All?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchDatabase(newText)
        }
        return true

    }

    private fun searchDatabase(newText: String) {
        val searchQuery = "%$newText%"
        mUserViewModel.searchDatabase(searchQuery).observe(this,{ user-> user.let {   adapter.setdata(it)} })
    }


}