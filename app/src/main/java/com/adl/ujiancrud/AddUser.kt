package com.adl.ujiancrud

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adl.ujiancrud.database.UserDatabase
import com.adl.ujiancrud.database.model.UserModel
import com.anirudh.locationfetch.EasyLocationFetch
import com.anirudh.locationfetch.GeoLocationModel
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class AddUser : AppCompatActivity() {

    lateinit var data :UserModel
    var isUpdate:Boolean = false
    lateinit var geloc:GeoLocationModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)


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
}