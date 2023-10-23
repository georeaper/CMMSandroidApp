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
//    @Query
//        ("Select Contracts.CustomerID, Customer.Name AS CustomerName, Contracts.ContractID," +
//            "Contracts.Title, Contracts.DateStart, Contracts.DateEnd, Contracts.Value,Contracts.Notes," +
//            "Contracts.Description, Contracts.ContractType, Contracts.ContractStatus, Contracts.ContactName " +
//            "From Contracts LEFT JOIN Customer ON Contracts.CustomerID = Customer.CustomerID"
//    )
    @Query("SELECT Contracts.CustomerID, Customer.Name AS CustomerName, Contracts.ContractID, " +
            "Contracts.Title, Contracts.DateStart, Contracts.DateEnd, Contracts.Value, " +
            "Contracts.Notes, Contracts.Description, Contracts.ContractType, " +
            "Contracts.ContractStatus, Contracts.ContactName " +
            "FROM Contracts LEFT JOIN Customer ON Contracts.CustomerID = Customer.CustomerID")
    fun getContractsCustomerNames(): LiveData<List<ContractsCustomerName>>

    //fun getCustomerName():LiveData<List<ContractsCustomerName>?>

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

    @Query
    ("Select CustomerID,Name as CustomerName from Customer")
    fun getCustomerID():LiveData<List<CustomerSelect>>
    @Query
    ("Select Equipments.CustomerID,Customer.Name AS CustomerName,Equipments.EquipmentID,Equipments.Name,Equipments.SerialNumber,Equipments.EquipmentStatus," +
     "Equipments.Model,Equipments.Manufacturer,Equipments.InstallationDate,Equipments.EquipmentCategory,Equipments.EquipmentVersion,Equipments.Warranty,Equipments.Description "+
     "From Equipments Left JOIN Customer ON Equipments.CustomerID = Customer.CustomerID"
    )
     fun getCustomerName():LiveData<List<EquipmentSelectCustomerName>>

    @Query("Select * FROM Equipments WHERE EquipmentID= :id")
     fun SelectRecordById(id :Int) : LiveData<Equipments>

 @Insert
    fun addEquipments(equipments: Equipments)

    @Update
    fun updateEquipments(equipments: Equipments)
    @Delete
    suspend fun delete(equipments: Equipments)

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

    @Query("Select Tickets.TicketID,Tickets.Title,Tickets.Active,Tickets.DateStart,Customer.Name AS CustomerName,Tickets.UserID," +
            "Tickets.CustomerID,Tickets.EquipmentID "+
            "From Tickets " +
            "Left JOIN Customer ON Tickets.CustomerID = Customer.CustomerID"
    )
    fun getCustomerName():LiveData<List<TicketCustomerName>>


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
