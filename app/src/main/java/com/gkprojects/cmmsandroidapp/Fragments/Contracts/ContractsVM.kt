package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.ContractEquipments

import com.gkprojects.cmmsandroidapp.DataClasses.Contracts
import com.gkprojects.cmmsandroidapp.DataClasses.ContractsCustomerName
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.DetailedContract
import com.gkprojects.cmmsandroidapp.DataClasses.OverviewMainData


class ContractsVM : ViewModel(){
    init {
        Log.d("ContractsVM", "ViewModel is being initialized")
        // Other initialization code
    }

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
    suspend fun getCustomerId(context: Context): LiveData<List<CustomerSelect>> {
       // return RepoCases.getCustomerIdData(context)
        return  RepoContracts.getCustomerIdData(context)
    }

    fun getCustomerName(context: Context): LiveData<List<ContractsCustomerName>>{
        return  RepoContracts.getListContracts(context)
    }
    fun getContractByID(context: Context,id :String):LiveData<Contracts>{
        return  RepoContracts.getContractsById(context,id)
    }
    fun getContractEquipmentByID(context: Context,id: String):LiveData<List<ContractEquipments>>{
        return RepoContracts.getContractEquipmentsById(context,id)
    }
    fun getDetailedContractByID(context: Context,id: String):LiveData<List<DetailedContract>>{
        return RepoContracts.getDetailedContractByID(context,id)
    }
    fun getContractEquipmentByContractEquipmentID(context: Context,id: String):LiveData<ContractEquipments>{
        return RepoContracts.getContractEquipmentByContractEquipmentID(context,id)
    }
    suspend fun deleteContractEquipment(context: Context, contractEquipments: ContractEquipments){
        RepoContracts.deleteContractEquipment(context,contractEquipments)
    }
    suspend fun insertContractEquipment(context:Context ,contractEquipments: ContractEquipments){
        RepoContracts.insertContractEquipmentIfNotExists(context,contractEquipments)
    }

}