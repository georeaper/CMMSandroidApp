package com.gkprojects.cmmsandroidapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentSelectCustomerName

import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class EquipmentAdapter(private val context: Context, private var equipmentlist:List<EquipmentSelectCustomerName>):RecyclerView.Adapter<EquipmentAdapter.MyViewHolder>() {

    private var onClickListener: EquipmentAdapter.OnClickListener? = null




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_equipment,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return equipmentlist.size
    }
    fun setData(equipmentlist:ArrayList<EquipmentSelectCustomerName>)
    {
        this.equipmentlist=equipmentlist
        notifyDataSetChanged()
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: EquipmentSelectCustomerName)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentitem = equipmentlist[position]

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentitem )
            }
        }
            holder.sn.text = currentitem.SerialNumber.toString()
            holder.model.text="Model: " + currentitem.Model.toString()
            holder.installationDate.text="Installation Date: "+currentitem.InstallationDate.toString()
            holder.customerName.text=currentitem.CustomerName.toString()


    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val sn: TextView = itemView.findViewById(R.id.serialNumberEquipmentList)
        val model :TextView =itemView.findViewById(R.id.modelEquipmentList)
        val installationDate :TextView=itemView.findViewById(R.id.installationDateEquipmentList)
        val customerName :TextView =itemView.findViewById(R.id.customerNameEquipmentList)







    }
}