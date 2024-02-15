package com.gkprojects.cmmsandroidapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentSelectCustomerName
import com.gkprojects.cmmsandroidapp.DataClasses.WorkOrdersList
import com.gkprojects.cmmsandroidapp.databinding.WorkOrdersListRowsBinding

class WorkOrdersAdapter(private var workOrderList : List<WorkOrdersList>): RecyclerView.Adapter<WorkOrdersAdapter.MyViewHolder>() {
    private var onClickListener: WorkOrdersAdapter.OnClickListener? = null

    fun setOnClickListener(onClickListener: WorkOrdersAdapter.OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: WorkOrdersList)
    }
    class MyViewHolder(private val binding: WorkOrdersListRowsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(workOrder: WorkOrdersList) {

            binding.workOrderListCustomerName.text=workOrder.customerName.toString()
            binding.workOrderListDateStarted.text=workOrder.dateOpened.toString()
            binding.workOrderListReportNumber.text=workOrder.reportNumber.toString()
            binding.workOrderListTitle.text=workOrder.title.toString()
        }
    }
    fun setData(templist:ArrayList<WorkOrdersList>)
    {
        this.workOrderList=templist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = WorkOrdersListRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return workOrderList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(workOrderList[position])

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, workOrderList[position])
        }
    }
}