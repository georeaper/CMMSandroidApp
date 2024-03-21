package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.CustomCheckListWithEquipmentData
import com.gkprojects.cmmsandroidapp.DataClasses.CustomWorkOrderPDFDATA
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReports
import com.gkprojects.cmmsandroidapp.DataClasses.WorkOrdersList
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportInventoryCustomData
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportToolsCustomData
import com.gkprojects.cmmsandroidapp.Repository.RepoWorkOrders


class WorkOrdersVM :ViewModel() {
    suspend fun insert(context: Context, workOrder: FieldReports)
    {
        RepoWorkOrders.insert(context,workOrder)
    }

    suspend fun update(context: Context,workOrder: FieldReports){
        RepoWorkOrders.update(context,workOrder)
    }
    fun getWorkOrderByID(context: Context,id :String):LiveData<FieldReports>{
        return RepoWorkOrders.getWorkOrderByID(context, id)
    }
    fun getWorkOrdersCustomerName(context: Context):LiveData<List<WorkOrdersList>>{
        return RepoWorkOrders.getWorkOrdersCustomerName(context)
    }
    fun printWorkOrder (context: Context,id: String):LiveData<CustomWorkOrderPDFDATA>{
        return RepoWorkOrders.printPdfCustomerData(context, id)
    }
    fun getDataEquipmentListAndCheckListByReportID(context: Context,id: String):LiveData<List<CustomCheckListWithEquipmentData>>{
        return RepoWorkOrders.getEquipmentListAndChecklistByReportID(context, id)
    }
    fun printDataInventoryListByReportID(context: Context,id: String):LiveData<List<FieldReportInventoryCustomData>>{
        return RepoWorkOrders.printInventoryReportID(context, id)
    }
    fun printDataToolsListByReportID(context: Context,id: String):LiveData<List<FieldReportToolsCustomData>>{
        return RepoWorkOrders.printToolsByReportID(context, id)
    }
}