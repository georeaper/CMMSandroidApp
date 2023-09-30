package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "FieldReportInventory",
    foreignKeys = [
        ForeignKey(entity = FieldReports::class,
            childColumns = ["FieldReportID"],
            parentColumns = ["FieldReportID"],
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Inventory::class,
            childColumns = ["InventoryID"],
            parentColumns = ["InventoryID"],
            onDelete = ForeignKey.CASCADE)]
)
data class FieldReportInventory (
    @PrimaryKey(autoGenerate = true) var FieldReportInventoryID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :String? ,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?,
    @ColumnInfo( name="FieldReportID")var FieldReportID : Int?,
    @ColumnInfo( name="InventoryID")var InventoryID : Int?
        )