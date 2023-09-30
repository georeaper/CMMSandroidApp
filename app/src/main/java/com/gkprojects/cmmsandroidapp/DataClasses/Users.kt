package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class Users(
    @PrimaryKey(autoGenerate = true) var UserID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :String? ,
    @ColumnInfo(name ="Name") var Name :String?,
    @ColumnInfo(name ="Description") var Description :String?,
    @ColumnInfo(name ="Email") var Email :String?,
    @ColumnInfo(name ="Phone") var Phone :String?,
    @ColumnInfo(name ="ReportPrefix") var ReportPrefix :String?,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?
)
