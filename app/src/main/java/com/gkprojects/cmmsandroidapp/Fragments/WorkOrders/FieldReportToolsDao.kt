package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportTools

@Dao
interface FieldReportToolsDao {
    @Query("Select " +
            "Tools.Title as toolsTitle , " +
            " Tools.SerialNumber as toolsSerialNumber , " +
            "Tools.CalibrationDate as toolsCalDate, " +
            "FieldReportTools.ToolsID as toolsID, " +
            "FieldReportTools.FieldReportID as fieldReportID, " +
            "FieldReportTools.FieldReportToolsID as fieldReportToolsID " +
            "from FieldReportTools " +
            "left join Tools on Tools.ToolsID =FieldReportTools.ToolsID " +
            "where  FieldReportTools.FieldReportID= :id ")
    fun getFieldReportToolsByID(id :String) :LiveData<List<FieldReportToolsCustomData>>

    @Insert
    fun insertFieldReportTool(fieldReportTools: FieldReportTools)

    @Delete
    fun deleteFieldReportTool(fieldReportTools: FieldReportTools)

    @Update
    fun updateFieldReportTools(fieldReportTools: FieldReportTools)
}