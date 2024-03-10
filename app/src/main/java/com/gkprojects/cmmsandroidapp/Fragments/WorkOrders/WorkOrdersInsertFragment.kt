package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gkprojects.cmmsandroidapp.Adapter.WorkOrdersViewPagerAdapter
import com.gkprojects.cmmsandroidapp.databinding.FragmentContractInsertBinding
import com.gkprojects.cmmsandroidapp.databinding.FragmentWorkOrdersInsertBinding
import com.google.android.material.tabs.TabLayoutMediator


class WorkOrdersInsertFragment : Fragment() {
    private lateinit var binding: FragmentWorkOrdersInsertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkOrdersInsertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = this.arguments
        val reportId :String ? = args?.getString("reportId")
        Log.d("repoId","$reportId")
        val sectionsPagerAdapter = WorkOrdersViewPagerAdapter(requireActivity())
        sectionsPagerAdapter.workOrderId=reportId
        sectionsPagerAdapter.notifyDataSetChanged()
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            // Configure tab titles
            tab.text = when (position) {
                0 -> "General Info"
                1 -> "Equipment List"
                2 -> "Tools"
                3 -> "Spare Parts"
                // add more cases as necessary
                else -> "Tab $position"
            }
           // ,tab.
        }.attach()


    }

    }



