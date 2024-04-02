package com.gkprojects.cmmsandroidapp.api.Login

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Users
import retrofit2.Call
import retrofit2.Callback




sealed class AuthState {
    object Loading : AuthState()
    data class Success(val response: Users) : AuthState()
    data class Failure(val message: String) : AuthState()
    object NoConnection : AuthState()
}


class ApiLoginViewModel : ViewModel() {
    val authResponse = MutableLiveData<AuthState>()

    fun authenticate(context: Context, usernameOrEmail: String, password: String) {
        if (!isNetworkAvailable(context)) {
            authResponse.value = AuthState.NoConnection
            return
        }

        authResponse.value = AuthState.Loading

        val call = LoginClient.instance.authenticate(Credentials(usernameOrEmail, password))
        call.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: retrofit2.Response<Users>) {
                if (response.isSuccessful) {
                    authResponse.value = AuthState.Success(response.body()!!)
                    Log.d("TestAPISuccess", "${authResponse.value}")
                } else {
                    authResponse.value = AuthState.Failure("Success but failed")
                    Log.d("TestAPIFailure", "Success but failed")
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                authResponse.value = AuthState.Failure("Request failed: ${t.message}")
                Log.d("TestAPIFailure", "Request failed", t)
            }
        })
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}