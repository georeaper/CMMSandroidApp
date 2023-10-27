package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoCases {

    companion object{
        var userDatabase: CMMSDatabase?=null

        private fun intialiseDB(context: Context): CMMSDatabase?
        {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insert(context: Context, tickets: Tickets)
        {
            userDatabase= intialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                //userDatabase!!.hospitalDAO().addHospital(hospital)
                userDatabase!!.TicketsDao().addTickets(tickets)
            }
        }
//
        fun delete(context: Context, tickets: Tickets){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {

                userDatabase!!.TicketsDao().deleteTickets(tickets)
            }

        }
//
        fun getAllCustomerData(context: Context): LiveData<List<Tickets>>
        {
            userDatabase= intialiseDB(context)
            //return userDatabase!!.hospitalDAO().getAllHospitals()
            return userDatabase!!.TicketsDao().getAllTickets()
        }
//
        fun updateCustomerData(context: Context, tickets: Tickets){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.TicketsDao().updateTickets(tickets)
            }

        }
        fun getCustomerDataByID(context: Context,id:Int):LiveData<List<Tickets>>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.TicketsDao().getTicketsById(id)
        }
        fun getCustomerIdData(context: Context): LiveData<List<CustomerSelect>> {

            //return userDatabase!!.hospitalDAO().getIdFromHospital()
            return userDatabase!!.TicketsDao().getCustomerID()

        }
        fun getCustomerNameTickets(context: Context):LiveData<List<TicketCustomerName>>{

            userDatabase= intialiseDB(context)
                return userDatabase!!.TicketsDao().getCustomerName()

        }

    }
}