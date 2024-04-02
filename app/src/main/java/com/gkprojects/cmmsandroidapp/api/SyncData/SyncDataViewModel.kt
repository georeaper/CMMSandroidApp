package com.gkprojects.cmmsandroidapp.api.SyncData

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Customer


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SyncDataViewModel : ViewModel() {
    val authResponse = MutableLiveData<AuthState>()
    fun authenticate(context: Context, deviceId :String ,timeStamp : String? , listCustomer :List<Customer>) {
        if (!isNetworkAvailable(context)) {
            authResponse.value = AuthState.NoConnection
            return
        }

        authResponse.value = AuthState.Loading
        val syncData = SyncData(deviceId,timeStamp,"Customer",listCustomer)
        Log.d("SyncData", syncData.toString())
        val call = SyncClient.instance.authenticate(syncData)
        call.enqueue(object : Callback<List<Customer>> {
            override fun onResponse(
                call: Call<List<Customer>>,
                response: Response<List<Customer>>
            ) {
                if (response.isSuccessful) {
                    authResponse.value = AuthState.Success(response.body()!!)
                    Log.d("TestSyncSuccess", "${authResponse.value}")
                } else {
                    authResponse.value = AuthState.Failure("Success but failed")
                    Log.d("TestSyncFailure", "Success but failed")
                }
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                authResponse.value = AuthState.Failure("Request failed: ${t.message}")
                Log.d("TestSyncing", "Request failed", t)
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

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val response: List<Customer>) : AuthState()
    data class Failure(val message: String) : AuthState()
    object NoConnection : AuthState()
}