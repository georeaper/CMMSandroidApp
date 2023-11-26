package com.gkprojects.cmmsandroidapp.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.gkprojects.cmmsandroidapp.DataClasses.TicketCustomerName
import com.gkprojects.cmmsandroidapp.DataClasses.Tickets
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.textfield.TextInputEditText

class CasesAdapter(private var context :Context , private var casesList : ArrayList<TicketCustomerName>): RecyclerView.Adapter<CasesAdapter.MyViewholder>() {
    private var onClickListener: CasesAdapter.OnClickListener? = null

    class MyViewholder(itemView : View):RecyclerView.ViewHolder(itemView) {
        val title: TextView=itemView.findViewById(R.id.titleCasesList)
        val serialNumber :TextView=itemView.findViewById(R.id.serialnumberCasesList)
        val startDate :TextView=itemView.findViewById(R.id.dateStartedCases)
        val customer : TextView=itemView.findViewById(R.id.customerNameCasesList)
        val status :View=itemView.findViewById(R.id.viewCasesList)
        val caseNumber :TextView=itemView.findViewById(R.id.caseNumberCases)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.caseslist_rv,parent,false)
        return CasesAdapter.MyViewholder(itemView)
    }

    override fun getItemCount(): Int {
        return casesList.size
    }
    fun setData(casesList:ArrayList<TicketCustomerName>)
    {
        this.casesList=casesList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val currentitem = casesList[position]
        holder.caseNumber.text = "ID: "+ currentitem.TicketID.toString()
        holder.customer.text = currentitem.CustomerName.toString()
        holder.serialNumber.text = currentitem.SerialNumber.toString()
        holder.startDate.text = "Start Date: "+ currentitem.DateStart.toString()
        holder.title.text=currentitem.Title.toString()
        Log.d("urgencyAdapter",currentitem.Urgency.toString())

        if (currentitem.Urgency.toString()=="RED"){
            var backgroundColor = ContextCompat.getColor(context, R.color.red)
            Log.d("BackgroundColor", backgroundColor.toString())
            holder.status.setBackgroundColor(backgroundColor)


         }
         else if(currentitem.Urgency.toString()=="YELLOW"){
            var backgroundColor = ContextCompat.getColor(context, R.color.orange)
            Log.d("BackgroundColor", backgroundColor.toString())
            holder.status.setBackgroundColor(backgroundColor)
        }
        else if(currentitem.Urgency.toString()=="BLUE"){
            var backgroundColor = ContextCompat.getColor(context, R.color.AppColor)
            Log.d("BackgroundColor", backgroundColor.toString())
            holder.status.setBackgroundColor(backgroundColor)
        }

//        val backgroundColor = ContextCompat.getColor(context, R.color.AppColor)
//        holder.status.setBackgroundColor(backgroundColor)



        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentitem )
            }
        }
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: TicketCustomerName)
    }

}