package com.gkprojects.cmmsandroidapp.DataClasses

data class EquipmentSelect(var name :String, var sn :String)

//data class EquipmentCustomerSelect( var hospitalID : Int,var name: String)
data class CustomerSelect( var CustomerID : Int,var CustomerName: String)

data class CasesList ( var  CasesTitle : String, var CasesId : Int, var CustomerId : Int,var status: String,var CustomerName :String)
