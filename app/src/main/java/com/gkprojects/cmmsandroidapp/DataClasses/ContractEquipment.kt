package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "ContractEquipments",
    foreignKeys = [
        ForeignKey(entity = Equipments::class,
            childColumns = ["EquipmentID"],
            parentColumns = ["EquipmentID"],
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Contracts::class,
            childColumns = ["ContractID"],
            parentColumns = ["ContractID"],
            onDelete = ForeignKey.CASCADE)]
)
data class ContractEquipments(
    @PrimaryKey(autoGenerate = true) var ContractEquipmentID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :Int? ,
    @ColumnInfo( name="Value")var Value : Double?,
    @ColumnInfo( name="Visits")var Visits : Double?,
    @ColumnInfo( name="ContractID")var ContractID : Int?,
    @ColumnInfo( name="EquipmentID")var EquipmentID : Int?,
    @ColumnInfo(name ="LastModified") var LastModified :String? ,
    @ColumnInfo(name ="DateCreated") var DateCreated :String? ,
    @ColumnInfo(name ="Version") var Version :String?
)
