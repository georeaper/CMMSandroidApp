package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gkprojects.cmmsandroidapp.Adapter.CustomerAdapter

import com.gkprojects.cmmsandroidapp.DataClasses.Contracts
import com.gkprojects.cmmsandroidapp.DataClasses.ContractsCustomerName

import com.gkprojects.cmmsandroidapp.R

class ContractAdapter(private var contractList :ArrayList<ContractsCustomerName> ) :RecyclerView.Adapter<ContractAdapter.MyViewHolder>() {
    private var onClickListener: ContractAdapter.OnClickListener? = null

    class MyViewHolder(itemView :View) :RecyclerView.ViewHolder( itemView) {
        val title = itemView.findViewById<TextView>(R.id.titleContractList)
        val customerName = itemView.findViewById<TextView>(R.id.customerNameContractList)
        val startDate = itemView.findViewById<TextView>(R.id.startDateContractList)
        val endDate = itemView.findViewById<TextView>(R.id.EndDateContractList)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_recycler_view_contract,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return contractList.size
    }
    fun setData(contractList: ArrayList<ContractsCustomerName>)
    {
        this.contractList=contractList
        notifyDataSetChanged()
    }
    fun setOnClickListener(onClickListener: ContractAdapter.OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: ContractsCustomerName)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = contractList[position]
        //holder.customerName.text=currentItem.CustomerID.toString()
        holder.customerName.text=currentItem.CustomerName
        holder.title.text=currentItem.Title.toString()
        holder.endDate.text="End Date: "+currentItem.DateEnd
        holder.startDate.text="Start Date: "+currentItem.DateStart

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentItem )
            }
        }

    }
}