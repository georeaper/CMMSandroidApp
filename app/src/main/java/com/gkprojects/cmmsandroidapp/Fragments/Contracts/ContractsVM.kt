package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.gkprojects.cmmsandroidapp.DataClasses.Contracts
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect


class ContractsVM : ViewModel(){

    suspend fun insert(context: Context, contract: Contracts)
    {
            RepoContracts.insert(context,contract)

    }

    fun getAllContractData(context: Context): LiveData<List<Contracts>>
    {

        return  RepoContracts.getAllContractData(context)
    }
    suspend fun deleteContract(context: Context, contract: Contracts){

        RepoContracts.delete(context,contract)
    }
    suspend fun updateContract(context: Context, contract: Contracts){

        RepoContracts.updateContractData(context,contract)
    }
    fun getCustomerId(context: Context): LiveData<List<CustomerSelect>> {
       // return RepoCases.getCustomerIdData(context)
        return  RepoContracts.getCustomerIdData(context)
    }

//    fun getCustomerNameWhereId(id :Int) :String{
//       // return RepoCases.getCustomerNameWhereId(id)
//    }
}