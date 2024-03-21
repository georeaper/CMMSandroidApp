package com.gkprojects.cmmsandroidapp.Repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gkprojects.cmmsandroidapp.DataClasseUNused.MaintenanceFieldForm
import com.gkprojects.cmmsandroidapp.DataClasseUNused.MaintenanceInventory
import com.gkprojects.cmmsandroidapp.DataClasses.*
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportInventoryCustomData
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportToolsCustomData


@Dao
interface CustomerDao {

    @Query("Select * from Customer")
    fun getAllCustomer(): LiveData<List<Customer>>

    @Query("SELECT * FROM Customer Where Customer.CustomerID = :id")
    fun getCustomerByID(id :String):LiveData<Customer>
    @Query("Select Equipments.EquipmentID, Equipments.SerialNumber, Equipments.Model ,Equipments.InstallationDate " +
            "From Equipments " +
            "where Equipments.CustomerID = :id")
    fun getDashboardEquipmentsByID(id:String):LiveData<List<DashboardCustomerEquipmentDataClass>>

    @Query("Select Contracts.ContractID,Contracts.Title , " +
            "Contracts.ContractStatus, Contracts.DateEnd ,Contracts.ContractType " +
            "From Contracts Where Contracts.CustomerID = :id ")
    fun getDashboardContractsByID(id :String):LiveData<List<DashboardCustomerContractsDataClass>>

    @Query("Select Tickets.TicketID , Tickets.Title, Tickets.Urgency , Tickets.DateStart , Tickets.DateEnd " +
            "From Tickets Where Tickets.CustomerID = :id ")
    fun getDashboardTechnicalCaseByID(id :String):LiveData<List<DashboardCustomerTechnicalCasesDataClass>>

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

    @Query("Select * from Contracts where Contracts.ContractID= :id")
    fun getContractsById(id:String):LiveData<Contracts>

    @Query ("Select CustomerID,Name as CustomerName from Customer")
    fun getCustomerID(): LiveData<List<CustomerSelect>>

    @Query("SELECT Contracts.CustomerID, Customer.Name AS CustomerName, Contracts.ContractID, " +
            "Contracts.Title, Contracts.DateStart, Contracts.DateEnd, Contracts.Value, " +
            "Contracts.Notes, Contracts.Description, Contracts.ContractType, " +
            "Contracts.ContractStatus, Contracts.ContactName " +
            "FROM Contracts LEFT JOIN Customer ON Contracts.CustomerID = Customer.CustomerID ")
    fun getContractsCustomerNames(): LiveData<List<ContractsCustomerName>>


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

    @Query ("Select * from ContractEquipments Where ContractEquipments.ContractID = :id")
    fun getContractEquipmentByID(id :String):LiveData<List<ContractEquipments>>
    @Query ("Select * from ContractEquipments Where ContractEquipments.ContractEquipmentID = :id")
    fun getContractEquipmentByEquipmentID(id :String):LiveData<ContractEquipments>

    @Query("SELECT COUNT(*) FROM ContractEquipments WHERE ContractID = :contractID AND EquipmentID = :equipmentID")
    fun count(contractID: String, equipmentID: String): Int

    @Insert
    fun addContractEquipments(contractEquipments:ContractEquipments)

    @Query("SELECT ContractEquipments.ContractID, " +
            "Equipments.EquipmentID as equipmentID, Equipments.SerialNumber as serialNumber, Equipments.Model as model, " +
            "ContractEquipments.ContractEquipmentID, " +
            "ContractEquipments.Value " +
            ", ContractEquipments.Visits " +
            ", ContractEquipments.LastModified ," +
            " ContractEquipments.DateCreated, " +
            "ContractEquipments.Version " +
            "FROM ContractEquipments " +
            "LEFT JOIN Equipments ON ContractEquipments.EquipmentID = Equipments.EquipmentID " +
            "WHERE ContractEquipments.ContractID = :id ")
    fun getDetailedContractByID(id: String):LiveData<List<DetailedContract>>



    @Update
    fun updateContractEquipments(contractEquipments:ContractEquipments)
    @Delete
    fun deleteContractEquipments(contractEquipments:ContractEquipments)

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
    @Query("Select * from Equipments where CustomerID = :customerId")
    fun getAllDataEquipmentsByCustomerID(customerId : String): LiveData<List<Equipments>>


    @Query
    ("Select CustomerID,Name as CustomerName from Customer")
    fun getCustomerID():LiveData<List<CustomerSelect>>
    @Query
    ("Select Equipments.CustomerID,Customer.Name AS CustomerName,Equipments.EquipmentID,Equipments.Name,Equipments.SerialNumber,Equipments.EquipmentStatus," +
     "Equipments.Model,Equipments.Manufacturer,Equipments.InstallationDate,Equipments.EquipmentCategory,Equipments.EquipmentVersion,Equipments.Warranty,Equipments.Description "+
     "From Equipments LEFT JOIN Customer ON Equipments.CustomerID = Customer.CustomerID"
    )
     fun getCustomerName():LiveData<List<EquipmentSelectCustomerName>>

