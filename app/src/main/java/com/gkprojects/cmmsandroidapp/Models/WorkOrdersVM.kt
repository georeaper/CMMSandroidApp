package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReports
import com.gkprojects.cmmsandroidapp.DataClasses.WorkOrdersList
import com.gkprojects.cmmsandroidapp.Repository.RepoWorkOrders


class WorkOrdersVM :ViewModel() {
    suspend fun insert(context: Context, workOrder: FieldReports)
    {
        RepoWorkOrders.insert(context,workOrder)
    }

    suspend fun update(context: Context,workOrder: FieldReports){
        RepoWorkOrders.update(context,workOrder)
    }
    fun getWorkOrderByID(context: Context,id :Int):LiveData<FieldReports>{
        return RepoWorkOrders.getWorkOrderByID(context, id)
    }
    fun getWorkOrdersCustomerName(context: Context):LiveData<List<WorkOrdersList>>{
        return RepoWorkOrders.getWorkOrdersCustomerName(context)
    }
}