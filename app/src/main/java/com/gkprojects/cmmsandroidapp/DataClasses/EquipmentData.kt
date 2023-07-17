package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "hospitals")
data class Hospital(
    @PrimaryKey(autoGenerate = true) val hospitalID: Int? ,

    @ColumnInfo(name="name" , defaultValue ="s" )
    val name: String,
    @ColumnInfo(name="address" , defaultValue ="a" )
    val address: String,
    @ColumnInfo(name="city" , defaultValue ="s" )
    val city: String,
//    val state: String,
    @ColumnInfo(name="zipCode" , defaultValue ="s" )
    val zipCode: String,
    @ColumnInfo(name="contactPerson" , defaultValue ="s" )
    val contactPerson: String,
    @ColumnInfo(name="contactEmail" , defaultValue ="s" )
    val contactEmail: String,
    @ColumnInfo(name="contactPhone" , defaultValue ="s" )
    val contactPhone: String
)

@Entity(
    tableName = "equipment",
    foreignKeys = [
        ForeignKey(
            entity = Hospital::class,
            parentColumns = ["hospitalID"],
            childColumns = ["hospitalID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Equipment(
    @PrimaryKey(autoGenerate = true) val equipmentID: Int?,
    @ColumnInfo(name="hospitalID" , defaultValue ="s" )
    val hospitalID:  Int?,
    @ColumnInfo(name="category" , defaultValue ="s" )
    val category: String,
    @ColumnInfo(name="manufacturer" , defaultValue ="s" )
    val manufacturer: String,
    @ColumnInfo(name="model" , defaultValue ="s" )
    val model: String,
    @ColumnInfo(name="serialNumber" , defaultValue ="s" )
    val serialNumber: String,
    @ColumnInfo(name="installationDate" , defaultValue ="s" )
    val installationDate: String,
    @ColumnInfo(name="warrantyExpirationDate" , defaultValue ="s" )
    val warrantyExpirationDate: String,
    @ColumnInfo(name="lastMaintenanceDate" , defaultValue ="s" )
    val lastMaintenanceDate: String,
    @ColumnInfo(name="nextMaintenanceDueDate" , defaultValue ="s" )
    val nextMaintenanceDueDate: String,
    @ColumnInfo(name="status" , defaultValue ="s" )
    val status: String
)

@Entity(
    tableName = "contracts",
    foreignKeys = [
        ForeignKey(
            entity = Hospital::class,
            parentColumns = ["hospitalID"],
            childColumns = ["hospitalID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Contract(
    @PrimaryKey(autoGenerate = true) val contractID: Int?,
    @ColumnInfo(name="hospitalID" , defaultValue ="s" )
    val hospitalID: String,
    @ColumnInfo(name="startDate" , defaultValue ="s" )
    val startDate: String,
    @ColumnInfo(name="endDate" , defaultValue ="s" )
    val endDate: String,
    @ColumnInfo(name="contractType" , defaultValue ="s" )
    val contractType: String,
    @ColumnInfo(name="contractValue" , defaultValue ="s" )
    val contractValue: Double,
    @ColumnInfo(name="contractStatus" , defaultValue ="s" )
    val contractStatus: String
)

@Entity(
    tableName = "contract_equipment",
    foreignKeys = [
        ForeignKey(
            entity = Contract::class,
            parentColumns = ["contractID"],
            childColumns = ["contractID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["equipmentID"],
            childColumns = ["equipmentID"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    primaryKeys = ["contractID", "equipmentID"]
)


data class ContractEquipment(
    val contractID: Int,
    val equipmentID: Int
)

@Entity(
    tableName = "maintenance",
    foreignKeys = [
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["equipmentID"],
            childColumns = ["equipmentID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Maintenance(
    @PrimaryKey(autoGenerate = true) val maintenanceID: Int?,
    val equipmentID: String,
    val maintenanceDate: String,
    val technicianName: String,
    val description: String,
    val cost: Double,
    val notes: String
)

@Entity(
    tableName = "spare_parts",
    foreignKeys = [
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["equipmentID"],
            childColumns = ["equipmentID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SparePart(
    @PrimaryKey(autoGenerate = true) val partID: Int?,
    val equipmentID: Int?,
    val name: String,
    val manufacturer: String,
    val model: String,
    val quantity: Int,
    val cost: Double,
    val lastRestockDate: String,
    val supplier: String
)
@Entity(
    tableName = "cases",
    foreignKeys = [
        ForeignKey(
            entity = Hospital::class,
            parentColumns = ["hospitalID"],
            childColumns = ["customerID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Cases(
    @PrimaryKey(autoGenerate = true) val caseID: Int?,
//                 val equipmentID: String,
    @ColumnInfo(name="title" , defaultValue ="s" )
    val title: String,
    @ColumnInfo(name="startDate" , defaultValue ="s" )
    val startDate: String,
    @ColumnInfo(name="endDate" , defaultValue ="s" )
    val endDate: String,
    @ColumnInfo(name="comments" , defaultValue ="s" )
    val comments: String,
    @ColumnInfo(name="userID" , defaultValue ="2" )
    val userID: Int,
    @ColumnInfo(name="customerID" , defaultValue ="1" )
    val customerID: Int
    ,
    @ColumnInfo(name="active" , defaultValue ="s" )
    val active: String,
    @ColumnInfo(name="status" , defaultValue ="s" )
    val status: String)
