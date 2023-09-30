package com.gkprojects.cmmsandroidapp.Repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gkprojects.cmmsandroidapp.DataClasses.*
//
//@Dao
//interface HospitalDAO {
//    @Query("Select * from hospitals")
//    fun getAllHospitals(): LiveData<List<Hospital>>
//    @Query("Select hospitalID,name from hospitals")
//    fun getIdFromHospital(): LiveData<List<CustomerSelect>>
//    @Insert
//    fun addHospital(hospital: Hospital)
//    @Update
//    fun updateHospital(hospital: Hospital)
//    @Delete
//     fun deleteHospital(hospital: Hospital)
//}
//@Dao
//interface EquipmentDAO {
//    @Query("Select * from equipment")
//    fun getAllEquipment(): LiveData<List<Equipment>>
//
//    @Query("Select hospitalID,name from hospitals")
//     fun getCustomersForEquipment(): LiveData<List<EquipmentCustomerSelect>>
//    @Insert
//    fun addEquipment(equipment: Equipment)
//    @Update
//    fun updateEquipment(equipment: Equipment)
//    @Delete
//    fun deleteEquipment(equipment: Equipment)
//}
//@Dao
//interface ContractDAO {
//    @Query("Select * from contracts")
//    fun getAllContracts(): LiveData<List<Contract>>
//    @Query("Select name from hospitals where hospitalId = :id")
//    fun getCustomerNameWhereId(id :Int) : String
//    @Query("Select hospitalID,name from hospitals")
//    fun getIdFromHospital(): LiveData<List<CustomerSelect>>
//    @Insert
//    fun addContract(contract: Contract)
//    @Update
//    fun updateContract(contract: Contract)
//    @Delete
//    fun deleteContract(contract: Contract)
//}
//@Dao
//interface ContractEquipmentDAO {
////    @Query("Select * from contract_equipment")
////    fun getAllContractEquipmentByContractID(contractID: String): List<ContractEquipment>
//    //fun getAllContractEquipmentByEquipmentID(equipmentID: String): List<ContractEquipment>
//    @Insert
//    fun addContractEquipment(contractEquipment: ContractEquipment)
//    @Delete
//    fun deleteContractEquipment(contractEquipment: ContractEquipment)
//}
//@Dao
//interface MaintenanceDAO {
//   @Query("Select * from maintenance")
//    fun getAllMaintenance(): LiveData<List<Maintenance>>
//    @Query("Select hospitalID,name from hospitals")
//    fun getIdFromHospital(): LiveData<List<CustomerSelect>>
//    @Insert
//    fun addMaintenance(maintenance: Maintenance)
//    @Update
//    fun updateMaintenance(maintenance: Maintenance)
//    @Delete
//    fun deleteMaintenance(maintenance: Maintenance)
//}
//@Dao
//interface SparePartDAO {
//    @Query("Select * from spare_parts")
//    fun getAllSpareParts(): LiveData<List<SparePart>>
//    @Query("Select hospitalID,name from hospitals")
//    fun getIdFromHospital(): LiveData<List<CustomerSelect>>
//    @Insert
//    fun addSparePart(sparePart: SparePart)
//    @Update
//    fun updateSparePart(sparePart: SparePart)
//    @Delete
//    fun deleteSparePart(sparePart: SparePart)
//}
//@Dao
//interface CasesDao{
//    @Query("Select * from cases")
//    fun getAllCases(): LiveData<List<Cases>>
//    @Query("Select name from hospitals where hospitalId = :id")
//    fun getCustomerNameWhereId(id :Int) : String
//
//    @Query("Select hospitalID,name from hospitals")
//    fun getIdFromHospital(): LiveData<List<CustomerSelect>>
//    @Insert
//    fun addCases(cases: Cases)
//    @Update
//    fun updateCases(cases: Cases)
//    @Delete
//    fun deleteCases(cases: Cases)
//}
