package com.gkprojects.cmmsandroidapp.Fragments.SpecialTools

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportCheckForm
import com.gkprojects.cmmsandroidapp.DataClasses.Tools
import com.gkprojects.cmmsandroidapp.databinding.DialogChecklistWorkorderPerEquipmentRowBinding
import com.gkprojects.cmmsandroidapp.databinding.SpecialToolsRecyclerviewRowsBinding

class AdapterSpecialToolsRecyclerView(private var toolList :ArrayList<Tools>,
                                      private val fragment: SpecialTools):RecyclerView.Adapter<AdapterSpecialToolsRecyclerView.ToolsViewHolder>() {


    inner class ToolsViewHolder(private val binding: SpecialToolsRecyclerviewRowsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(tools : Tools, context: Context) {
            binding.specialToolsRecyclerviewRowCalDate.setText(tools.CalibrationDate.toString())
            binding.specialToolsRecyclerviewRowToolName.setText(tools.Title.toString())
            binding.specialToolsRecyclerviewRowBtn.setOnClickListener {
                //(context as SpecialTools).openDialogDisplayTools(tools)
                fragment.openDialogDisplayTools(tools)
            }

        }
    }
    fun getData(): ArrayList<Tools> {
        return toolList
    }
    fun setData(newList: ArrayList<Tools>) {
        toolList = newList
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
    fun filterList(text: String) {
        val filteredList = toolList.filter { it.Title!!.contains(text, ignoreCase = true) || it.SerialNumber!!.contains(text, ignoreCase = true) }
        setData(ArrayList(filteredList))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolsViewHolder {
        val binding= SpecialToolsRecyclerviewRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToolsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return toolList.size
    }

    override fun onBindViewHolder(holder: ToolsViewHolder, position: Int) {
        val tools = toolList[position]
        holder.bind(tools, holder.itemView.context)

    }
}