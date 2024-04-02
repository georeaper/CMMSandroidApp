package com.gkprojects.cmmsandroidapp.Fragments.SpecialTools

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Tools
import com.gkprojects.cmmsandroidapp.Fragments.SpecialTools.ToolsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RepoSpecialTools {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insert(context: Context,tools: Tools){
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
            tools.DateCreated = dateFormat.format(currentDateTime)
            tools.LastModified=dateFormat.format(currentDateTime)
            userDatabase= initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.ToolsDao().insertTools(tools)
            }
        }
        fun getAllTools(context: Context):LiveData<List<Tools>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.ToolsDao().getAllTools()
        }

        fun getSingleTool(context: Context,id:String):LiveData<Tools>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.ToolsDao().getSingleTool(id)
        }
        fun updateTools(context: Context,tools: Tools){
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())

            tools.LastModified=dateFormat.format(currentDateTime)
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.ToolsDao().updateTools(tools)
            }
        }
        fun deleteTools(context: Context,tools: Tools){
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.ToolsDao().deleteTools(tools)
            }
        }
    }
}