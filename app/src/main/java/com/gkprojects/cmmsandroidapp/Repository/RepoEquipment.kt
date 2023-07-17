package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RepoEquipment {
    companion object{
        var userDatabase:CMMSDatabase?=null

        private fun intialiseDB(context:Context): CMMSDatabase?
        {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insert(context: Context,equipment: Equipment)
        {
            userDatabase= intialiseDB(context)

            CoroutineScope(IO).launch {
                userDatabase!!.equipmentDAO().addEquipment(equipment)
            }
        }

        fun getAllEquipmentrData(context: Context): LiveData<List<Equipment>>
        {
            userDatabase= intialiseDB(context)
            return userDatabase!!.equipmentDAO().getAllEquipment()
        }
    }

}