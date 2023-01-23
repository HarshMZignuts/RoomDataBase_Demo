package com.example.roomdatabasedemo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabasedemo.R
import com.example.roomdatabasedemo.model.User
import com.example.roomdatabasedemo.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mUserViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.et_update_first_name.setText(args.currentuser.firstName)
        view.et_update_last_name.setText(args.currentuser.lastName)
        view.et_update_age.setText(args.currentuser.age.toString())
        view.btn_update.setOnClickListener {
            updateItem()
        }
        setHasOptionsMenu(true)
        return view
    }

  private fun updateItem()
  {
      val firstname = et_update_first_name.text.toString()
      val lastname = et_update_last_name.text.toString()
      val age = Integer.parseInt(et_update_age.text.toString())

      if (inputCheck(firstname,lastname,et_update_age.text)){

            val updateuser = User(args.currentuser.id,firstname,lastname,age)

            mUserViewModel.updateUser(updateuser)

          findNavController().navigate(R.id.action_updateFragment_to_listFragment)
          Toast.makeText(requireContext(),"Update Successfully",Toast.LENGTH_LONG).show()

      }
      else
      {
          Toast.makeText(requireContext(),"Please fill out All Details",Toast.LENGTH_LONG).show()
      }
  }

    private fun inputCheck(firstname:String,lastname:String,age: Editable):Boolean{

        return !(TextUtils.isEmpty(firstname) && TextUtils.isEmpty(lastname) && age.isEmpty())

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_delete)
        {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _->
            mUserViewModel.deleteUser(args.currentuser)
            Toast.makeText(requireContext(),"Successfully Deleted ${args.currentuser.firstName}",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_, _->}
        builder.setTitle("Delete ${args.currentuser.firstName}?")
        builder.setMessage("Are you Sure You want Delete ${args.currentuser.firstName}?")
        builder.create().show()
    }

}