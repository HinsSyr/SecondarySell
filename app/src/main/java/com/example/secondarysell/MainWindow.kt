package com.example.secondarysell

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_window.*

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hw6.DatabaseHelper
import com.google.android.material.navigation.NavigationView


class MainWindow : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    ListView.OnFragmentInteractionListener,
    Mysellings.OnFragmentInteractionListener,
    editgood.OnFragmentInteractionListener{

    var currentfrag:ListView?=null
    val fg=ListView.newInstance("Front fragment is created","value 2")
    var message:UserData?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_window)

        message= intent.getSerializableExtra("1") as UserData

        setSupportActionBar(toolbar)
        val appbar=supportActionBar
        appbar!!.setDisplayShowTitleEnabled(false)

      //  setSupportActionBar(toolbar)
        val toggle =
            ActionBarDrawerToggle(this, mainAct, toolbar, R.string.log_in, R.string.sign_up)
        mainAct.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        navView.getHeaderView(0).findViewById<TextView>(R.id.account_email).text=message!!.email
        navView.getHeaderView(0).findViewById<TextView>(R.id.account_flname).text=message!!.firstname+ " "+ message!!.lastname


        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.meContainer,fg)
        transaction.commit()
        currentfrag=fg



    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
// inflate the menu into toolbar
        val inflater = menuInflater
        inflater.inflate(R.menu.fragmenu, menu)

        val search = menu.findItem(R.id.action_search).actionView as SearchView
        var rview1= findViewById<RecyclerView>(R.id.rcview1)
        if ( search != null ){
            search.setOnQueryTextListener( object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val pos = currentfrag!!.myAdapter!!.findFirst( query!!)
                    if (pos >= 0) {
                        rview1.smoothScrollToPosition(pos)
                    } else {
                        rview1.smoothScrollToPosition(0)
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })

        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            if (data != null) {
                message = data.getSerializableExtra("profile_back") as UserData
                navView.getHeaderView(0).findViewById<TextView>(R.id.account_email).text=message!!.email
                navView.getHeaderView(0).findViewById<TextView>(R.id.account_flname).text=message!!.firstname+ " "+ message!!.lastname
            }
        }

        if (resultCode == Activity.RESULT_OK && requestCode == 101) {
            if (data != null) {
                message = data.getSerializableExtra("cartsendtomain") as UserData
                navView.getHeaderView(0).findViewById<TextView>(R.id.account_email).text=message!!.email
                navView.getHeaderView(0).findViewById<TextView>(R.id.account_flname).text=message!!.firstname+ " "+ message!!.lastname
                fg.myAdapter!!.readallgoods()
                fg.myAdapter!!.notifyDataSetChanged()
            }
        }

        if (resultCode == Activity.RESULT_OK && requestCode == 102) {
            if (data != null) {

            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 103) {
            if (data != null) {

            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.cart->{
                var intent=Intent(this,mycart::class.java).apply {
                    putExtra("mainsendtocart",message)

                }
                startActivityForResult(intent,101)
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean { // handler navigation menu item selection!
        when (item.itemId) {
            R.id.nav_AboutMe -> {

                var intent=Intent(this,myprofile::class.java).apply {
                                 putExtra("sendusertoprofile",message)
                }
                startActivityForResult(intent, 100)
            }
            R.id.nav_buy -> {

             //   val fg=ListView.newInstance("Front fragment is created","value 2")
                //     val mfg = movie.newInstance("aaa","bbb")
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.meContainer,fg)
                transaction.commit()
                currentfrag=fg
            }
            R.id.nav_sell -> {
                val fg=Mysellings.newInstance(message!!)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.meContainer,fg)
                transaction.commit()
            }
            R.id.nav_order -> {
                var intent=Intent(this,myorder::class.java).apply {
                    putExtra("mainsendtoorder",message)
                }
                startActivityForResult(intent,103)

            }
            R.id.nav_notification -> {

            }
            R.id.nav_logout -> {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_share, R.id.nav_send -> { // “one of many” value check
                Toast.makeText(
                    this,
                    "Communication Item (either Share or Send) Selected",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        mainAct.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(mainAct.isDrawerOpen(GravityCompat.START)){
            mainAct.closeDrawer(GravityCompat.START)
        }
        else
            super.onBackPressed()
    }

    override fun onGoodClicked(good: GoodsData) {
        var intent=Intent(this,ItemDetail::class.java).apply {
            putExtra("mainsendtodetail_good",good)
            putExtra("mainsendtodetail_user",message!!)
        }
        startActivityForResult(intent,102)
    }

    override fun onADDFragmentInteraction() {
        val fg=editgood.newInstance(message!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.meContainer,fg)
        transaction.commit()
    }

    override fun onDELETEFragmentInteraction(goods:ArrayList<GoodsData>) {
        val myDB: DatabaseHelper = DatabaseHelper(this)
            myDB.deletegoods(goods)
        val fg=Mysellings.newInstance(message!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.meContainer,fg)
        transaction.commit()
        myDB.closeDB()
    }

    override fun onSUBMITFragmentInteraction(good: GoodsData) {
        val myDB: DatabaseHelper = DatabaseHelper(this)
        myDB.addgoodsdb(good)
        val fg=Mysellings.newInstance(message!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.meContainer,fg)
        transaction.commit()

        myDB.closeDB()
    }

    override fun onCANCELragmentInteraction() {
        val fg=Mysellings.newInstance(message!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.meContainer,fg)
        transaction.commit()
    }
}