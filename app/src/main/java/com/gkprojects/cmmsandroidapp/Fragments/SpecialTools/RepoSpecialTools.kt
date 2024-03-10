package com.gkprojects.cmmsandroidapp.Fragments.SpecialTools

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Tools
import com.gkprojects.cmmsandroidapp.Fragments.SpecialTools.ToolsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RepoSpecialTools {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insert(context: Context,tools: Tools){
            userDatabase= initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.ToolsDao().insertTools(tools)
            }
        }
        fun getAllTools(context: Context):LiveData<List<Tools>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.ToolsDao().getAllTools()
        }
    }
}