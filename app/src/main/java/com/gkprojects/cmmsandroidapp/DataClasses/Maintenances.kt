package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "Maintenances" ,
    foreignKeys = [ForeignKey(entity = Equipments::class,
        childColumns = ["EquipmentID"],
        parentColumns = ["EquipmentID"],
        onDelete = ForeignKey.CASCADE)])
data class Maintenances(
    @PrimaryKey(autoGenerate = true) var MaintenanceID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :String? ,
    @ColumnInfo(name ="Name") var Name :String?,
    @ColumnInfo(name ="Description") var Description :String?,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?,
    @ColumnInfo( name="EquipmentID") var EquipmentID : Int?)

