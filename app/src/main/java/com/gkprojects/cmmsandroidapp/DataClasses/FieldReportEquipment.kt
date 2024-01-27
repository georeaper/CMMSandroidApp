package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "FieldReportEquipment",
    foreignKeys = [
    ForeignKey(entity = FieldReports::class,
        childColumns = ["FieldReportID"],
        parentColumns = ["FieldReportID"],
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Equipments::class,
        childColumns = ["EquipmentID"],
        parentColumns = ["EquipmentID"],
        onDelete = ForeignKey.CASCADE) ]
    )
data class FieldReportEquipment(
    @PrimaryKey(autoGenerate = true) var FieldReportEquipmentID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :Int? ,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?,
    @ColumnInfo(name ="FieldReportID") var UserID :Int?,
    @ColumnInfo(name ="EquipmentID") var CustomerID :Int?,
    @ColumnInfo(name ="MaintenanceID") var MaintenanceID :Int?
)
