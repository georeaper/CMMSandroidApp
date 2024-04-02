package com.gkprojects.cmmsandroidapp.Fragments.Inventory

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Inventory
import com.gkprojects.cmmsandroidapp.Fragments.SpecialTools.RepoSpecialTools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RepoInventory {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }

        fun getInventories(context: Context):LiveData<List<Inventory>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.InventoryDao().getAllInventory()
        }
        fun getSingleInventory(context: Context,id:String):LiveData<Inventory>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.InventoryDao().getSingleInventory(id)
        }
        fun insertInventory(context: Context,inventory: Inventory){
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
            inventory.DateCreated = dateFormat.format(currentDateTime)
            inventory.LastModified=dateFormat.format(currentDateTime)
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                RepoInventory.userDatabase!!.InventoryDao().addInventory(inventory)
            }
        }
        fun updateInventory(context: Context,inventory: Inventory){
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())

            inventory.LastModified=dateFormat.format(currentDateTime)
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                RepoInventory.userDatabase!!.InventoryDao().updateInventory(inventory)
            }
        }
        fun deleteInventory(context: Context,inventory: Inventory){
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                RepoInventory.userDatabase!!.InventoryDao().deleteInventory(inventory)
            }
        }
    }
}