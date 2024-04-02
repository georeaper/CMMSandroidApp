package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.CustomCheckListWithEquipmentData
import com.gkprojects.cmmsandroidapp.DataClasses.CustomWorkOrderPDFDATA
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReports
import com.gkprojects.cmmsandroidapp.DataClasses.WorkOrdersList
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportInventoryCustomData
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportToolsCustomData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RepoWorkOrders {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }
        fun insert(context: Context, workOrder : FieldReports)
        {
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
            workOrder.DateCreated = dateFormat.format(currentDateTime)
            workOrder.LastModified=dateFormat.format(currentDateTime)
            userDatabase = initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.FieldReportsDao().addFieldReports(workOrder)
            }
        }
        fun update(context: Context,workOrder: FieldReports){
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())

            workOrder.LastModified=dateFormat.format(currentDateTime)
            userDatabase= initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.FieldReportsDao().updateFieldReports(workOrder)
            }
        }

        fun getWorkOrderByID(context: Context,id :String):LiveData<FieldReports>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.FieldReportsDao().getReportsByID(id)
        }

        fun getWorkOrdersCustomerName(context: Context):LiveData<List<WorkOrdersList>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.FieldReportsDao().getCustomerName()
        }
        fun printPdfCustomerData(context: Context,id: String):LiveData<CustomWorkOrderPDFDATA>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.FieldReportsDao().printDetails(id)
        }
        fun getEquipmentListAndChecklistByReportID(context: Context,id: String):LiveData<List<CustomCheckListWithEquipmentData>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.FieldReportsDao().printEquipmentWithCheckList(id)
        }

        fun printToolsByReportID(context: Context,id: String):LiveData<List<FieldReportToolsCustomData>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.FieldReportsDao().printToolsByReportID(id)
        }
        fun printInventoryReportID(context: Context,id: String):LiveData<List<FieldReportInventoryCustomData>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.FieldReportsDao().printInventoryDataByReportID(id)
        }
    }
}