package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.Repository.RepoEquipment

class EquipmentVM :ViewModel() {



    fun insert(context: Context,equipment: Equipment)
    {
        RepoEquipment.insert(context,equipment)
    }

    fun getAllUserData(context: Context):LiveData<List<Equipment>>
    {
        return RepoEquipment.getAllEquipmentrData(context)
    }
}