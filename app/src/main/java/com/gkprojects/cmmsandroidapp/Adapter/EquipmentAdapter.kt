package com.gkprojects.cmmsandroidapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.Equipments
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.R

class EquipmentAdapter(private val context: Context, private var equipmentlist:List<Equipments>):RecyclerView.Adapter<EquipmentAdapter.MyViewHolder>() {

    private var onClickListener: EquipmentAdapter.OnClickListener? = null




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_equipment,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return equipmentlist.size
    }
    fun setData(equipmentlist:ArrayList<Equipments>)
    {
        this.equipmentlist=equipmentlist
        notifyDataSetChanged()
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Equipments)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentitem = equipmentlist[position]
        holder.titlesn.text = currentitem.SerialNumber
        holder.model.text = currentitem.Model
        holder.category.text = currentitem.EquipmentCategory
        holder.customer.text =(currentitem.CustomerID.toString())
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentitem )
            }
        }
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val titlesn: TextView = itemView.findViewById(R.id.tv_equip_sn)
        val model :TextView =itemView.findViewById(R.id.tv_equip_model)
        val category :TextView=itemView.findViewById(R.id.tv_equip_category)
        val customer :TextView =itemView.findViewById(R.id.tv_equip_customer)


    }
}