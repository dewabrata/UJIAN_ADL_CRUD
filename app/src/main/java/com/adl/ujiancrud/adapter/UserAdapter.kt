package com.adl.ujiancrud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adl.ujiancrud.R
import com.adl.ujiancrud.database.model.UserModel

class UserAdapter(val data:ArrayList<UserModel>):RecyclerView.Adapter<UserViewHolder>() {
    lateinit var parent:ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        this.parent = parent

        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.bindData(this@UserAdapter,position)
    }

    override fun getItemCount(): Int {
      return data.size
    }
}