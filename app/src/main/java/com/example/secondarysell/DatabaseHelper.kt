package com.example.hw6

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.secondarysell.GoodsData
import com.example.secondarysell.UserData
import kotlin.math.log


class DatabaseHelper(context: Context,var user:UserData?=null): SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    private val storedcontext=context

    companion object {
        private val DB_NAME = "app.db"
        private val DB_VER = 1

        private val COL_ID = "id"
        private val COL_TITLE = "title"
        private val COL_PRICE = "price"
        private val COL_OWNER ="owner"
        private val COL_IMAGEID ="imageid"
        private val COL_OVERVIEW = "overview"


        private val COL_USERNAME = "username"
        private val COL_PASSWORD = "password"
        private val COL_FIRSTNAME = "firstname"
        private val COL_LASTNAME = "lastname"
        private val COL_EMAIL = "email"



        private val COL_GOODSID ="goodsid"
        private val COL_USERNAMECART="usernamecart"


        // create table users
        private val CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS users " +
                "( $COL_USERNAME TEXT,  $COL_PASSWORD INTEGER, " +
                "$COL_FIRSTNAME TEXT,  $COL_LASTNAME TEXT, $COL_EMAIL TEXT)"

        private val DROP_TABLE_USERS = "DROP TABLE IF EXISTS users"

        // create table goods
        private val CREATE_TABLE_GOODS="CREATE TABLE IF NOT EXISTS goods " +
                "( $COL_ID INTEGER PRIMARY KEY,  $COL_TITLE TEXT, " +
                "$COL_PRICE REAL,  $COL_OWNER TEXT,$COL_IMAGEID INTEGER, $COL_OVERVIEW TEXT)"
        private val DROP_TABLE_GOODS= "DROP TABLE IF EXISTS goods"

        //create table userscart
        private val CREATE_TABLE_USERSCART="CREATE TABLE IF NOT EXISTS carts " +
                "( $COL_GOODSID INTEGER,  $COL_USERNAMECART TEXT)"
        private val DROP_TABLE_CARTS = "DROP TABLE IF EXISTS carts"

        //create table orders
        private val CREATE_TABLE_ORDERS="CREATE TABLE IF NOT EXISTS orders " +
                "( $COL_ID INTEGER PRIMARY KEY,  $COL_TITLE TEXT, " +
                "$COL_PRICE REAL,  $COL_OWNER TEXT,$COL_IMAGEID INTEGER, $COL_OVERVIEW TEXT)"
        private val DROP_TABLE_ORDERS = "DROP TABLE IF EXISTS orders"

    }

    fun resetall()
    {
        val db = this.writableDatabase
        db?.execSQL(DROP_TABLE_USERS)
        db?.execSQL(DROP_TABLE_GOODS)
        db?.execSQL(DROP_TABLE_CARTS)
        db?.execSQL(DROP_TABLE_ORDERS)
        db?.execSQL(CREATE_TABLE_USERS)
        db?.execSQL(CREATE_TABLE_GOODS)
        db?.execSQL(CREATE_TABLE_USERSCART)
        db?.execSQL(CREATE_TABLE_ORDERS)
    }

    fun closeDB(){
        val db = this.readableDatabase
        if(db != null && db.isOpen)
            db.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USERS)
        db?.execSQL(CREATE_TABLE_GOODS)
        db?.execSQL(CREATE_TABLE_USERSCART)
        db?.execSQL(CREATE_TABLE_ORDERS)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //db?.execSQL(DROP_TABLE_USERS)
    }

     fun search(col_name:String,search_value:String):Boolean
    {
        val query = "SELECT * FROM users WHERE $col_name=\"$search_value\""
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)

        c.moveToNext()
        if(!c.isAfterLast)
        {
            return true
        }
        return false
    }

    fun verifyaccount(input_username:String,input_password:Int):Boolean {

        if (search("username", input_username)) {
            val query = "SELECT password FROM users WHERE username=\"$input_username\""
            val db = this.readableDatabase
            val c = db.rawQuery(query, null)

            c.moveToNext()

            val db_password = c.getInt(c.getColumnIndex(COL_PASSWORD))
            Log.i("index of cursor", db_password.toString())

            if(db_password==input_password)
            {
                return true
            }
            else
            {
                Toast.makeText(storedcontext,"password isn't correct",Toast.LENGTH_LONG).show()
                return false
            }
        }
        else {
            Toast.makeText(storedcontext,"account doesn't exist",Toast.LENGTH_LONG).show()
            return false
        }
    }



    fun getuser(input_username:String):UserData {

            val query = "SELECT * FROM users WHERE username=\"$input_username\""
            val db = this.readableDatabase
            val c = db.rawQuery(query, null)

            c.moveToNext()

            val user = UserData(
                username = c.getString(c.getColumnIndex(COL_USERNAME)),
                password = c.getInt(c.getColumnIndex(COL_PASSWORD)),
                firstname = c.getString(c.getColumnIndex(COL_FIRSTNAME)),
                lastname = c.getString(c.getColumnIndex(COL_LASTNAME)),
                email = c.getString(c.getColumnIndex(COL_EMAIL))
            )
            return user
    }


    fun addUser(user: UserData): Long{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_USERNAME, user.username)
        values.put(COL_PASSWORD, user.password)
        values.put(COL_FIRSTNAME, user.firstname)
        values.put(COL_LASTNAME, user.lastname)
        values.put(COL_EMAIL, user.email)

        return db.insert("users", null, values)
    }

    fun updateUser(user:UserData){
        val db = this.writableDatabase
        val newcontent=ContentValues()
        val temp=user.username
        newcontent.put(COL_PASSWORD,user.password)
        newcontent.put(COL_FIRSTNAME,user.firstname)
        newcontent.put(COL_LASTNAME,user.lastname)
        newcontent.put(COL_EMAIL,user.email)
        val args = arrayOf<String>(temp!!)
        db.update("users",newcontent,"username=?",args)
    }

    fun creategoodsdb(inputgoods:ArrayList<GoodsData>)
    {
        val db = this.writableDatabase
        val values = ContentValues()

        for (i in inputgoods.indices) {

            values.put(COL_TITLE,inputgoods[i].title)
            values.put(COL_ID, inputgoods[i].id)
            values.put(COL_PRICE, inputgoods[i].price)
            values.put(COL_OWNER, inputgoods[i].owner)
            values.put(COL_IMAGEID, inputgoods[i].imageid)
            values.put(COL_OVERVIEW, inputgoods[i].overview)
            db.insert("goods", null, values)
        }
    }

    fun getAllGoods():ArrayList<GoodsData>? {

        var goods_from_db:ArrayList<GoodsData>?= ArrayList()
        val query = "SELECT * FROM goods"
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)

        c.moveToNext()
        while(!c.isAfterLast)
        {
            Log.i("index of cursor",c.position.toString())
            val good = GoodsData(

                id = c.getInt(c.getColumnIndex(COL_ID)),
                title = c.getString(c.getColumnIndex(COL_TITLE)),
                price = c.getDouble(c.getColumnIndex(COL_PRICE)),
                owner = c.getString(c.getColumnIndex(COL_OWNER)),
                imageid = c.getInt(c.getColumnIndex(COL_IMAGEID)),
                overview = c.getString(c.getColumnIndex(COL_OVERVIEW))
            )

            goods_from_db?.add(good)
            c.moveToNext()
        }
        return goods_from_db
    }

    fun getMysellings(user:UserData):ArrayList<GoodsData>
    {
        var goods_from_db:ArrayList<GoodsData>?= ArrayList()
        var username=user.username
        val query = "SELECT * FROM goods WHERE owner=\"$username\""
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)

        c.moveToNext()
        while(!c.isAfterLast)
        {
            Log.i("index of cursor",c.position.toString())
            val good = GoodsData(

                id = c.getInt(c.getColumnIndex(COL_ID)),
                title = c.getString(c.getColumnIndex(COL_TITLE)),
                price = c.getDouble(c.getColumnIndex(COL_PRICE)),
                owner = c.getString(c.getColumnIndex(COL_OWNER)),
                imageid = c.getInt(c.getColumnIndex(COL_IMAGEID)),
                overview = c.getString(c.getColumnIndex(COL_OVERVIEW))
            )

            goods_from_db?.add(good)
            c.moveToNext()
        }
        return goods_from_db!!

    }

    fun addgoodsdb(good:GoodsData)
    {
        val db = this.writableDatabase
        val values = ContentValues()

            values.put(COL_TITLE,good.title)
            values.put(COL_ID,getmaxid()+1 )
            values.put(COL_PRICE, good.price)
            values.put(COL_OWNER, good.owner)
            values.put(COL_IMAGEID, good.imageid)
            values.put(COL_OVERVIEW, good.overview)
            db.insert("goods", null, values)

    }

    fun getmaxid():Int {

        val query = "SELECT MAX(ID) FROM goods"
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)

        c.moveToNext()

        val result = c.getInt(c.getColumnIndex("MAX(ID)"))

        return result
    }
    fun deletegoods(goods:ArrayList<GoodsData>)
    {
        val db = this.readableDatabase
        for(good in goods) {
            val args = arrayOf<String>(good.id.toString())
            db.delete("goods", "id=?", args)
        }
    }

    fun addtocart(user:UserData,good: GoodsData)
    {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_GOODSID,good.id)
        values.put(COL_USERNAMECART,user.username )
        if(!searchcart(user,good)) {
            db.insert("carts", null, values)
        }
    }

    fun searchcart(user:UserData,good: GoodsData):Boolean
    {
        val id=good.id
        val un=user.username
        val query = "SELECT * FROM carts WHERE $COL_GOODSID=\"$id\" AND $COL_USERNAMECART=\"$un\""
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)

        c.moveToNext()
        if(!c.isAfterLast)
        {
            return true
        }
        return false
    }

    fun getmycartgoods(user:UserData):ArrayList<GoodsData>
    {
        var goods_from_db:ArrayList<GoodsData>?= ArrayList()
        var ids:ArrayList<Int>?=ArrayList()
        var username=user.username
        val query = "SELECT * FROM carts WHERE $COL_USERNAMECART=\"$username\""
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)
        c.moveToNext()
        while(!c.isAfterLast)
        {
            Log.i("index of cursor",c.position.toString())
            val id=c.getInt(c.getColumnIndex(COL_GOODSID))
            ids?.add(id)
            c.moveToNext()
        }

        for(tempid in ids!!) {
            val query2 = "SELECT * FROM goods WHERE $COL_ID=\"$tempid\""
            val db2 = this.readableDatabase
            val c2 = db.rawQuery(query2, null)
                    c2.moveToNext()
                while(!c2.isAfterLast)
                {
                    Log.i("index of cursor",c2.position.toString())
                    val good = GoodsData(
                        id = c2.getInt(c2.getColumnIndex(COL_ID)),
                        title = c2.getString(c2.getColumnIndex(COL_TITLE)),
                        price = c2.getDouble(c2.getColumnIndex(COL_PRICE)),
                        owner = c2.getString(c2.getColumnIndex(COL_OWNER)),
                        imageid = c2.getInt(c2.getColumnIndex(COL_IMAGEID)),
                        overview = c2.getString(c2.getColumnIndex(COL_OVERVIEW))
                    )

                    goods_from_db?.add(good)
                    c2.moveToNext()
                }
        }
        return goods_from_db!!
    }

    fun deletecarts(goods:ArrayList<GoodsData>)
    {
        val db = this.readableDatabase
        for(good in goods) {
            val args = arrayOf<String>(good.id.toString())
            db.delete("carts", "goodsid=?", args)
        }
    }

    fun addgoodtoorders(user:UserData,goods:ArrayList<GoodsData>)
    {
        val db = this.writableDatabase
        val values = ContentValues()

        for(good in goods) {
            values.put(COL_TITLE, good.title)
            values.put(COL_ID, good.id)
            values.put(COL_PRICE, good.price)
            values.put(COL_OWNER, user.username)
            values.put(COL_IMAGEID, good.imageid)
            values.put(COL_OVERVIEW, good.overview)
            db.insert("orders", null, values)
        }


    }

    fun getAllorders(user:UserData):ArrayList<GoodsData>? {

        var goods_from_db:ArrayList<GoodsData>?= ArrayList()
        var un=user.username
        val query = "SELECT * FROM orders WHERE owner=\"$un\""
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)

        c.moveToNext()
        while(!c.isAfterLast)
        {
            Log.i("index of cursor",c.position.toString())
            val good = GoodsData(

                id = c.getInt(c.getColumnIndex(COL_ID)),
                title = c.getString(c.getColumnIndex(COL_TITLE)),
                price = c.getDouble(c.getColumnIndex(COL_PRICE)),
                owner = c.getString(c.getColumnIndex(COL_OWNER)),
                imageid = c.getInt(c.getColumnIndex(COL_IMAGEID)),
                overview = c.getString(c.getColumnIndex(COL_OVERVIEW))
            )

            goods_from_db?.add(good)
            c.moveToNext()
        }
        return goods_from_db
    }



}