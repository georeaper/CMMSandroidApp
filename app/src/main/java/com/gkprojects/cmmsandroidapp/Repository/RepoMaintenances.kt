package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Equipments
import com.gkprojects.cmmsandroidapp.DataClasses.Maintenances
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RepoMaintenances {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insert(context: Context,maintenances: Maintenances)
        {
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
            maintenances.DateCreated = dateFormat.format(currentDateTime)
            maintenances.LastModified=dateFormat.format(currentDateTime)
            userDatabase = initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.MaintenancesDao().addMaintenances(maintenances)
            }
        }
        suspend fun delete(context: Context,maintenances: Maintenances){
            userDatabase = initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch{
                userDatabase!!.MaintenancesDao().deleteMaintenances(maintenances)
            }
        }
        fun getAllMaintenances(context: Context): LiveData<List<Maintenances>>
        {
            userDatabase = initialiseDB(context)
            return userDatabase!!.MaintenancesDao().getAllMaintenances()
        }
    }
}