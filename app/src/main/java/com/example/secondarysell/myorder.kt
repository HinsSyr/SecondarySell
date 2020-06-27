package com.example.secondarysell

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_myorder.*

class myorder : AppCompatActivity(),GoodsListAdapater.MyItemClickListener {

    var messagefrom_main:UserData?=null
    var myAdapter:GoodsListAdapater?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myorder)

        messagefrom_main=intent.getSerializableExtra("mainsendtoorder") as UserData

        setSupportActionBar(ordertoolbar)
        val appbar=supportActionBar
        appbar!!.title="My Order"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        myAdapter= GoodsListAdapater(this,messagefrom_main)
        rcview4.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 1)

        myAdapter!!.getitemsfromorder()
        myAdapter!!.setMyItemClickListener(this)

        rcview4.addItemDecoration(
            ListPaddingDecoration(this,0,0)
        )
        rcview4.adapter = myAdapter

        rcview4.itemAnimator?.addDuration = 1000L
        rcview4.itemAnimator?.removeDuration = 1000L
        rcview4.itemAnimator?.moveDuration = 1000L
        rcview4.itemAnimator?.changeDuration = 1000L

    }

    override fun onItemClickedFromAdapter(good: GoodsData) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                val intent = Intent()
                intent.putExtra("ordersendtomain",messagefrom_main )
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
