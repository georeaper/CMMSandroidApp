package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportTools

class FieldReportToolsVM : ViewModel() {

    fun insert (context: Context , fieldReportTools: FieldReportTools){
        RepoWorkOrderTools.insertWorkOrderTools(context,fieldReportTools)
    }
    fun delete (context: Context ,fieldReportTools: FieldReportTools){
        RepoWorkOrderTools.deleteWorkOrderTools(context, fieldReportTools)
    }

    fun getTollsByReportID(context: Context, id :String) :LiveData<List<FieldReportToolsCustomData>>{
        return  RepoWorkOrderTools.getWorkOrderToolsByReportID(context, id)
    }

}