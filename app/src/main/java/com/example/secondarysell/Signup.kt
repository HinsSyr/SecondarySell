package com.example.secondarysell

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hw6.DatabaseHelper
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btnsignuplogin.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }



        btnsignup2.setOnClickListener {
            val myDB: DatabaseHelper = DatabaseHelper(this)
            val strpassword = sueditpas.text.toString()
            val intpassword = Integer.parseInt(strpassword)
            val search_username=suedituser.text.toString()

            if(!myDB.search("username",search_username)) {
                var newuser = UserData(
                    suedituser.text.toString(), intpassword, sueditfirst.text.toString(),
                    sueditlast.text.toString(), sueditemail.text.toString()
                )
                // if(sueditpas.text == sueditrepas.text)
                myDB.addUser(newuser)
                Toast.makeText(this,"You have successfully signed up your account",Toast.LENGTH_LONG).show()

                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this,"The username is already exists",Toast.LENGTH_LONG).show()
            }

            myDB.closeDB()
        }
    }
}
