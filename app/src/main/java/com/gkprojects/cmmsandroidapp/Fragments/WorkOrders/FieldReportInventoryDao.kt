package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportInventory

@Dao
interface FieldReportInventoryDao {

    @Query("Select * from FieldReportInventory")
    fun getAllFieldReportInventory(): LiveData<List<FieldReportInventory>>
    @Insert
    fun addFieldReportInventory(fieldReportInventory: FieldReportInventory)

    @Update
    fun updateFieldReportInventory(fieldReportInventory: FieldReportInventory)
    @Delete
    fun deleteFieldReportInventory(fieldReportInventory: FieldReportInventory)

}