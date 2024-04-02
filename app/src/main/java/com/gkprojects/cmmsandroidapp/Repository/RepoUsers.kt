package com.gkprojects.cmmsandroidapp.Repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.OverviewMainData
import com.gkprojects.cmmsandroidapp.DataClasses.TicketCalendar
import com.gkprojects.cmmsandroidapp.DataClasses.TicketCustomerName
import com.gkprojects.cmmsandroidapp.DataClasses.Tickets
import com.gkprojects.cmmsandroidapp.DataClasses.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RepoUsers {

    companion object{
        var userDatabase: CMMSDatabase?=null

        private fun initialiseDB(context: Context): CMMSDatabase?
        {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insertUser(context: Context, users:Users)
        {
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
            users.DateCreated = dateFormat.format(currentDateTime)
            users.LastModified=dateFormat.format(currentDateTime)
            userDatabase= initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                //userDatabase!!.hospitalDAO().addHospital(hospital)
                userDatabase!!.UsersDao().addUsers(users)
            }
        }
        fun deleteUser(context: Context,users: Users){
            userDatabase= initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                //userDatabase!!.hospitalDAO().addHospital(hospital)
                userDatabase!!.UsersDao().deleteUsers(users)
            }
        }

        fun getAllUsers(context: Context): LiveData<List<Users>>
        {
            userDatabase= initialiseDB(context)
            //return userDatabase!!.hospitalDAO().getAllHospitals()
            return userDatabase!!.UsersDao().getAllUsers()
        }
        fun updateUser(context: Context, users:Users){
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())

            users.LastModified=dateFormat.format(currentDateTime)
            userDatabase= initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                //userDatabase!!.hospitalDAO().addHospital(hospital)
                userDatabase!!.UsersDao().updateUsers(users)
            }
        }
        fun getSingleUser(context:Context ,id :String):LiveData<Users>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.UsersDao().getUserByID(id)
        }
        fun increaseLastReport(context: Context,number:Int ,id :String){
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.UsersDao().increaseLastReportNumber(number,id)
            }

        }
        fun getFirstUser(context: Context):LiveData<Users>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.UsersDao().getFirstUser()
        }


    }
}