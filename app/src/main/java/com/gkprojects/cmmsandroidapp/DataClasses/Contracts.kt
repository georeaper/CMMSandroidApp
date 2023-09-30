package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Contracts" ,
    foreignKeys = [ForeignKey(entity = Customer::class,
        childColumns = ["CustomerID"],
        parentColumns = ["CustomerID"],
        onDelete = ForeignKey.CASCADE)])
data class Contracts(
    @PrimaryKey(autoGenerate = true) var ContractID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :String? ,
    @ColumnInfo(name ="Title") var Title :String?,
    @ColumnInfo(name ="DateStart") var DateStart :String?,
    @ColumnInfo(name ="DateEnd") var DateEnd :String?,
    @ColumnInfo(name ="Value") var Value :Double?,
    @ColumnInfo(name ="Notes") var Notes :String?,
    @ColumnInfo(name ="Description") var Description :String?,
    @ColumnInfo(name ="ContractType") var ContractType :String?,
    @ColumnInfo(name ="ContractStatus") var ContractStatus :String?,
    @ColumnInfo(name ="ContactName") var ContactName :String?,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?,
    @ColumnInfo( name="CustomerID")var CustomerID : Int?)
