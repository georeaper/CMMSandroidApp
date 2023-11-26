package com.gkprojects.cmmsandroidapp.Fragments

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.lang.Exception


class HomeFragment : Fragment() {

    private lateinit var contractsViewModel: ContractsVM
    private lateinit var ticketsViewModel :CasesVM
    private lateinit var workOrdersRecyclerView: RecyclerView
    private var templist = ArrayList<OverviewMainData>()
    private lateinit var adapterHomeWorkOrder: MainOverviewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId=R.id.action_home

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        super.onResume()
        var activity =requireActivity()

        var drawerLayout = activity.findViewById<DrawerLayout>(R.id.DrawLayout)
        val navView: NavigationView = activity.findViewById(R.id.navView)
        val toolbar: MaterialToolbar = activity.findViewById(R.id.topAppBar)
        toolbar.title="Home"


        var toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        templist.clear()
        super.onViewCreated(view, savedInstanceState)
        var inputs = ArrayList<HomeRecyclerViewData>()
        var overview =ArrayList<OverviewMainData>()

        workOrdersRecyclerView =view.findViewById(R.id.recyclerViewHomeWorkOrder)
        val btnCalendar =view.findViewById<ImageButton>(R.id.img_calendar_view)
        val btnContent =view.findViewById<ImageButton>(R.id.img_content_item_view)
        val calendar :CalendarView=view.findViewById(R.id.calendarView_calendar_view)
        btnCalendar.setOnClickListener { if(calendar.visibility==View.VISIBLE){
            calendar.visibility=View.GONE
            btnCalendar.setImageResource(R.drawable.add_expandable_icon)
        }else{
            calendar.visibility=View.VISIBLE
            btnCalendar.setImageResource(R.drawable.remove_expandable_icon)
        }

        }
        btnContent.setOnClickListener {
            when(workOrdersRecyclerView.visibility){
                View.GONE->{
                    workOrdersRecyclerView.visibility=View.VISIBLE
                    btnContent.setImageResource(R.drawable.remove_expandable_icon)
                }
                View.VISIBLE->{
                    workOrdersRecyclerView.visibility=View.GONE
                    btnContent.setImageResource(R.drawable.add_expandable_icon)
                }
            }
        }
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








    }
    private fun setdatatoRv( recyclerview : RecyclerView , adapterRv : MainOverviewAdapter,  input :ArrayList<OverviewMainData>){
        Log.d("debugInput",input.toString())
        recyclerview.apply { setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = adapterRv }
        adapterRv.setData(input)

    }


}