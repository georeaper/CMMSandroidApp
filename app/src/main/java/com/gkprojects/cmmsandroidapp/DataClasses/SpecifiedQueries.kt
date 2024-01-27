package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey


data class CustomerSelect( var CustomerID : Int,
                           var CustomerName: String)



data class EquipmentSelectCustomerName ( var CustomerID :Int? ,
                                         var CustomerName : String? ,
                                         var EquipmentID : Int? ,
                                         var Name: String?,
                                         var SerialNumber: String?,
                                         var EquipmentStatus: String?,
                                         var Model: String?,
                                         var Manufacturer: String?,
                                         var InstallationDate: String?,
                                         var EquipmentCategory: String?,
                                         var EquipmentVersion: String?,
                                         var Warranty : String?   ,
                                         var Description : String?)

data class ContractsCustomerName(
    var CustomerID :Int?,
    var CustomerName : String? ,
    var ContractID :Int?,
    var Title :String?,
    var DateStart :String?,
    var DateEnd :String?,
    var Value :Double?,
    var Notes :String?,
    var Description :String?,
    var ContractType :String?,
    var ContractStatus :String?,
    var ContactName :String?,
)

data class TicketCustomerName(
     var TicketID :Int?,
     var Title :String?,
     var Active :String?,
     var DateStart: String?,
     var Urgency :String?,
     var CustomerName: String?,
     var UserID :String?,
     var SerialNumber : String?,
     var CustomerID :String?,
     var EquipmentID :String?

)
data class TicketCalendar(
    var TicketID :Int?,
    var Title :String?,
    var Active :String?,
    var DateStart: String?,
    var Urgency :String?,
    var CustomerName: String?,
    var UserID :String?,
    var SerialNumber : String?,
    var CustomerID :String?,
    var EquipmentID :String?,
    var Model: String?,
    var Manufacturer: String?
)
data class DashboardCustomerEquipmentDataClass(
    var EquipmentID: Int?,
    var SerialNumber: String?,
    var Model: String?,
    var InstallationDate: String?
)
data class DashboardCustomerTechnicalCasesDataClass(
    var TicketID: Int?,
    var Title: String?,
    var Urgency: String?,
    var DateStart: String?,
    var DateEnd :String?

)
data class DashboardCustomerWorkOrdersDataClass(
    var FieldReportID : Int?,
    var ReportNumber : String?,
    var Title: String?,
    var DateCreated: String?
)
data class DashboardCustomerContractsDataClass(
    var ContractID: Int?,
    var Title: String?,
    var ContractStatus: String?,
    var DateEnd: String?,
    var ContractType: String?

)
data class EquipmentListInCases(
    var EquipmentID :Int?,
    var SerialNumber :String?,
    var Model :String?,
    var CustomerID : Int?
) {
    override fun toString(): String {
        return SerialNumber ?: ""
    }
}
data class DetailedContract(
    var ContractID: Int?,
    var equipmentID: Int?,
    var serialNumber: String?,
    var model: String?,
    var ContractEquipmentID : Int?,
    var Value : Double?,
    var Visits : String?,
    var LastModified : String?,
    var DateCreated: String?,
    var Version : String?

)

