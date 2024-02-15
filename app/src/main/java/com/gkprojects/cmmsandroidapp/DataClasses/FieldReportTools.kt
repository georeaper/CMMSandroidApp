package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FieldReportTools")
data class FieldReportTools(
    @PrimaryKey(autoGenerate = true) var FieldReportToolsID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :Int?,
    @ColumnInfo(name ="FieldReportID") var FieldReportID :Int?,
    @ColumnInfo(name ="ToolsID") var ToolsID :Int?,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?
)
