package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "MaintenanceInventory",
    foreignKeys = [
        ForeignKey(entity = Maintenances::class,
            childColumns = ["MaintenanceID"],
            parentColumns = ["MaintenanceID"],
            onDelete = ForeignKey.CASCADE)]

)
data class MaintenanceInventory (
    @PrimaryKey(autoGenerate = true) var FieldReportInventoryID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :String? ,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?,
    @ColumnInfo( name="MaintenanceID")var FieldReportID : Int?,
    @ColumnInfo( name="InventoryID")var InventoryID : Int?
)
