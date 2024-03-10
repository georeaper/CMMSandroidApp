package com.gkprojects.cmmsandroidapp.Fragments.Inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.Inventory

import com.gkprojects.cmmsandroidapp.databinding.InventoryRecyclerviewRowBinding


class AdapterInventoryRecyclerView(private var inventoryList : ArrayList<Inventory>,
                                   private val fragment: InventoryFragment) :RecyclerView.Adapter<AdapterInventoryRecyclerView.InventoryViewHolder>() {


    inner class InventoryViewHolder(private var binding:InventoryRecyclerviewRowBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(inventory : Inventory){
            binding.inventoryRecyclerviewRowPartnumber.setText(inventory.Title.toString())
            binding.inventoryRecyclerviewRowDescription.setText(inventory.Description.toString())
            binding.inventoryRecyclerviewRowBtn.setOnClickListener {
                fragment.openDialogDisplayTools(inventory)
            }

        }
    }
    fun getData(): ArrayList<Inventory> {
        return inventoryList
    }
    fun setData(newList: ArrayList<Inventory>) {
        inventoryList = newList
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val binding= InventoryRecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InventoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return inventoryList.size
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val inventory = inventoryList[position]
        holder.bind(inventory)
    }
}