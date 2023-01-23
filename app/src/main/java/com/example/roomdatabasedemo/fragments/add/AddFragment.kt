package com.example.roomdatabasedemo.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabasedemo.R
import com.example.roomdatabasedemo.model.User
import com.example.roomdatabasedemo.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.btn_add.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        var firstname = et_first_name.text.toString()
        var lastname =  et_last_name.text.toString()
        var age =  et_age.text
        if (inputCheck(firstname,lastname,age))
        {
            //create user object
            val user = User(0,firstname,lastname,Integer.parseInt(age.toString()))

            //add data in database
            mUserViewModel.addUser(user)

            Toast.makeText(requireContext(),"Successfully Added",Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)



        }
        else{
            Toast.makeText(requireContext(),"Please fill all details",Toast.LENGTH_LONG).show()


        }
    }

    private fun inputCheck(firstname:String,lastname:String,age:Editable):Boolean{

        return !(TextUtils.isEmpty(firstname) && TextUtils.isEmpty(lastname) && age.isEmpty())

    }


}