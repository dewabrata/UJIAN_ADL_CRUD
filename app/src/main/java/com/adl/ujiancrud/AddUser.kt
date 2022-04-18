package com.adl.ujiancrud

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adl.ujiancrud.database.UserDatabase
import com.adl.ujiancrud.database.model.UserModel
import com.anirudh.locationfetch.EasyLocationFetch
import com.anirudh.locationfetch.GeoLocationModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lib.android.imagepicker.ImagePicker
import java.util.*

class AddUser : AppCompatActivity(),ImagePicker.OnImageSelectedListener {

    lateinit var data :UserModel
    var isUpdate:Boolean = false
    lateinit var geloc:GeoLocationModel
    lateinit var imagePicker:ImagePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        imagePicker = ImagePicker(this@AddUser,BuildConfig.APPLICATION_ID)
        imagePicker.setImageSelectedListener(this@AddUser)


        btnCamera.setOnClickListener({
            imagePicker.takePhotoFromCamera()
        })



        geloc = EasyLocationFetch(this@AddUser).getLocationData();
      //  txtGPS.setText("${geloc.lattitude.toString()} - ${geloc.lattitude.toString()}" )
        txtGPS.setText("${geloc.address.toString()} " )



        if(intent.hasExtra("data")) {
            data = intent.getParcelableExtra("data")!!
            isUpdate = true
            setUIWithModel(data)

        }

        btnSendData.setOnClickListener({

            GlobalScope.launch {
                var model: UserModel
                if(!isUpdate){
                 model = UserModel(
                    0,
                    txtNamaUser.text.toString(),
                    spnGender.selectedItemPosition.toString(),
                    txtUmur.text.toString(),
                    spnStatus.selectedItemPosition.toString(),
                    txtGPS.text.toString()

                )
                    UserDatabase.getInstance(this@AddUser).userDao().insertUser(model)
                }else{
                     model = UserModel(
                        data.id,
                        txtNamaUser.text.toString(),
                        spnGender.selectedItemPosition.toString(),
                        txtUmur.text.toString(),
                        spnStatus.selectedItemPosition.toString(),
                        txtGPS.text.toString())
                    UserDatabase.getInstance(this@AddUser).userDao().updateUser(model)
                }


                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            }


        })
    }

    private fun setUIWithModel(data: UserModel) {
       // var optGender = getResources().getStringArray(R.array.gender);
      //  var optStatus = getResources().getStringArray(R.array.status);
        txtNamaUser.setText(data.nama)
        txtUmur.setText(data.umur)
        txtGPS.setText(data.alamat)
        spnGender.setSelection(data.gender.toInt())
        spnStatus.setSelection(data.status.toInt())




    }

    override fun onImageSelectFailure() {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    override fun onImageSelectSuccess(imagePath: String) {
        imageView?.let {
            Glide.with(this@AddUser)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .load(imagePath)
                .into(it)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        imagePicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}