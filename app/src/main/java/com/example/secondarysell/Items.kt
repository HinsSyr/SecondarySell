package com.example.secondarysell
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class GoodsData(
    @SerializedName("title") var title: String?,
    @SerializedName("id")var id: Int=-1,
    @SerializedName("overview")val overview: String?,
    @SerializedName("price")val price:Double?,
    @SerializedName("imageid")var imageid:Int=-1,
    @SerializedName("owner")val owner:String?,
    @SerializedName("checked") var checked:Boolean=false
) :Serializable


class Items {
    val goods: ArrayList<GoodsData> = ArrayList()
    val posterTable: MutableMap<String, Int> = mutableMapOf()
    init{
        linkimage()
        addGoods()
    }
    private fun addGoods(){
        goods.add(GoodsData("bowl",1,"this is a bowl",100000.00,posterTable["bowl"]!!,"boby"))
        goods.add(GoodsData("chair",2,"this is a chair",99999.00,posterTable["chair"]!!,"bqiu03"))
        goods.add(GoodsData("hotwatercooker",3,"this is a hotwatercooker",88888.00,posterTable["hotwatercooker"]!!,"bqiu03"))
        goods.add(GoodsData("light",4,"this is a light",77777.00,posterTable["light"]!!,"boby"))
        goods.add(GoodsData("microwaveoven",5,"this is a microwaveoven",66666.00,posterTable["microwaveoven"]!!,"bqiu03"))
        goods.add(GoodsData("pan",6,"this is a pan",55555.00,posterTable["pan"]!!,"boby"))
        goods.add(GoodsData("ricecooker",7,"this is a ricecooker",44444.00,posterTable["ricecooker"]!!,"bqiu03"))
        goods.add(GoodsData("trashbin",8,"this is a trashbin",33333.00,posterTable["trashbin"]!!,"boby"))
    }
    private fun linkimage()
    {
        posterTable["bowl"]=R.drawable.bowl
        posterTable["chair"]=R.drawable.chair
        posterTable["hotwatercooker"]=R.drawable.hotwatercooker
        posterTable["light"]=R.drawable.light
        posterTable["microwaveoven"]=R.drawable.microwaveoven
        posterTable["pan"]=R.drawable.pan
        posterTable["ricecooker"]=R.drawable.ricecooker
        posterTable["trashbin"]=R.drawable.trashbin
    }
}