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
    @ColumnInfo(name ="RemoteID") var RemoteID :Int? ,
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
    @ColumnInfo(name ="UserID") var UserID :Int?, //changed 2-3
    @ColumnInfo(name ="CustomerID") var CustomerID :Int?,  //changed 3-4
    @ColumnInfo(name ="EquipmentID") var EquipmentID :Int? // changed 4-5
)
