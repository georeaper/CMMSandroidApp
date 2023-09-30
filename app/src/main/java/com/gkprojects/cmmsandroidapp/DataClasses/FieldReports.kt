package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "FieldReports" ,
    foreignKeys = [
        ForeignKey(entity = Customer::class,
        childColumns = ["CustomerID"],
        parentColumns = ["CustomerID"],
        onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Contracts::class,
            childColumns = ["ContractID"],
            parentColumns = ["ContractID"],
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Users::class,
            childColumns = ["UserID"],
            parentColumns = ["UserID"],
            onDelete = ForeignKey.CASCADE)      ])
data class FieldReports(
    @PrimaryKey(autoGenerate = true) var FieldReportID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :String? ,
    @ColumnInfo(name ="ReportNumber") var Name :String?,
    @ColumnInfo(name ="Description") var Description :String?,
    @ColumnInfo(name ="Title") var Title :String?,
    @ColumnInfo(name ="Value") var Value :Double?,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?,
    @ColumnInfo( name="CustomerID") var CustomerID : Int?,
    @ColumnInfo( name="ContractID") var ContractID : Int?  ,
    @ColumnInfo( name="UserID") var UserID : Int?)
