package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Cases
import com.gkprojects.cmmsandroidapp.DataClasses.Contract
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoContracts {

    companion object{
        var userDatabase: CMMSDatabase?=null

        private fun intialiseDB(context: Context): CMMSDatabase?
        {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insert(context: Context, contract: Contract)
        {
            userDatabase= intialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.contractDAO().addContract(contract)

            }
        }

        fun delete(context: Context, contract: Contract){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {

                userDatabase!!.contractDAO().deleteContract(contract)
            }

        }

        fun getAllContractData(context: Context): LiveData<List<Contract>>
        {
            userDatabase= intialiseDB(context)
            //return userDatabase!!.hospitalDAO().getAllHospitals()
            return userDatabase!!.contractDAO().getAllContracts()
        }

        fun updateContractData(context: Context, contract: Contract){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.contractDAO().updateContract(contract)
            }

        }
        fun getCustomerIdData(context: Context): LiveData<List<CustomerSelect>> {


            return userDatabase!!.contractDAO().getIdFromHospital()

        }
//        fun getCustomerNameWhereId(id :Int) : String{
//
//
//            return userDatabase!!.contractDAO().
//        }
    }
}