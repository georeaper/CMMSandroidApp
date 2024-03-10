package com.gkprojects.cmmsandroidapp.Fragments.SpecialTools

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Tools

class SpecialToolsVM : ViewModel() {

    fun insertTools(context: Context,tools: Tools){

        RepoSpecialTools.insert(context,tools)
    }
    fun updateTools(context: Context,tools: Tools){
        RepoSpecialTools.updateTools(context,tools)
    }
    fun deleteTools(context: Context,tools: Tools){
        RepoSpecialTools.deleteTools(context, tools)
    }

    fun getTools(context: Context):LiveData<List<Tools>>{
        return RepoSpecialTools.getAllTools(context)
    }
    fun getSingleTool(context: Context,id:String):LiveData<Tools>{
        return RepoSpecialTools.getSingleTool(context, id)
    }
}