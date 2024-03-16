package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportTools

class RepoWorkOrderTools {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insertWorkOrderTools(context : Context ,fieldReportTools: FieldReportTools){
            userDatabase= initialiseDB(context)
            userDatabase!!.FieldReportToolsDao().insertFieldReportTool(fieldReportTools)
        }

        fun updateWorkOrderTools(context: Context,fieldReportTools: FieldReportTools){
            userDatabase= initialiseDB(context)
            userDatabase!!.FieldReportToolsDao().updateFieldReportTools(fieldReportTools)
        }
        fun deleteWorkOrderTools(context: Context,fieldReportTools: FieldReportTools){
            userDatabase= initialiseDB(context)
            userDatabase!!.FieldReportToolsDao().deleteFieldReportTool(fieldReportTools)
        }
        fun getWorkOrderToolsByReportID(context: Context,id :String):LiveData<List<FieldReportToolsCustomData>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.FieldReportToolsDao().getFieldReportToolsByID(id)
        }

    }

}