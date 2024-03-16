package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportInventory
import com.gkprojects.cmmsandroidapp.databinding.WorkOrderInventoryRowBinding

class AdapterFieldReportRecyclerViewInventory(private var fieldReportInventoryList:ArrayList<FieldReportInventoryCustomData>):
RecyclerView.Adapter<AdapterFieldReportRecyclerViewInventory.FieldReportInventoryVH>(){
    inner class FieldReportInventoryVH(private val binding : WorkOrderInventoryRowBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(customData: FieldReportInventoryCustomData){

            binding.fieldReportSparepartsRecyclerviewRowDescription.setText(customData.title)
            binding.fieldReportSparepartsRecyclerviewRowPartnumber.setText(customData.description)

        }

    }
    fun getData(): ArrayList<FieldReportInventoryCustomData> {
        return fieldReportInventoryList
    }
    fun setData(newList: ArrayList<FieldReportInventoryCustomData>) {
        fieldReportInventoryList = newList
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldReportInventoryVH {
        val binding =WorkOrderInventoryRowBinding.inflate(LayoutInflater.from(parent.context)
        ,parent
        ,false)
        return FieldReportInventoryVH(binding)
    }

    override fun getItemCount(): Int {
        return fieldReportInventoryList.size
    }

    override fun onBindViewHolder(holder: FieldReportInventoryVH, position: Int) {
        val fieldInventory = fieldReportInventoryList[position]
        holder.bind(fieldInventory)
    }
}