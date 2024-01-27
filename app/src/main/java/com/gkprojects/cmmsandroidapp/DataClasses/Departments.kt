package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity (tableName = "Departments" ,
    foreignKeys = [ForeignKey(entity = Customer::class,
    childColumns = ["CustomerID"],
    parentColumns = ["CustomerID"],
    onDelete = ForeignKey.CASCADE)])
data class Departments(
    @PrimaryKey(autoGenerate = true) var DeparmentID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :Int? ,
    @ColumnInfo(name ="Name") var Name :String? ,
    @ColumnInfo(name ="Phone") var Phone :String? ,
    @ColumnInfo(name ="Email") var Email :String? ,
    @ColumnInfo(name ="ContactPerson") var ContactPerson :String? ,
    @ColumnInfo(name ="Notes") var Notes :String? ,
    @ColumnInfo(name ="Description") var Description :String? ,
    @ColumnInfo(name ="CustomerStatus") var DepartmentStatus :Boolean? ,
    @ColumnInfo(name ="LastModified") var LastModified :String? ,
    @ColumnInfo(name ="DateCreated") var DateCreated :String? ,
    @ColumnInfo(name ="Version") var Version :String? ,
    @ColumnInfo( name="CustomerID")var CustomerID : Int?)