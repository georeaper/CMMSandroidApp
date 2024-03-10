package com.gkprojects.cmmsandroidapp.Adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.CustomerInfoFragment
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.EquipmentListFragment
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.SparePartListFragment
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.ToolsListFragment

class WorkOrdersViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var workOrderId: String? = null

        override fun getItemCount(): Int = 4 // Number of tabs

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    val fragment = CustomerInfoFragment()
                    workOrderId?.let {
                        val bundle = Bundle()
                        bundle.putString("reportId", it)
                        fragment.arguments = bundle
                    }
                    fragment
                }
                1 -> EquipmentListFragment()

                2 -> ToolsListFragment()

                3 -> SparePartListFragment()

                // Add more fragments for other tabs
                else -> Fragment()
            }
        }
    }
