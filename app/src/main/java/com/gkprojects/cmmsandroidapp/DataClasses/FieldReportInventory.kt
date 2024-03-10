package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "FieldReportInventory",
    foreignKeys = [
        ForeignKey(entity = FieldReports::class,
            childColumns = ["FieldReportID"],
            parentColumns = ["FieldReportID"],
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = Inventory::class,
            childColumns = ["InventoryID"],
            parentColumns = ["InventoryID"],
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)]
)
data class FieldReportInventory(
    @PrimaryKey var FieldReportInventoryID: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "RemoteID") var RemoteID: Int?,
    @ColumnInfo(name = "LastModified") var LastModified: String?,
    @ColumnInfo(name = "DateCreated") var DateCreated: String?,
    @ColumnInfo(name = "Version") var Version: String?,
    @ColumnInfo(name = "FieldReportID") var FieldReportID: String?,
    @ColumnInfo(name = "InventoryID") var InventoryID: String?
)