    @Query(" Select EquipmentID,SerialNumber,Model,CustomerID " +
            " From Equipments " +
            " Where CustomerID= :Customerid ")
    fun selectEquipmentByCustomerID(Customerid : String) : LiveData<List<EquipmentListInCases>>

    @Query("Select * FROM Equipments WHERE EquipmentID= :id")
     fun SelectRecordById(id :String) : LiveData<Equipments>

     @Query("Select * from Tickets Where EquipmentID= :id")
     fun getTicketsByEquipmentId(id : String) : LiveData<List<Tickets>>

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

    @Query("Select * from FieldReportEquipment where FieldReportEquipmentID= :id ")
    fun getFieldReportEquipmentByID(id :String):LiveData<FieldReportEquipment>

    @Query (" Select Equipments.EquipmentID as EquipmentID, " +
            " Equipments.Model as Model, " +
            " Equipments.SerialNumber as SerialNumber, " +
            " FieldReportEquipment.CompletedStatus as CompletedStatus, " +
            " FieldReportEquipment.FieldReportEquipmentID as idFieldReportEquipment " +
            " From FieldReportEquipment " +
            " LEFT JOIN Equipments " +
            " Where FieldReportEquipment.EquipmentID=Equipments.EquipmentID AND " +
            " FieldReportEquipment.FieldReportID = :id ")
    fun getFieldReportEquipmentByFieldReportID(id :String ):LiveData<List<CustomDisplayDatFieldReportEquipments>>
    @Query("UPDATE FieldReportEquipment SET CompletedStatus = :value WHERE FieldReportEquipmentID = :id")
    fun updateCompletedStatus(value : Int, id :String)
    @Insert
    fun addFieldReportEquipment(fieldReportEquipment: FieldReportEquipment)

    @Update
    fun updateFieldReportEquipment(fieldReportEquipment: FieldReportEquipment)
    @Delete
    fun deleteFieldReportEquipment(fieldReportEquipment: FieldReportEquipment)

}

@Dao
interface FieldReportsDao {

    @Query("Select * from FieldReports")
    fun getAllFieldReports(): LiveData<List<FieldReports>>

    @Query("Select * from FieldReports Where FieldReportID= :id")
    fun getReportsByID(id :String) : LiveData<FieldReports>

    @Query("SELECT FieldReports.FieldReportID AS workOrderID " +
            ", FieldReports.ReportNumber AS reportNumber " +
            ",FieldReports.StartDate AS dateOpened " +
            ",FieldReports.Title AS title " +
            ",Customer.Name AS customerName " +
            " FROM FieldReports LEFT JOIN Customer ON FieldReports.CustomerID = Customer.CustomerID")
    fun getCustomerName():LiveData<List<WorkOrdersList>>

    @Query ("Select CustomerID,Name as CustomerName from Customer")
    fun getCustomerID():LiveData<List<CustomerSelect>>
    @Insert
    fun addFieldReports(fieldReports: FieldReports)

    @Update
    fun updateFieldReports(fieldReports: FieldReports)
    @Delete
    fun deleteFieldReports(fieldReports: FieldReports)

    @Query("Select Customer.Name as customerName, " +
            "FieldReports.ClientName as signeName, " +
            "FieldReports.Department as departmentWorkOrder, " +
            "FieldReports.Title as reportTitle, " +
            "FieldReports.Description as detailedReport , " +
            "FieldReports.StartDate as startDate, " +
            "FieldReports.EndDate as endDate, " +
            "FieldReports.ReportNumber as reportNumber, " +
            "Users.Name as usersName " +
            "from Customer " +
            "Left join FieldReports " +
            "Left join Users " +
            "Where Customer.CustomerID = FieldReports.CustomerID and " +
            "FieldReports.UserID = Users.UserID and FieldReports.FieldReportID =:id")
    fun printDetails(id :String):LiveData<CustomWorkOrderPDFDATA>

    @Query("Select FieldReportEquipment.EquipmentID as EquipmentId , " +
            "FieldReportEquipment.FieldReportEquipmentID as FieldEquipmentId , " +
            "FieldReportEquipment.FieldReportID as ReportId , " +
            "FieldReportCheckForm.FieldReportCheckFormID as FieldCheckListId, " +
            "FieldReportCheckForm.Description as FieldCheckListDescription, " +
            "FieldReportCheckForm.ValueMeasured as FieldCheckListMeasure , " +
            "FieldReportCheckForm.ValueExpected as FieldCheckListLimit , " +
            "FieldReportCheckForm.Result as FieldCheckListResult , " +
            "Equipments.Manufacturer as EquipmentManufacturer, " +
            "Equipments.Model as EquipmentModel, " +
            "Equipments.SerialNumber as EquipmentSerialNumber, " +
            "Equipments.EquipmentCategory as EquipmentCategory " +
            "from FieldReportEquipment " +
            "left join Equipments on Equipments.EquipmentID = FieldReportEquipment.EquipmentID " +
            "left join FieldReportCheckForm on FieldReportCheckForm.FieldReportEquipmentID = FieldReportEquipment.FieldReportEquipmentID " +
            "where  FieldReportEquipment.FieldReportID = :reportId "
    )
    fun printEquipmentWithCheckList(reportId :String):LiveData<List<CustomCheckListWithEquipmentData>>

