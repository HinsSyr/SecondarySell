package com.example.secondarysell

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.example.hw6.DatabaseHelper
import kotlinx.android.synthetic.main.goodslistview.view.*
import kotlinx.android.synthetic.main.goodslistview.view.rvOverview
import kotlinx.android.synthetic.main.goodslistview.view.rvOwner
import kotlinx.android.synthetic.main.goodslistview.view.rvPoster
import kotlinx.android.synthetic.main.goodslistview.view.rvTitle
import kotlinx.android.synthetic.main.goodslistview.view.rvprice
import kotlinx.android.synthetic.main.goodslistview2.view.*


class GoodsListAdapater( val context: Context,var user:UserData?=null,var type:Int?=-1) :
    androidx.recyclerview.widget.RecyclerView.Adapter<GoodsListAdapater.ViewHolder>() {


    var myListener: MyItemClickListener? = null
    var lastPosition = -1
    val myDB:DatabaseHelper= DatabaseHelper(context,user)

    lateinit var goods: ArrayList<GoodsData>




    interface MyItemClickListener {
        fun onItemClickedFromAdapter(good: GoodsData)
      //  fun onItemLongClickedFromAdapter(position: Int)
      //  fun onOverflowMenuClickedFromAdapter(view: View, position: Int)
    }

    fun setMyItemClickListener(listener: MyItemClickListener) {
        this.myListener = listener
    }

    fun readallgoods():ArrayList<GoodsData>
    {
        goods=this.myDB.getAllGoods()!!
        return goods
    }

    fun getmysellinggoods():ArrayList<GoodsData>
    {
        goods=this.myDB.getMysellings(user!!)
        return goods

    }
    fun getitemsfromcart():ArrayList<GoodsData>
    {
        goods=this.myDB.getmycartgoods(user!!)
        return goods
    }

    fun getitemsfromorder():ArrayList<GoodsData>
    {
        goods=this.myDB.getAllorders(user!!)!!
        return goods
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        var view: View?

        when(type)
        {
        1-> {
            val layoutInflater = LayoutInflater.from(parent.context)
            view = layoutInflater.inflate(R.layout.goodslistview2, parent, false)

        }
            else->{
                val layoutInflater = LayoutInflater.from(parent.context)
                view = layoutInflater.inflate(R.layout.goodslistview, parent, false)
            }}
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return goods.size
    }

    fun getItem(index: Int): Any {
        return goods[index]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    fun sortbytitle() {
        goods.sortBy { it.title }
        notifyDataSetChanged()
    }

    fun sortbyprice() {
        goods.sortBy { it.price }
        notifyDataSetChanged()
    }

    fun selectedgoods():ArrayList<GoodsData>{
        var temp=ArrayList<GoodsData>()
        for (good in goods) {
                if (good.checked) {
                    temp.add(good)
                }

        }
    return temp
    }
    fun findFirst(query: String): Int {
        var res = 0
        for (i in 0 until goods.size) {
            if (goods[i].title!!.contains(query, ignoreCase = true)) {
                res = i

                return res
            }
        }
        return res
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val good = goods[position]

        var overview = good.overview

        if (overview!!.length > 200)
            overview = overview!!.substring(0, 200) + "..."

        holder.txtowner?.text= "Owner:"+ " "+ good.owner
        holder.txttitle?.text = "Title:"+ " " +good.title
        holder.txtover?.text = "Description:"+" "+overview
        if(good.imageid!=-1) {
            holder.postimage!!.setImageResource(good.imageid!!)
        }
        holder.txtprice!!.text = "Price:"+" "+good.price.toString()+"$"
        setAnimation(holder.postimage!!, position)
        if(type==1) {
            holder.chebox!!.isChecked = good.checked!!
        }

    }

    private fun setAnimation(view: View, position: Int) {
        if (position != lastPosition) {
            when (getItemViewType(position)) {
                1 -> {
                    val animation =
                        AnimationUtils.loadAnimation(view.context, android.R.anim.slide_in_left)
                    animation.duration = 700
                    animation.startOffset = position * 100L
                    view.startAnimation(animation)
                }
                2 -> {
                    val animation = AlphaAnimation(0.0f, 1.0f)
                    animation.duration = 1000
                    animation.startOffset = position * 50L
                    view.startAnimation(animation)
                }
                else -> {
                    val animation = ScaleAnimation(
                        0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                    )
                    animation.duration = 500
                    animation.startOffset = position * 200L
                    view.startAnimation(animation)
                }
            }
//animation.startOffset = position * 100L
            lastPosition = position
        }
    }


    inner class ViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        var txttitle: TextView?
        var postimage: ImageView?
        var txtover: TextView?
        var txtprice: TextView?
        var txtowner: TextView?
        var chebox: CheckBox?=null

        init {

            postimage = itemView.rvPoster
            txttitle = itemView.rvTitle
            txtprice = itemView.rvprice
            txtover = itemView.rvOverview
            txtowner=itemView.rvOwner

            if(type==1) {
                chebox = itemView.rvChx
                chebox!!.setOnCheckedChangeListener { buttonView, isChecked ->
                    goods[adapterPosition].checked = isChecked
                }
            }


            itemView.setOnClickListener {
                if (myListener != null) {
                    if (adapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
                        myListener!!.onItemClickedFromAdapter(goods[adapterPosition])
                    }
                }
            }



        }
    }


}
