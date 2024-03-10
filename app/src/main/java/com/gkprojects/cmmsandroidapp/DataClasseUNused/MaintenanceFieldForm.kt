package com.gkprojects.cmmsandroidapp.DataClasseUNused

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReports

@Entity(tableName = "MaintenanceFieldForm",
    foreignKeys = [
        ForeignKey(entity = FieldReports::class,
            childColumns = ["FieldReportID"],
            parentColumns = ["FieldReportID"])
    ]
)
data class MaintenanceFieldForm(
    @PrimaryKey(autoGenerate = true) var MaintenanceFieldFormID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :Int? ,
    @ColumnInfo(name ="Title") var Title :String?,
    @ColumnInfo(name ="Description") var Description :String?,
    @ColumnInfo(name ="ValueExpected") var ValueExpected :String?,
    @ColumnInfo(name ="ValueMeasured") var ValueMeasured :String?,
    @ColumnInfo(name ="Result") var Result :String?,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?,
    @ColumnInfo(name ="FieldReportID") var FieldReportID :Int?
)
