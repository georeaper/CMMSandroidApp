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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    fun getSingleUser(context: Context,id :String):LiveData<Users>{
        return RepoUsers.getSingleUser(context, id)
    }
    fun increaseLastReportNumber(context: Context,number: Int,id: String){
        RepoUsers.increaseLastReport(context,number,id)
    }
    fun getFirstUser(context: Context):LiveData<Users>{
        return RepoUsers.getFirstUser(context)
    }
    fun updateUser(context: Context,user :Users) {

        CoroutineScope(Dispatchers.IO).launch {
            RepoUsers.updateUser(context, user)
        }

    }




}