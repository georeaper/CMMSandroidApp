package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.CustomDisplayDatFieldReportEquipments
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportEquipment
import com.gkprojects.cmmsandroidapp.Repository.RepoFieldReportEquipment

class FieldReportEquipmentVM : ViewModel() {

    fun getSingleFieldReportEquipmentByID(context: Context,id :String):LiveData<FieldReportEquipment>{
        return RepoFieldReportEquipment.getFieldReportByID(context,id)

    }
    fun insertFieldReportEquipment(context: Context,fieldReportEquipment: FieldReportEquipment){
        RepoFieldReportEquipment.insertFieldReportEquipment(context,fieldReportEquipment)
    }

    fun getFieldReportEquipmentCustomDisplay(context: Context,id :String):LiveData<List<CustomDisplayDatFieldReportEquipments>>{
        return RepoFieldReportEquipment.getFieldReportEquipmentByID(context,id)

    }
    fun deleteFieldReportEquipment(context: Context,fieldReportEquipment: FieldReportEquipment){
        RepoFieldReportEquipment.deleteFieldReportEquipment(context, fieldReportEquipment)
    }
    fun updateFieldReportEquipment(context: Context,fieldReportEquipment: FieldReportEquipment){
        RepoFieldReportEquipment.updateFieldReportEquipment(context, fieldReportEquipment)
    }
    fun updateStatusFieldReportEquipment(context: Context,value: Int,id:String){
        RepoFieldReportEquipment.updateStatusFieldReportEquipmentByID(context,value,id)
    }
}