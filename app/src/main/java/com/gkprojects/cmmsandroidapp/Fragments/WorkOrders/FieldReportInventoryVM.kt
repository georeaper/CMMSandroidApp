package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportInventory


class FieldReportInventoryVM : ViewModel(){

    fun insert (context: Context,fieldReportInventory: FieldReportInventory){
        RepoWorkOrderInventory.insertWorkOrderInventory(context, fieldReportInventory)

    }

    fun delete(context: Context,fieldReportInventory: FieldReportInventory){
        RepoWorkOrderInventory.deleteWorkOrderInventory(context, fieldReportInventory)
    }

    fun getInventoryByFieldReportID(context :Context , id :String ):LiveData<List<FieldReportInventoryCustomData>>{
        return RepoWorkOrderInventory.getWorkOrderInventoryByReportID(context,id)
    }
}