package com.example.secondarysell

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.hw6.DatabaseHelper
import kotlinx.android.synthetic.main.activity_mycart.*

class mycart : AppCompatActivity(),GoodsListAdapater.MyItemClickListener {

    var messagefrom_main:UserData?=null
    var myAdapter:GoodsListAdapater?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycart)

        messagefrom_main=intent.getSerializableExtra("mainsendtocart") as UserData



        setSupportActionBar(carttoolbar)
        val appbar=supportActionBar
        appbar!!.title="My Cart"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        myAdapter= GoodsListAdapater(this,messagefrom_main,1)
        rcview3.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 1)

        myAdapter!!.getitemsfromcart()
        myAdapter!!.setMyItemClickListener(this)

        rcview3.addItemDecoration(
            ListPaddingDecoration(this,0,0)
        )
        rcview3.adapter = myAdapter

        rcview3.itemAnimator?.addDuration = 1000L
        rcview3.itemAnimator?.removeDuration = 1000L
        rcview3.itemAnimator?.moveDuration = 1000L
        rcview3.itemAnimator?.changeDuration = 1000L

        btncartbuy.setOnClickListener {
            val myDB: DatabaseHelper = DatabaseHelper(this)
            val selecteditems=myAdapter!!.selectedgoods()
            myDB.addgoodtoorders(messagefrom_main!!,selecteditems)
            myDB.deletecarts(myAdapter!!.selectedgoods())
            myDB.deletegoods(selecteditems)
            myAdapter!!.getitemsfromcart()
            myAdapter!!.notifyDataSetChanged()
            myDB.closeDB()
        }

        btncartremove.setOnClickListener {
            val myDB: DatabaseHelper = DatabaseHelper(this)
            myDB.deletecarts(myAdapter!!.selectedgoods())
            myAdapter!!.getitemsfromcart()
            myAdapter!!.notifyDataSetChanged()
            myDB.closeDB()
        }
    }

    override fun onItemClickedFromAdapter(good: GoodsData) {

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                val intent = Intent()
                intent.putExtra("cartsendtomain",messagefrom_main )
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
