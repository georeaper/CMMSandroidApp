package com.gkprojects.cmmsandroidapp.api
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.gkprojects.cmmsandroidapp.DataClasses.Response as ResponseModel
//class ApiViewModel :ViewModel() {
//
//    val authResponse = MutableLiveData<ResponseModel>()
//
//    fun authenticate() {
//        val call = RetrofitClient.instance.authenticate()
//        call.enqueue(object : Callback<ResponseModel> {
//            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
//                if (response.isSuccessful) {
//                    authResponse.value = response.body()
//                    Log.d("TestAPISuccess","${authResponse.value}")
//                } else {
//                    Log.d("TestAPIFailure","Success but failed")
//                    // Handle error
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
//                Log.d("TestAPIFailure","Request failed", t)
//            }
//        })
//    }
//}

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val response: ResponseModel) : AuthState()
    data class Failure(val message: String) : AuthState()
    object NoConnection : AuthState()
}

class ApiViewModel : ViewModel() {

    val authResponse = MutableLiveData<AuthState>()

    fun authenticate(context: Context) {
        if (!isNetworkAvailable(context)) {
            authResponse.value = AuthState.NoConnection
            return
        }

        authResponse.value = AuthState.Loading

        val call = RetrofitClient.instance.authenticate()
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    authResponse.value = AuthState.Success(response.body()!!)
                    Log.d("TestAPISuccess","${authResponse.value}")
                } else {
                    authResponse.value = AuthState.Failure("Success but failed")
                    Log.d("TestAPIFailure","Success but failed")
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                authResponse.value = AuthState.Failure("Request failed: ${t.message}")
                Log.d("TestAPIFailure","Request failed", t)
            }
        })
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}