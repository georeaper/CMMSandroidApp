package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.CheckForms
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportCheckForm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoCheckListItems {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }
        fun insertCheckFormField(context: Context, fieldReportCheckForm: FieldReportCheckForm){
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {

                userDatabase!!.FieldReportCheckFormsDao().insertFieldReportCheckForms(fieldReportCheckForm)
            }
        }
        fun deleteCheckFormField(context: Context, fieldReportCheckForm: FieldReportCheckForm){
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.FieldReportCheckFormsDao().deleteFieldReportCheckForms(fieldReportCheckForm)
            }
        }
        fun updateCheckFormField(context: Context, fieldReportCheckForm: FieldReportCheckForm){
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.FieldReportCheckFormsDao().updateFieldReportCheckForms(fieldReportCheckForm)
            }
        }
        fun getCheckFormFields (context: Context, id :String): LiveData<List<FieldReportCheckForm>> {
            userDatabase= initialiseDB(context)

            return userDatabase!!.FieldReportCheckFormsDao().selectFieldReportCheckFormsByFieldReportEquipmentID(id)
        }
    }
}