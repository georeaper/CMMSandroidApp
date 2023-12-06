package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.CasesAdapter
import com.gkprojects.cmmsandroidapp.Adapter.CustomerAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.ContractsCustomerName
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.DetailedContract
import com.gkprojects.cmmsandroidapp.Models.CasesVM
import com.gkprojects.cmmsandroidapp.R

class ContractInsertEquipmentListAdapter(private var list : ArrayList<DetailedContract>) : RecyclerView.Adapter<ContractInsertEquipmentListAdapter.MyViewholder>(){
    private var onClickListener: ContractInsertEquipmentListAdapter.OnClickListener? = null

    class MyViewholder(itemView : View):RecyclerView.ViewHolder(itemView) {
        val serialNumber : TextView=itemView.findViewById(R.id.contract_insert_equipmentList_tvSerialNumber)
        val model : TextView=itemView.findViewById(R.id.contract_insert_equipmentList_tvModel)
        val visits : TextView=itemView.findViewById(R.id.contract_insert_equipmentList_tvVisits)
        val deletebtn : ImageButton=itemView.findViewById(R.id.contract_insert_equipmentList_btnDelete)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.contract_insert_equipment_list_row,parent,false)
        return ContractInsertEquipmentListAdapter.MyViewholder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener {
        fun onDeleteItem(position: Int, model: DetailedContract)
    }
    fun setOnClickListener(onClickListener: ContractInsertEquipmentListAdapter.OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val item = list[position]
        holder.serialNumber.text = item.serialNumber
        holder.model.text = item.model
        holder.visits.text = item.Visits.toString()
        holder.deletebtn.setOnClickListener {
            // Implement item deletion logic here
            if (onClickListener != null) {
                onClickListener!!.onDeleteItem(position, item )
            }

        }
    }
    fun removeItemAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setData(contractList: ArrayList<DetailedContract>)
    {
        this.list=contractList
        notifyDataSetChanged()
    }


}