package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Maintenances
import com.gkprojects.cmmsandroidapp.Repository.RepoMaintenances

class MaintenancesVM :ViewModel(){

    fun insert(context : Context,maintenances: Maintenances){
        RepoMaintenances.insert(context,maintenances)

    }

    suspend fun delete(context : Context, maintenances: Maintenances){
        RepoMaintenances.delete(context ,maintenances)

    }

    fun getAllMaintenances(context:Context):LiveData<List<Maintenances>>{

        return  RepoMaintenances.getAllMaintenances(context)

    }
}