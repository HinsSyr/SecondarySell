package com.example.secondarysell

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class UserData(
    @SerializedName("username") var username: String?,
    @SerializedName("password")val password: Int?,
    @SerializedName("firstname")val firstname: String?,
    @SerializedName("lastname")val lastname:String?,
    @SerializedName("email")val email:String?
   // @SerializedName("db_id") var db_id: Int = -1
) :Serializable

