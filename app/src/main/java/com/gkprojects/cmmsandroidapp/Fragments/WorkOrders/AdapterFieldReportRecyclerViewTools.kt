package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.Tools
import com.gkprojects.cmmsandroidapp.databinding.WorkOrderToolsRowBinding

class AdapterFieldReportRecyclerViewTools(private var fieldReportToolsList : ArrayList<FieldReportToolsCustomData>):
    RecyclerView.Adapter<AdapterFieldReportRecyclerViewTools.FieldReportToolsViewHolder>() {
    inner class FieldReportToolsViewHolder(private val binding : WorkOrderToolsRowBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(customData: FieldReportToolsCustomData){
           binding.fieldReportToolsRecyclerviewRowToolName.setText(customData.toolsTitle)
           binding.fieldReportToolsRecyclerviewRowCalDate.setText(customData.toolsCalDate)

        }
    }
    fun getData(): ArrayList<FieldReportToolsCustomData> {
        return fieldReportToolsList
    }
    fun setData(newList: ArrayList<FieldReportToolsCustomData>) {
        fieldReportToolsList = newList
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldReportToolsViewHolder {
        val binding =WorkOrderToolsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FieldReportToolsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return fieldReportToolsList.size
    }

    override fun onBindViewHolder(holder: FieldReportToolsViewHolder, position: Int) {
        val fieldReportTools =fieldReportToolsList[position]
        holder.bind(fieldReportTools)
    }
}