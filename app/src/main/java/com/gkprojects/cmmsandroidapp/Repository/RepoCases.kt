package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Cases
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
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

        fun insert(context: Context, cases: Cases)
        {
            userDatabase= intialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                //userDatabase!!.hospitalDAO().addHospital(hospital)
                userDatabase!!.casesDAO().addCases(cases)
            }
        }

        fun delete(context: Context, cases: Cases){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {

                userDatabase!!.casesDAO().deleteCases(cases)
            }

        }

        fun getAllCustomerData(context: Context): LiveData<List<Cases>>
        {
            userDatabase= intialiseDB(context)
            //return userDatabase!!.hospitalDAO().getAllHospitals()
            return userDatabase!!.casesDAO().getAllCases()
        }

        fun updateCustomerData(context: Context, cases: Cases){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.casesDAO().updateCases(cases)
            }

        }
        fun getCustomerIdData(context: Context): LiveData<List<CustomerSelect>> {

            //return userDatabase!!.hospitalDAO().getIdFromHospital()
            return userDatabase!!.casesDAO().getIdFromHospital()

        }
        fun getCustomerNameWhereId(id :Int) : String{


            return userDatabase!!.casesDAO().getCustomerNameWhereId(id)
        }
    }
}