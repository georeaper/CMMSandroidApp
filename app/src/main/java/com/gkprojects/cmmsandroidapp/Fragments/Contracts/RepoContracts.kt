package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.*
import com.gkprojects.cmmsandroidapp.Repository.RepoEquipment
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

        fun insert(context: Context, contracts: Contracts)
        {
            userDatabase= intialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.ContractsDao().addContracts(contracts)

            }
        }

        fun delete(context: Context, contracts: Contracts){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {

                userDatabase!!.ContractsDao().deleteContracts(contracts)
            }

        }

        fun getAllContractData(context: Context): LiveData<List<Contracts>>
        {
            userDatabase= intialiseDB(context)
            //return userDatabase!!.hospitalDAO().getAllHospitals()
            return userDatabase!!.ContractsDao().getAllContracts()
        }

        fun updateContractData(context: Context, contract: Contracts){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.ContractsDao().updateContracts(contract)
            }

        }
        fun getCustomerIdData(context: Context): LiveData<List<CustomerSelect>> {


            return userDatabase!!.ContractsDao().getCustomerID()

        }

        @SuppressLint("SuspiciousIndentation")
        fun getListContracts(context: Context): LiveData<List<ContractsCustomerName>>{
            userDatabase =intialiseDB(context)
                var tempLog =userDatabase!!.ContractsDao().getContractsCustomerNames()

                    Log.d("tag34",tempLog.toString())

            return  tempLog

        }

        fun getContractsOverview(context: Context):LiveData<List<OverviewMainData>>{

            userDatabase= intialiseDB(context)
            return userDatabase!!.ContractsDao().getCustomerNameOnOverview()
        }
    }


}