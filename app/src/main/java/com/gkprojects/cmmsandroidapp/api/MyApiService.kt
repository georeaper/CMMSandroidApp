package com.gkprojects.cmmsandroidapp.api


import com.gkprojects.cmmsandroidapp.DataClasses.Response
import retrofit2.Call
import retrofit2.http.GET

interface MyApiService {
    @GET("authenticate.php")
    fun authenticate(): Call<Response>
}