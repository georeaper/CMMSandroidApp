package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.WorkOrdersAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.WorkOrdersList
import com.gkprojects.cmmsandroidapp.Models.SharedViewModel

import com.gkprojects.cmmsandroidapp.Models.WorkOrdersVM
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.FragmentWorkOrdersBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import java.util.Locale


class Work_Orders : Fragment() {
    private lateinit var binding: FragmentWorkOrdersBinding
    private lateinit var recyclerViewWorkOrder: RecyclerView
    private lateinit var adapterWorkOrder: WorkOrdersAdapter
    private lateinit var searchViewWorkOrder: SearchView
    private lateinit var workOrderViewModel: WorkOrdersVM
    private var tempWorkOrdersList = ArrayList<WorkOrdersList>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onResume() {
        super.onResume()
        var activity =requireActivity()

        var drawerLayout = activity.findViewById<DrawerLayout>(R.id.DrawLayout)
        val navView: NavigationView = activity.findViewById(R.id.navView)
        val toolbar: MaterialToolbar = activity.findViewById(R.id.topAppBar)
        toolbar.title="Work Orders"

        var toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkOrdersBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedViewModel: SharedViewModel by activityViewModels()
        super.onViewCreated(view, savedInstanceState)
        recyclerViewWorkOrder=binding.workOrdersRecyclerView
        adapterWorkOrder= WorkOrdersAdapter(tempWorkOrdersList)
        recyclerViewWorkOrder.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this.context)
            adapter= adapterWorkOrder
        }

        workOrderViewModel= ViewModelProvider(this)[WorkOrdersVM::class.java]
        workOrderViewModel.getWorkOrdersCustomerName(requireContext()).observe(viewLifecycleOwner,
            Observer {
                tempWorkOrdersList=it as ArrayList<WorkOrdersList>
                adapterWorkOrder.setData(tempWorkOrdersList)
            })
        searchViewWorkOrder = binding.workOrdersSearchView
        searchViewWorkOrder.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    filterList(p0.lowercase(Locale.ROOT))
                }
                return true
            }
        })
        adapterWorkOrder.setOnClickListener(object : WorkOrdersAdapter.OnClickListener{
            override fun onClick(position: Int, model: WorkOrdersList) {

                val bundle = Bundle()
                bundle.putString("reportId", model.workOrderID!!)
                val fragmentManager =parentFragmentManager
                val fragmentTransaction=fragmentManager.beginTransaction()
                val fragment = WorkOrdersInsertFragment()
                fragment.arguments = bundle
                fragmentTransaction.replace(R.id.frameLayout1,fragment)
                fragmentTransaction.commit()
                //passData(model.workOrderID)
            }

        })

        val btnFloat= binding.workOrdersFloatButton

        btnFloat.setOnClickListener {
            val fragmentManager =parentFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            val fragment = WorkOrdersInsertFragment()
            fragmentTransaction.replace(R.id.frameLayout1,fragment)
            fragmentTransaction.commit()
        }
    }



    private fun filterList(query:String){
        val filteredList= java.util.ArrayList<WorkOrdersList>()
        for (i in tempWorkOrdersList){
            if((i.customerName?.lowercase(Locale.ROOT)?.contains(query)==true) or (i.reportNumber?.lowercase(Locale.ROOT)?.contains(query) == true)or(i.dateOpened?.lowercase(Locale.ROOT)?.contains(query) == true))

                filteredList.add(i)
            Log.d("dataEquipment", filteredList.toString())
        }
        if (filteredList.isEmpty() ){
            Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

        }else{
            adapterWorkOrder.setData(filteredList)
        }


    }


}