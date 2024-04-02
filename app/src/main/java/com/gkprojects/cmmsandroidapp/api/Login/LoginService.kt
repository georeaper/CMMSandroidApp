package com.gkprojects.cmmsandroidapp.api.Login


import com.gkprojects.cmmsandroidapp.DataClasses.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface LoginService {
    @POST("verifyUser.php")
    fun authenticate(@Body credentials: Credentials): Call<Users>
}
data class Credentials(val usernameOrEmail: String, val password: String)