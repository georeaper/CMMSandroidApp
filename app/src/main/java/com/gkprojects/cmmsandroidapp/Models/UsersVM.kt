package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentListInCases
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentSelectCustomerName
import com.gkprojects.cmmsandroidapp.DataClasses.Equipments
import com.gkprojects.cmmsandroidapp.DataClasses.Tickets
import com.gkprojects.cmmsandroidapp.DataClasses.Users
import com.gkprojects.cmmsandroidapp.Repository.RepoEquipment
import com.gkprojects.cmmsandroidapp.Repository.RepoUsers

class UsersVM: ViewModel() {
    fun insertUser(context: Context, users: Users)
    {
        RepoUsers.insertUser(context,users)
    }
    suspend fun deleteUser(context: Context, users: Users){
         RepoUsers.deleteUser(context,users)
    }
    fun getAllUsers(context: Context):LiveData<List<Users>>{
        return RepoUsers.getAllUsers(context)
    }
    fun getSingleUser(context: Context,id :Int):LiveData<Users>{
        return RepoUsers.getSingleUser(context, id)
    }
    fun increaseLastReportNumber(context: Context,number: Int,id: Int){
        RepoUsers.increaseLastReport(context,number,id)
    }


}