    @Query("select Inventory.Description as description, " +
            "Inventory.Title as title, " +
            "FieldReportInventory.FieldReportID as fieldReportID, " +
            "FieldReportInventory.FieldReportInventoryID as fieldReportInventoryID, " +
            "FieldReportInventory.InventoryID as inventoryID " +
            "from FieldReportInventory " +
            "left join Inventory on Inventory.InventoryID =FieldReportInventory.InventoryID " +
            "where FieldReportInventory.FieldReportID = :id ")
    fun printInventoryDataByReportID(id :String):LiveData<List<FieldReportInventoryCustomData>>
    @Query("Select " +
            "Tools.Title as toolsTitle , " +
            " Tools.SerialNumber as toolsSerialNumber , " +
            "Tools.CalibrationDate as toolsCalDate, " +
            "FieldReportTools.ToolsID as toolsID, " +
            "FieldReportTools.FieldReportID as fieldReportID, " +
            "FieldReportTools.FieldReportToolsID as fieldReportToolsID " +
            "from FieldReportTools " +
            "left join Tools on Tools.ToolsID =FieldReportTools.ToolsID " +
            "where  FieldReportTools.FieldReportID= :id ")
    fun printToolsByReportID(id :String) :LiveData<List<FieldReportToolsCustomData>>
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

    @Query("Select * from Tickets where Tickets.TicketID= :id")
    fun getTicketsById(id: String): LiveData<Tickets>

    @Query ("Select CustomerID,Name as CustomerName from Customer")
    fun getCustomerID():LiveData<List<CustomerSelect>>

    @Query("Select Tickets.TicketID,Tickets.Title,Tickets.Active,Tickets.DateStart,Tickets.Urgency,Customer.Name AS CustomerName,Tickets.UserID, Equipments.SerialNumber AS SerialNumber , " +
            "Tickets.CustomerID,Tickets.EquipmentID "+
            "From Tickets " +
            "Left JOIN Customer ON Tickets.CustomerID = Customer.CustomerID " +
            "Left JOIN Equipments ON Tickets.EquipmentID = Equipments.EquipmentID "

    )
    fun getCustomerName():LiveData<List<TicketCustomerName>>
    @Query("Select Tickets.TicketID,Tickets.Title,Tickets.Active,Tickets.DateStart,Tickets.Urgency,Customer.Name AS CustomerName,Tickets.UserID, Equipments.SerialNumber AS SerialNumber , " +
            "Tickets.CustomerID,Tickets.EquipmentID ,Equipments.Model AS Model, Equipments.Manufacturer AS Manufacturer "+
            "From Tickets " +
            "Left JOIN Customer ON Tickets.CustomerID = Customer.CustomerID " +
            "Left JOIN Equipments ON Tickets.EquipmentID = Equipments.EquipmentID "

    )
    fun getTicketInformationCalendar():LiveData<List<TicketCalendar>>



    @Query
        ("Select Tickets.Urgency , Customer.Name as CustomerName ,Tickets.DateStart ," +
            "Tickets.DateEnd,Tickets.Title,Tickets.Description,"+
            "Tickets.UserID ,Tickets.EquipmentID , Tickets.TicketID ,Tickets.CustomerID "+
            "From Tickets LEFT JOIN Customer ON Tickets.CustomerID= Customer.CustomerID " )
    fun getDateForOverview():LiveData<List<OverviewMainData>>
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

    @Query("SELECT * FROM Users LIMIT 1")
    fun getFirstUser(): LiveData<Users>

    @Query("UPDATE Users SET LastReportNumber = :number WHERE UserID = :id")
    fun increaseLastReportNumber(number : Int ,id : String)

    @Query("SELECT * FROM Users WHERE UserID = :id ")
    fun getUserByID(id:String):LiveData<Users>

    @Insert
    fun addUsers(users: Users)

    @Update
    fun updateUsers(users: Users)
    @Delete
    fun deleteUsers(users: Users)

}


@Dao
interface FieldReportCheckFormsDao{
    @Insert
    fun insertFieldReportCheckForms(fieldReportCheckForm: FieldReportCheckForm)

    @Update
    fun updateFieldReportCheckForms(fieldReportCheckForm: FieldReportCheckForm)

    @Delete
    fun deleteFieldReportCheckForms(fieldReportCheckForm: FieldReportCheckForm)

    @Query(" Select * From FieldReportCheckFOrm where FieldReportEquipmentID= :id ")
    fun selectFieldReportCheckFormsByFieldReportEquipmentID(id :String) : LiveData<List<FieldReportCheckForm>>

}
@Dao
interface CheckFormsDao{
    @Insert
    fun addCheckFormsFields(checkForms: CheckForms)

    @Update
    fun updateCheckFormsFields(checkForms: CheckForms)

    @Delete
    fun deleteCheckFormsFields(checkForms : CheckForms)

    @Query ("Select * from CheckForms where MaintenancesID = :id")
    fun getCheckFormsFieldsByMaintenanceID(id :String) :LiveData<List<CheckForms>>

}
