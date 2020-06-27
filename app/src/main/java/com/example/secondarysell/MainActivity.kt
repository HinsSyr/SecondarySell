package com.example.secondarysell

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hw6.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnlogin.setOnClickListener {
            val myDB: DatabaseHelper = DatabaseHelper(this)
            val username=loginusername.text.toString()
            val strpassword=loginpasswd.text.toString()
            if(strpassword !="") {
                val intpassword = Integer.parseInt(strpassword)
                if(myDB.verifyaccount(username,intpassword)) {
                    val user =myDB.getuser(username)
                    val intent= Intent(this,MainWindow::class.java).apply {
                        putExtra("1",user)
                    }
                   // Log.i("userinfo",user.username+user.password+user.firstname+user.lastname+user.email)
                    startActivity(intent)
                }
            }
            else
                Toast.makeText(this,"Please input your password",Toast.LENGTH_LONG).show()

            myDB.closeDB()
        }

        btnclickhere.setOnClickListener {
            val intent =Intent(this,Forgetpassword::class.java)
            startActivity(intent)

        }
        btnsignup.setOnClickListener {
            val intent =Intent(this,Signup::class.java)
            startActivity(intent)
        }

        btnsecretentry.setOnClickListener {
            val myDB: DatabaseHelper = DatabaseHelper(this)
            myDB.resetall()
            var item=Items()
            myDB.creategoodsdb(item.goods)
            var user1=UserData("bqiu03",123456,"BO","QIU","bqiu03@syr.edu")
            var user2=UserData("boby",123456,"KEVIN","DU","kevindu@syr.edu")
            var user3=UserData("anna",123456,"QINRU","QIU","qinrq@syr.edu")
            myDB.addUser(user1)
            myDB.addUser(user2)
            myDB.addUser(user3)

            myDB.closeDB()
        }
    }
}
