package com.example.roomdatabasedemo.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasedemo.R
import com.example.roomdatabasedemo.diffutil.MyDiffUtil
import com.example.roomdatabasedemo.model.User
import kotlinx.android.synthetic.main.custom_layout.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var userList = emptyList<User>()

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.txt_id.text = currentItem.id.toString()
        holder.itemView.txt_first_name.text = currentItem.firstName
        holder.itemView.txt_last_name.text = currentItem.lastName
        holder.itemView.txt_age.text = currentItem.age.toString()

        holder.itemView.rowLayout.setOnClickListener {
            var action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
    fun setdata(user : List<User>){
//        this.userList = user
//        notifyDataSetChanged()
        val diffUtil =MyDiffUtil(userList,user)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        userList = user
        diffResult.dispatchUpdatesTo(this)
    }
}