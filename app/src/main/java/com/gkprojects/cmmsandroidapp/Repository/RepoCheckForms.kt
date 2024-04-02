package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.CheckForms
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RepoCheckForms {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }
         fun insertCheckFormField(context:Context,checkForms: CheckForms){
             val currentDateTime = Calendar.getInstance().time
             val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
             checkForms.DateCreated = dateFormat.format(currentDateTime)
             checkForms.LastModified=dateFormat.format(currentDateTime)

            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {

                userDatabase!!.CheckFormsDao().addCheckFormsFields(checkForms)
            }
        }
         fun deleteCheckFormField(context:Context,checkForms: CheckForms){
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                //userDatabase!!.hospitalDAO().addHospital(hospital)
                userDatabase!!.CheckFormsDao().deleteCheckFormsFields(checkForms)
            }
        }
         fun getCheckFormFields (context: Context,id :String): LiveData<List<CheckForms>> {
            userDatabase= initialiseDB(context)
            return userDatabase!!.CheckFormsDao().getCheckFormsFieldsByMaintenanceID(id)
        }
    }
}