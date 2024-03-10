package com.gkprojects.cmmsandroidapp.Fragments.Inventory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Inventory

class InventoryVM:ViewModel() {

    fun insertInventory(context: Context,inventory: Inventory){
        RepoInventory.insertInventory(context, inventory)
    }
    fun updateInventory(context: Context,inventory: Inventory){
        RepoInventory.updateInventory(context, inventory)
    }
    fun deleteInventory(context: Context,inventory: Inventory){
        RepoInventory.deleteInventory(context, inventory)
    }
    fun getAllInventory(context: Context):LiveData<List<Inventory>>{
        return RepoInventory.getInventories(context)
    }
    fun getSingleInventory(context: Context,id:String):LiveData<Inventory>{
        return RepoInventory.getSingleInventory(context,id)
    }
}