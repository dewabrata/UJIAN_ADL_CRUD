package com.adl.ujiancrud.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class UserModel(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val nama:String,
    val gender:String,
    val umur:String,
    val status:String,
    val alamat:String
) :Parcelable
