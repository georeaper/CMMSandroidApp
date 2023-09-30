package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "Inventory",
//    foreignKeys = [
//        ForeignKey(entity = Equipments::class,
//            childColumns = ["EquipmentID"],
//            parentColumns = ["EquipmentID"],
//            onDelete = ForeignKey.CASCADE),
//        ForeignKey(entity = Contracts::class,
//            childColumns = ["ContractID"],
//            parentColumns = ["ContractID"],
//            onDelete = ForeignKey.CASCADE)]
)
data class Inventory(
    @PrimaryKey(autoGenerate = true) var InventoryID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :String? ,
    @ColumnInfo(name ="Title") var Title :Int?,
    @ColumnInfo(name ="Description") var Description :Int?,
    @ColumnInfo(name ="Quantity") var Quantity :Long?,
    @ColumnInfo(name ="Value") var Value :Double?,
    @ColumnInfo(name ="Type") var Type :String?,

    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?,
)
