package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.CheckForms
import com.gkprojects.cmmsandroidapp.DataClasses.Tickets
import com.gkprojects.cmmsandroidapp.Repository.RepoCases
import com.gkprojects.cmmsandroidapp.Repository.RepoCheckForms

class CheckFormVM: ViewModel() {

     fun insert(context: Context, checkForms: CheckForms)
    {

        RepoCheckForms.insertCheckFormField(context,checkForms)
    }
    fun delete(context: Context,checkForms: CheckForms){
        RepoCheckForms.deleteCheckFormField(context,checkForms)
    }
    fun getCheckFormFields(context: Context,id :String):LiveData<List<CheckForms>>{
        return RepoCheckForms.getCheckFormFields(context,id)
    }

}