package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tools" )
data class Tools(
    @PrimaryKey(autoGenerate = true) var ToolsID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :Int? ,
    @ColumnInfo(name ="Title") var Title :String?,
    @ColumnInfo(name ="Description") var Description :String?,
    @ColumnInfo(name ="Model") var Model :String?,
    @ColumnInfo(name ="Manufacturer") var Manufacturer :String?,
    @ColumnInfo(name ="SerialNumber") var SerialNumber :String?,
    @ColumnInfo(name ="CalibrationDate") var CalibrationDate :String?,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?

    )
