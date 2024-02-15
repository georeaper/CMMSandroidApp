package com.gkprojects.cmmsandroidapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.CheckForms
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentListCheckOut
import com.gkprojects.cmmsandroidapp.Models.CheckFormVM

import com.gkprojects.cmmsandroidapp.databinding.DialogCheckformFieldsRowBinding

import java.util.ArrayList

class AdapterCheckFormsFields(private var listFields : ArrayList<CheckForms>):RecyclerView.Adapter<AdapterCheckFormsFields.MyViewHolder>() {

    var listener: OnItemClickListener? = null

    inner class MyViewHolder(private val binding: DialogCheckformFieldsRowBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(checkForms : CheckForms){
            binding.dialogCheckFormsFieldsRowDescription.text=checkForms.Description
            binding.dialogCheckFormsFieldsRowValueExpected.text=checkForms.ValueExpected
            binding.dialogCheckFormsFieldsRowTypeValue.text=checkForms.ValueType


            binding.dialogCheckFormsFieldsRowImageBtn.setOnClickListener {
                listener?.onDeleteClick(checkForms)
            }

        }
    }
    interface OnItemClickListener {

        fun onDeleteClick(checkForms: CheckForms)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding=DialogCheckformFieldsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFields.size
    }
    fun setData(checkOutFields:ArrayList<CheckForms>)
    {
        this.listFields=checkOutFields
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listFields[position])
    }
}