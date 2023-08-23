package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoCustomer {
    companion object{
        var userDatabase: CMMSDatabase?=null

        private fun intialiseDB(context: Context): CMMSDatabase?
        {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insert(context: Context, hospital: Hospital)
        {
            userDatabase= intialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.hospitalDAO().addHospital(hospital)
            }
        }

        fun delete(context: Context,hospital: Hospital){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.hospitalDAO().deleteHospital(hospital)
            }

        }

        fun getAllCustomerData(context: Context): LiveData<List<Hospital>>
        {
            userDatabase= intialiseDB(context)
            return userDatabase!!.hospitalDAO().getAllHospitals()
        }

        fun updateCustomerData(context: Context,hospital: Hospital){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.hospitalDAO().updateHospital(hospital)
            }

        }
        fun getCustomerIdData(context: Context):LiveData<List<CustomerSelect>>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.hospitalDAO().getIdFromHospital()

        }
    }

}