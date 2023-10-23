package com.gkprojects.cmmsandroidapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.Cases
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.DataClasses.TicketCustomerName
import com.gkprojects.cmmsandroidapp.DataClasses.Tickets
import com.gkprojects.cmmsandroidapp.R

class CasesAdapter(private var context :Context , private var casesList : ArrayList<TicketCustomerName>): RecyclerView.Adapter<CasesAdapter.MyViewholder>() {
    private var onClickListener: CasesAdapter.OnClickListener? = null

    class MyViewholder(itemView : View):RecyclerView.ViewHolder(itemView) {
        val name= itemView.findViewById<TextView>(R.id.tv_list_customerName)
        val title= itemView.findViewById<TextView>(R.id.tv_list_title)
        val startDate= itemView.findViewById<TextView>(R.id.tv_list_startDate)
        val status= itemView.findViewById<TextView>(R.id.tv_list_status)

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
        holder.name.text=currentitem.CustomerName
        holder.title.text=currentitem.Title
        holder.startDate.text=currentitem.DateStart
        holder.status.text=currentitem.Active

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