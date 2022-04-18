package com.adl.ujiancrud

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.adl.ujiancrud.adapter.UserAdapter
import com.adl.ujiancrud.database.UserDatabase
import com.adl.ujiancrud.database.model.UserModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var db:UserDatabase
    var listUser = ArrayList<UserModel>()
    lateinit var userAdapter :UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        reloadList()
        btnAddUser.setOnClickListener({
            val intent = Intent(this@MainActivity,AddUser::class.java)
            result.launch(intent)
        })

    }

    private fun reloadList() {
        GlobalScope.launch {

            listUser = ArrayList(UserDatabase.getInstance(this@MainActivity).userDao().getAll())

            this@MainActivity.runOnUiThread({
                txtJmlUser.setText(listUser.size.toString() +"Users")
                userAdapter = UserAdapter(listUser)
                lstUser.apply{
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = userAdapter
                }
            })


        }
    }


    val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            GlobalScope.launch {
                listUser.clear()
                listUser.addAll(ArrayList(UserDatabase.getInstance(this@MainActivity).userDao().getAll()))

                this@MainActivity.runOnUiThread({
                    txtJmlUser.setText(listUser.size.toString()+ "Users ")
                    userAdapter.notifyDataSetChanged()
                }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        reloadList()
    }
}