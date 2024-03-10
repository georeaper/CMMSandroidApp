package com.gkprojects.cmmsandroidapp.Fragments.SpecialTools

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gkprojects.cmmsandroidapp.DataClasses.Tools

@Dao
interface ToolsDao{

    @Query("Select * from Tools")
    fun getAllTools(): LiveData<List<Tools>>

    @Query("Select * from Tools Where ToolsID= :id ")
    fun getSingleTool(id :String):LiveData<Tools>

    @Update
    fun updateTools(tool : Tools)

    @Insert
    fun insertTools(tool : Tools)

    @Delete
    fun deleteTools(tools: Tools)

}