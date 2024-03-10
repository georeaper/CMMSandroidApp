package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "FieldReports",
    foreignKeys = [
        ForeignKey(entity = Customer::class,
            childColumns = ["CustomerID"],
            parentColumns = ["CustomerID"],
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = Users::class,
            childColumns = ["UserID"],
            parentColumns = ["UserID"],
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = Contracts::class,
            childColumns = ["ContractID"],
            parentColumns = ["ContractID"],
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = Tickets::class,
            childColumns = ["CaseID"],
            parentColumns = ["TicketID"],
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    ])
data class FieldReports(
    @PrimaryKey var FieldReportID: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "RemoteID") var RemoteID: Int?,
    @ColumnInfo(name = "ReportNumber") var ReportNumber: String?,
    @ColumnInfo(name = "Description") var Description: String?,
    @ColumnInfo(name = "StartDate") var StartDate: String?,
    @ColumnInfo(name = "EndDate") var EndDate: String?,
    @ColumnInfo(name = "Title") var Title: String?,
    @ColumnInfo(name = "Department") var Department: String?,
    @ColumnInfo(name = "ClientName") var ClientName: String?,
    @ColumnInfo(name = "ReportStatus") var ReportStatus: Boolean?,
    @ColumnInfo(name = "ClientSignature") var ClientSignature: ByteArray?,
    @ColumnInfo(name = "Value") var Value: Double?,
    @ColumnInfo(name = "LastModified") var LastModified: String?,
    @ColumnInfo(name = "DateCreated") var DateCreated: String?,
    @ColumnInfo(name = "Version") var Version: String?,
    @ColumnInfo(name = "CustomerID") var CustomerID: String?,
    @ColumnInfo(name = "ContractID") var ContractID: String?,
    @ColumnInfo(name = "UserID") var UserID: String?,
    @ColumnInfo(name = "CaseID") var CaseID: String?
)