package com.gkprojects.cmmsandroidapp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FieldReportCheckFOrm")
data class FieldReportCheckForm(
    @PrimaryKey(autoGenerate = true) var FieldReportCheckFormID :Int?,
    @ColumnInfo(name ="RemoteID") var RemoteID :Int?,
    @ColumnInfo(name ="FieldReportEquipmentID") var FieldReportEquipmentID :Int?,
    @ColumnInfo(name ="Description") var Description :String?,
    @ColumnInfo(name ="ValueExpected") var ValueExpected :String?,
    @ColumnInfo(name ="ValueMeasured") var ValueMeasured :String?,
    @ColumnInfo(name ="Result") var Result :String?,
    @ColumnInfo(name ="LastModified") var LastModified :String?,
    @ColumnInfo(name ="DateCreated") var DateCreated :String?,
    @ColumnInfo(name ="Version") var Version :String?

)
