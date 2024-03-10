package com.gkprojects.cmmsandroidapp.Fragments.Inventory

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gkprojects.cmmsandroidapp.DataClasses.Inventory

@Dao
interface InventoryDao {

    @Query("Select * from Inventory")
    fun getAllInventory(): LiveData<List<Inventory>>

    @Query("Select * from Inventory where InventoryID=:id")
    fun getSingleInventory(id : String ):LiveData<Inventory>
    @Insert
    fun addInventory(inventory: Inventory)

    @Update
    fun updateInventory(inventory: Inventory)
    @Delete
    fun deleteInventory(inventory: Inventory)

}