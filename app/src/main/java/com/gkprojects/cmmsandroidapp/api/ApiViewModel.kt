package com.gkprojects.cmmsandroidapp.api
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.gkprojects.cmmsandroidapp.DataClasses.Response as ResponseModel
class ApiViewModel :ViewModel() {

    val authResponse = MutableLiveData<ResponseModel>()

    fun authenticate() {
        val call = RetrofitClient.instance.authenticate()
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    authResponse.value = response.body()
                    Log.d("TestAPISuccess","${authResponse.value}")
                } else {
                    Log.d("TestAPIFailure","Success but failed")
                    // Handle error
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("TestAPIFailure","Request failed", t)
            }
        })
    }
}