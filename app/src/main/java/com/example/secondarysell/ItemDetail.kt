package com.example.secondarysell

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import com.example.hw6.DatabaseHelper
import kotlinx.android.synthetic.main.activity_item_detail.*
import java.net.URI

class ItemDetail : AppCompatActivity(),detail.OnFragmentInteractionListener {

    var messagefrom_main_good:GoodsData?=null
    var messagefrom_main_user:UserData?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        messagefrom_main_user=intent.getSerializableExtra("mainsendtodetail_user") as UserData
        messagefrom_main_good=intent.getSerializableExtra("mainsendtodetail_good") as GoodsData

        setSupportActionBar(detailtoolbar)
        val appbar=supportActionBar

        appbar!!.title=messagefrom_main_good!!.title

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fl=detail.newInstance(messagefrom_main_good!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.detailContainer,fl)
        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                val intent = Intent()
                intent.putExtra("cartsendtomain_good",messagefrom_main_good )
           //     intent.putExtra("cartsendtomain_user",messagefrom_main_user )
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onADDCARTFragmentInteraction() {
        val myDB: DatabaseHelper = DatabaseHelper(this)
        myDB.addtocart(messagefrom_main_user!!,messagefrom_main_good!!)
        myDB.closeDB()
    }


    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detailmenu, menu)

        val shareItem = menu!!.findItem(R.id.action_share)
        val mShareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        val intentShare = Intent(Intent.ACTION_SEND)
        intentShare.type = "text/plain"
        intentShare.putExtra(Intent.EXTRA_TEXT,messagefrom_main_good!!.title)
        if (mShareActionProvider != null && intentShare != null)
            mShareActionProvider.setShareIntent(intentShare)
        return super.onCreateOptionsMenu(menu)
    }
}
