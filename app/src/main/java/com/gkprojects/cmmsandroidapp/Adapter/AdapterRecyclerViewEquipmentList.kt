package com.gkprojects.cmmsandroidapp.Adapter

import android.service.autofill.CustomDescription
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.CheckForms
import com.gkprojects.cmmsandroidapp.DataClasses.CustomDisplayDatFieldReportEquipments
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentListCheckOut
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.DialogCheckformFieldsRowBinding
import com.gkprojects.cmmsandroidapp.databinding.WorkOrdersEquipmentListRowBinding

class AdapterRecyclerViewEquipmentList(private var equipmentList : ArrayList<CustomDisplayDatFieldReportEquipments>):RecyclerView.Adapter<AdapterRecyclerViewEquipmentList.MyViewHolder>() {
    var listener: OnItemClickListener? = null

    inner class MyViewHolder(private val binding :WorkOrdersEquipmentListRowBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(equipmentsCheckOut : CustomDisplayDatFieldReportEquipments){
            binding.workOrderEquipmentListRowSn.text=equipmentsCheckOut.SerialNumber
            binding.workOrderEquipmentListRowModel.text=equipmentsCheckOut.Model

            if (equipmentsCheckOut.CompletedStatus!!) {
                binding.workOrderEquipmentListRowIMG.setImageResource(R.drawable.completed_order_svgrepo_com)
            } else {
                binding.workOrderEquipmentListRowIMG.setImageResource(R.drawable.attention_svgrepo_com)
            }
            binding.root.setOnClickListener {
                listener?.onItemClick(equipmentsCheckOut)
            }

        }
    }
    interface OnItemClickListener {
        fun onItemClick(equipment: CustomDisplayDatFieldReportEquipments)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setData(equipmentCheckOut: ArrayList<CustomDisplayDatFieldReportEquipments>)
    {
        this.equipmentList=equipmentCheckOut
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding= WorkOrdersEquipmentListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return equipmentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val equipment = equipmentList[position]
        holder.bind(equipment)

    }
}