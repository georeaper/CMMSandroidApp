package com.gkprojects.cmmsandroidapp.Repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gkprojects.cmmsandroidapp.DataClasses.*


@Dao
interface CustomerDao {

    @Query("Select * from Customer")
    fun getAllCustomer(): LiveData<List<Customer>>

    @Insert
    fun addCustomer(customer: Customer)

    @Update
    fun updateCustomer(customer: Customer)
    @Delete
    fun deleteCustomer(customer: Customer)


}
@Dao
interface ContractsDao {

    @Query("Select * from Contracts")
    fun getAllContracts(): LiveData<List<Contracts>>

    @Query ("Select CustomerID,Name as CustomerName from Customer")
    fun getCustomerID(): LiveData<List<CustomerSelect>>

    @Insert
    fun addContracts(contracts:Contracts)

    @Update
    fun updateContracts(contracts:Contracts)
    @Delete
    fun deleteContracts(contracts:Contracts)

}
@Dao
interface ContractEquipmentsDao {

    @Query("Select * from ContractEquipments")
    fun getAllContractEquipment(): LiveData<List<ContractEquipments>>

    @Insert
    fun addContractEquipments(contracts:Contracts)

    @Update
    fun updateContractEquipments(contracts:Contracts)
    @Delete
    fun deleteContractEquipments(contracts:Contracts)

}
@Dao
interface DepartmentsDao {

    @Query("Select * from Departments")
    fun getAllDepartments(): LiveData<List<Departments>>
    @Query ("Select CustomerID,Name as CustomerName from Customer")
    fun getCustomerID():LiveData<List<CustomerSelect>>
    @Insert
    fun addDepartments(departments: Departments)

    @Update
    fun updateDepartments(departments: Departments)
    @Delete
    fun deleteDepartments(departments: Departments)


}
@Dao
interface EquipmentsDao{
    @Query("Select * from Equipments")
    fun getAllEquipments(): LiveData<List<Equipments>>

    @Query ("Select CustomerID,Name as CustomerName from Customer")
    fun getCustomerID():LiveData<List<CustomerSelect>>
    @Insert
    fun addEquipments(equipments: Equipments)

    @Update
    fun updateEquipments(equipments: Equipments)
    @Delete
    fun deleteEquipments(equipments: Equipments)

}

@Dao
interface FieldReportEquipmentDao {

    @Query("Select * from FieldReportEquipment")
    fun getAllFieldReportEquipment(): LiveData<List<FieldReportEquipment>>
    @Insert
    fun addFieldReportEquipment(fieldReportEquipment: FieldReportEquipment)

    @Update
    fun updateFieldReportEquipment(fieldReportEquipment: FieldReportEquipment)
    @Delete
    fun deleteFieldReportEquipment(fieldReportEquipment: FieldReportEquipment)

}
@Dao
interface FieldReportInventoryDao {

    @Query("Select * from FieldReportInventory")
    fun getAllFieldReportInventory(): LiveData<List<FieldReportInventory>>
    @Insert
    fun addFieldReportInventory(fieldReportInventory: FieldReportInventory)

    @Update
    fun updateFieldReportInventory(fieldReportInventory: FieldReportInventory)
    @Delete
    fun deleteFieldReportInventory(fieldReportInventory: FieldReportInventory)

}
@Dao
interface FieldReportsDao {

    @Query("Select * from FieldReports")
    fun getAllFieldReports(): LiveData<List<FieldReports>>
    @Query ("Select CustomerID,Name as CustomerName from Customer")
    fun getCustomerID():LiveData<List<CustomerSelect>>
    @Insert
    fun addFieldReports(fieldReports: FieldReports)

    @Update
    fun updateFieldReports(fieldReports: FieldReports)
    @Delete
    fun deleteFieldReports(fieldReports: FieldReports)

}
@Dao
interface InventoryDao {

    @Query("Select * from Inventory")
    fun getAllInventory(): LiveData<List<Inventory>>
    @Insert
    fun addInventory(inventory: Inventory)

    @Update
    fun updateInventory(inventory: Inventory)
    @Delete
    fun deleteInventory(inventory: Inventory)

}
@Dao
interface MaintenanceFieldFormDao {

    @Query("Select * from MaintenanceFieldForm")
    fun getAllMaintenanceFieldForm(): LiveData<List<MaintenanceFieldForm>>
    @Insert
    fun addMaintenanceFieldForm(maintenanceFieldForm: MaintenanceFieldForm)

    @Update
    fun updateMaintenanceFieldForm(maintenanceFieldForm: MaintenanceFieldForm)
    @Delete
    fun deleteMaintenanceFieldForm(maintenanceFieldForm: MaintenanceFieldForm)

}
@Dao
interface MaintenanceInventoryDao {

    @Query("Select * from MaintenanceInventory")
    fun getAllMaintenanceInventory(): LiveData<List<MaintenanceInventory>>
    @Insert
    fun addMaintenanceInventory(maintenanceInventory: MaintenanceInventory)

    @Update
    fun updateMaintenanceInventory(maintenanceInventory: MaintenanceInventory)
    @Delete
    fun deleteMaintenanceInventory(maintenanceInventory: MaintenanceInventory)

}
@Dao
interface MaintenancesDao {

    @Query("Select * from Maintenances")
    fun getAllMaintenances(): LiveData<List<Maintenances>>

    @Insert
    fun addMaintenances(maintenances: Maintenances)

    @Update
    fun updateMaintenances(maintenances: Maintenances)
    @Delete
    fun deleteMaintenances(maintenances: Maintenances)

}
@Dao
interface TicketsDao {

    @Query("Select * from Tickets")
    fun getAllTickets(): LiveData<List<Tickets>>

    @Query ("Select CustomerID,Name as CustomerName from Customer")
    fun getCustomerID():LiveData<List<CustomerSelect>>
    @Insert
    fun addTickets(tickets: Tickets)

    @Update
    fun updateTickets(tickets: Tickets)
    @Delete
    fun deleteTickets(tickets: Tickets)

}
@Dao
interface UsersDao {

    @Query("Select * from Users")
    fun getAllUsers(): LiveData<List<Users>>
    @Insert
    fun addUsers(users: Users)

    @Update
    fun updateUsers(users: Users)
    @Delete
    fun deleteUsers(users: Users)

}
