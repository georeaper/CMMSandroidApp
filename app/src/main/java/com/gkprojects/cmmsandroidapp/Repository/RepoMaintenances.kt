package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Equipments
import com.gkprojects.cmmsandroidapp.DataClasses.Maintenances
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoMaintenances {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insert(context: Context,maintenances: Maintenances)
        {
            RepoMaintenances.userDatabase = RepoMaintenances.initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                RepoMaintenances.userDatabase!!.MaintenancesDao().addMaintenances(maintenances)
            }
        }
        suspend fun delete(context: Context,maintenances: Maintenances){
            RepoMaintenances.userDatabase = RepoMaintenances.initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch{
                RepoMaintenances.userDatabase!!.MaintenancesDao().deleteMaintenances(maintenances)
            }
        }
        fun getAllMaintenances(context: Context): LiveData<List<Maintenances>>
        {
            RepoMaintenances.userDatabase = RepoMaintenances.initialiseDB(context)
            return RepoMaintenances.userDatabase!!.MaintenancesDao().getAllMaintenances()
        }
    }
}