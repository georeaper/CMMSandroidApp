package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey


data class CustomerSelect( var CustomerID : Int,var CustomerName: String)



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
     var CustomerName: String?,
     var UserID :String?,
     var CustomerID :String?,
     var EquipmentID :String?

)
