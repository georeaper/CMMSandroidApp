package com.gkprojects.cmmsandroidapp.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.MainOverviewAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.HomeRecyclerViewData
import com.gkprojects.cmmsandroidapp.DataClasses.OverviewMainData
import com.gkprojects.cmmsandroidapp.DataClasses.TicketCustomerName
import com.gkprojects.cmmsandroidapp.Fragments.Contracts.ContractsVM
import com.gkprojects.cmmsandroidapp.Models.CasesVM
import com.gkprojects.cmmsandroidapp.R
import java.lang.Exception


class HomeFragment : Fragment() {

    private lateinit var contractsViewModel: ContractsVM
    private lateinit var ticketsViewModel :CasesVM
    private lateinit var workOrdersRecyclerView: RecyclerView
    private var templist = ArrayList<OverviewMainData>()
    private lateinit var adapterHomeWorkOrder: MainOverviewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        templist.clear()
        super.onViewCreated(view, savedInstanceState)
        var inputs = ArrayList<HomeRecyclerViewData>()
        var overview =ArrayList<OverviewMainData>()
        workOrdersRecyclerView =view.findViewById(R.id.recyclerViewHomeWorkOrder)
        adapterHomeWorkOrder= context?.let { MainOverviewAdapter(it,ArrayList<OverviewMainData>()) }!!
        contractsViewModel = ViewModelProvider(this)[ContractsVM::class.java]
        ticketsViewModel = ViewModelProvider(this)[CasesVM::class.java]

        try {
            context?.let {
                ticketsViewModel.getOverviewData(it).observe(viewLifecycleOwner,Observer{
                    for (i in it.indices){
                        templist.add(it[i])

                    }
                    setdatatoRv(workOrdersRecyclerView,adapterHomeWorkOrder,templist)


                })
            }

        }catch (e:Exception){
            Log.d("ticketOverview",e.toString())
        }


        try {
            context?.let {
                contractsViewModel.getContractsOverview(it).observe(viewLifecycleOwner, Observer {

//                    adapterHomeWorkOrder.setData(it as ArrayList<OverviewMainData>)
                    for (i in it.indices){
                        templist.add(it[i])
                    }
                    setdatatoRv(workOrdersRecyclerView,adapterHomeWorkOrder,templist)

                })
            }


        }catch (e:Exception){
            Log.d("testingHome",e.toString())
        }


    }
    private fun setdatatoRv( recyclerview : RecyclerView , adapterRv : MainOverviewAdapter,  input :ArrayList<OverviewMainData>){
        Log.d("debugInput",input.toString())
        recyclerview.apply { setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = adapterRv }
        adapterRv.setData(input)

    }


}