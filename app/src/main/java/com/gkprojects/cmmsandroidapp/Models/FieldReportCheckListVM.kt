package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportCheckForm
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportEquipment
import com.gkprojects.cmmsandroidapp.Repository.RepoCheckListItems
import com.gkprojects.cmmsandroidapp.Repository.RepoFieldReportEquipment

class FieldReportCheckListVM :ViewModel() {

    fun getFieldReportCheckListByFieldEquipmentID(context: Context, id : String):LiveData<List<FieldReportCheckForm>>{
        return RepoCheckListItems.getCheckFormFields(context, id)
    }
    fun insertFieldReportCheckList(context: Context,fieldReportCheckForm: FieldReportCheckForm){
        RepoCheckListItems.insertCheckFormField(context, fieldReportCheckForm)
    }

}