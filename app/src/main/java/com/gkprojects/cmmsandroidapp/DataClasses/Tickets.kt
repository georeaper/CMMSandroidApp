package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Tickets",
    foreignKeys = [
                ForeignKey(entity = Customer::class,
            childColumns = ["CustomerID"],
            parentColumns = ["CustomerID"],
            onDelete = ForeignKey.CASCADE)
        ]
)
data class Tickets(
    @PrimaryKey(autoGenerate = true) var TicketID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :String? ,
    @ColumnInfo(name ="Title") var Title :String?,
    @ColumnInfo(name ="Description") var Description :String?,
    @ColumnInfo(name ="Notes") var Notes :String?,
    @ColumnInfo(name ="Urgency") var Urgency :String?,
    @ColumnInfo(name ="Active") var Active :Boolean?,
    @ColumnInfo(name ="DateStart") var DateStart: String?,
    @ColumnInfo(name ="DateEnd") var DateEnd :String?,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?,
    @ColumnInfo(name ="UserID") var UserID :String?,
    @ColumnInfo(name ="CustomerID") var CustomerID :String?,
    @ColumnInfo(name ="EquipmentID") var EquipmentID :String?
)
