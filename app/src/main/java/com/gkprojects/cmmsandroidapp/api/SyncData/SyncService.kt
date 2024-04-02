package com.gkprojects.cmmsandroidapp.api.SyncData

import com.gkprojects.cmmsandroidapp.DataClasses.Customer

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface SyncService {
    @POST("syncBridgeAPI.php")
    fun authenticate(@Body syncData: SyncData): Call<List<Customer>>

}
data class SyncData(
    var deviceId : String,
    var timestamp: String?,
    val typeData: String,
    val customers: List<Customer>
    ){
    override fun toString(): String {
        return "SyncData(deviceId='$deviceId', timestamp=$timestamp, typeData='$typeData', customers=$customers)"
    }
}