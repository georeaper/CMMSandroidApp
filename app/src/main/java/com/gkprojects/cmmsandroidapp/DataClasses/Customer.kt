package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "Customer")
data class Customer(
    @PrimaryKey(autoGenerate = true) var CustomerID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :String? ,
    @ColumnInfo(name ="Name") var Name :String? ,
    @ColumnInfo(name ="Phone") var Phone :String? ,
    @ColumnInfo(name ="Email") var Email :String? ,
    @ColumnInfo(name ="Address") var Address :String? ,
    @ColumnInfo(name ="ZipCode") var ZipCode :String? ,
    @ColumnInfo(name ="City") var City :String? ,
    @ColumnInfo(name ="Notes") var Notes :String? ,
    @ColumnInfo(name ="Description") var Description :String? ,
    @ColumnInfo(name ="CustomerStatus") var CustomerStatus :String? ,
    @ColumnInfo(name ="LastModified") var LastModified :String? ,
    @ColumnInfo(name ="DateCreated") var DateCreated :String? ,
    @ColumnInfo(name ="Version") var Version :String? )

