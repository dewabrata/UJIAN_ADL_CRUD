package com.adl.ujiancrud.adapter

import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.adl.ujiancrud.AddUser
import com.adl.ujiancrud.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.activity_add_user.view.*
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*
import kotlin.collections.ArrayList


class UserViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val nama = view.txtNama
    val alamat = view.txtAlamat
    val status = view.txtStatus
    val image = view.photo
    val edit = view.btnEditUser


    fun bindData(adapter:UserAdapter,position:Int){

        nama.setText(adapter.data.get(position).nama)
        var optGender = ((adapter.parent.context.getResources().getStringArray(R.array.gender)));
        var optStatus = ((adapter.parent.context.getResources().getStringArray(R.array.status)));
        alamat.setText("${optGender.get((adapter.data.get(position).gender.toInt())).toString()} / ${adapter.data.get(position).umur}" )
        status.setText(optStatus.get(adapter.data.get(position).status.toInt()).toString())
        image?.let {
            Glide.with(adapter.parent.context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .load(adapter.data.get(position).photo)
                .into(it)


        }

        edit.setOnClickListener({
            val intent = Intent(adapter.parent.context, AddUser::class.java)
            intent.putExtra("data",adapter.data.get(position))
            adapter.parent.context.startActivity(intent)

        })

    }

}