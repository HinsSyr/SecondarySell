package com.example.secondarysell

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_myprofile.*
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.example.hw6.DatabaseHelper


class myprofile : AppCompatActivity(),profiletext.OnFragmentInteractionListener,profileedit.OnFragmentInteractionListener {

    var messagefrom_main:UserData ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)

        messagefrom_main=intent.getSerializableExtra("sendusertoprofile") as UserData


        setSupportActionBar(profiletoolbar)
        val appbar=supportActionBar
        appbar!!.title="Account Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Log.i("userinfo",messagefrom_main!!.username+messagefrom_main!!.password+messagefrom_main!!.firstname+messagefrom_main!!.lastname+messagefrom_main!!.email)

        val fg=profiletext.newInstance(messagefrom_main!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_profile,fg)
        transaction.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                val intent = Intent()
                intent.putExtra("profile_back",messagefrom_main )
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEditFragmentInteraction(view: View) {
        val fg=profileedit.newInstance(messagefrom_main!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_profile,fg)
        transaction.commit()
    }

    override fun onSAVEFragmentInteraction(newuser: UserData) {
        val myDB: DatabaseHelper = DatabaseHelper(this)
        myDB.updateUser(newuser)
        myDB.closeDB()
        messagefrom_main=newuser
        val fg=profiletext.newInstance(messagefrom_main!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_profile,fg)
        transaction.commit()
    }

    override fun onCANCELFragmentInteraction()
    {
        val fg=profiletext.newInstance(messagefrom_main!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_profile,fg)
        transaction.commit()
    }
}
