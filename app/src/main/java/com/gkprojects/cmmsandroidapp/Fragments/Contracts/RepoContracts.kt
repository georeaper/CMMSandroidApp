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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RepoContracts {

    companion object{
        var userDatabase: CMMSDatabase?=null

        private fun intialiseDB(context: Context): CMMSDatabase?
        {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insert(context: Context, contracts: Contracts)
        {
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
            contracts.DateCreated = dateFormat.format(currentDateTime)
            contracts.LastModified=dateFormat.format(currentDateTime)
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
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())

            contract.LastModified=dateFormat.format(currentDateTime)
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.ContractsDao().updateContracts(contract)
            }

        }
        fun getCustomerIdData(context: Context): LiveData<List<CustomerSelect>> {
            userDatabase= intialiseDB(context)

            return userDatabase!!.ContractsDao().getCustomerID()

        }

        @SuppressLint("SuspiciousIndentation")
        fun getListContracts(context: Context): LiveData<List<ContractsCustomerName>>{
            userDatabase =intialiseDB(context)
            return userDatabase!!.ContractsDao().getContractsCustomerNames()

        }
        fun getContractsById(context: Context,id :String): LiveData<Contracts>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.ContractsDao().getContractsById(id)
        }
        fun getContractEquipmentsById(context: Context,id : String):LiveData<List<ContractEquipments>>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.ContractEquipmentsDao().getContractEquipmentByID(id)
        }
        fun getDetailedContractByID(context: Context, id : String):LiveData<List<DetailedContract>>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.ContractEquipmentsDao().getDetailedContractByID(id)
        }
         fun deleteContractEquipment(context: Context,contractEquipment : ContractEquipments){
            userDatabase= intialiseDB(context)
            userDatabase!!.ContractEquipmentsDao().deleteContractEquipments(contractEquipment)

        }

        fun getContractEquipmentByContractEquipmentID(context: Context, id: String): LiveData<ContractEquipments> {
            userDatabase= intialiseDB(context)
            return userDatabase!!.ContractEquipmentsDao().getContractEquipmentByEquipmentID(id)
        }

        fun insertContractEquipmentIfNotExists(context: Context, contractEquipment: ContractEquipments) {
            userDatabase = intialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
//                val count = userDatabase!!.ContractEquipmentsDao().count(
//                    contractEquipment.ContractID ,
//                    contractEquipment.EquipmentID
//                )

//                if (count == 0) {
                    userDatabase!!.ContractEquipmentsDao().addContractEquipments(contractEquipment)
//                }
            }
        }


    }


}