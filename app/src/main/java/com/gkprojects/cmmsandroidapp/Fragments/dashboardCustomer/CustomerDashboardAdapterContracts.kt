package com.gkprojects.cmmsandroidapp.Fragments.dashboardCustomer

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerContractsDataClass

class CustomerDashboardAdapterContracts(contactList :ArrayList<DashboardCustomerContractsDataClass>): RecyclerView.Adapter<CustomerDashboardAdapterContracts.DashboardContractsVH>() {
    class DashboardContractsVH(itemView :View ):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardContractsVH {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DashboardContractsVH, position: Int) {
        TODO("Not yet implemented")
    }
}