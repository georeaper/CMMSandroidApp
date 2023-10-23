package com.gkprojects.cmmsandroidapp.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.CasesAdapter
import com.gkprojects.cmmsandroidapp.Adapter.CustomerAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.Cases
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.DataClasses.TicketCustomerName
import com.gkprojects.cmmsandroidapp.DataClasses.Tickets
import com.gkprojects.cmmsandroidapp.Models.CasesVM
import com.gkprojects.cmmsandroidapp.Models.CustomerVM
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class CasesFragment : Fragment() {
    private lateinit var casesRecyclerView: RecyclerView

    private var templist = ArrayList<TicketCustomerName>()
    private lateinit var casesAdapter: CasesAdapter
    private lateinit var casesViewModel: CasesVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cases, container, false)
    }

    @SuppressLint("SuspiciousIndentation", "UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        casesRecyclerView = view.findViewById(R.id.cases_recyclerview)
        casesAdapter = this.context?.let { CasesAdapter(it, ArrayList<TicketCustomerName>()) }!!

        casesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = casesAdapter
        }
        casesViewModel = ViewModelProvider(this)[CasesVM::class.java]

//        context?.let { it ->
//            casesViewModel.getAllCasesData(it).observe(viewLifecycleOwner, Observer {
//
//                casesAdapter.setData(it as ArrayList<TicketCustomerName>)
//
//
//
//                Log.d("debug12367", it.toString())
//                templist.clear() // clear the templist,because it keeps populate everytime we open and close Customer Drawer
//                for (i in it.indices) {
//                    templist.add(it[i])
//
//                       // casesViewModel.getCustomerNameWhereId(it[i].customerID)
//
//
//                }
//                Log.d("templistCases", templist.size.toString())
//            })
//        }
        try{
            lifecycleScope.launch { withContext(Dispatchers.Main){
                casesViewModel.getCustomerName(context!!).observe(viewLifecycleOwner, Observer {

                    casesAdapter.setData(it as ArrayList<TicketCustomerName>)
                    templist.clear()
                    for(i in it.indices){
                        templist.add(it[i])


                    }
                    Log.d("casesItems", templist.toString())
                })

            } }

        }catch (e:Exception){
            Log.d("casesDebug",e.toString())
        }


        val searchView = view.findViewById<SearchView>(R.id.searchView_cases)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        casesAdapter.setOnClickListener(object : CasesAdapter.OnClickListener {
            override fun onClick(position: Int, model: TicketCustomerName) {
//                var temp: java.io.Serializable = model as java.io.Serializable
                Toast.makeText(context, model.toString(), Toast.LENGTH_LONG).show()
                passDataCustomer(model)

                //passDataCustomer()
            }
        })


        val btnFloat = view.findViewById<FloatingActionButton>(R.id.openCasesFragment)
        btnFloat.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, CaseInsertFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        val myCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {

            // More code here
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {


//DELETE METHOD needed
            }


        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(casesRecyclerView)
    }



    private fun filterList(query:String){
        Log.d("query",query)
        if (query!=null){
            val filteredList= ArrayList<TicketCustomerName>()
            for (i in templist){
                if (i.Title?.lowercase(Locale.ROOT)?.contains(query) == true)
                    filteredList.add(i)
                Log.d("filteredCases", filteredList.toString())
            }
            if (filteredList.isEmpty() ){
                Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

            }else{
                casesAdapter.setData(filteredList)
            }
        }


    }

    private fun passDataCustomer(data : TicketCustomerName){

        val bundle = Bundle()
        data.TicketID?.let { bundle.putInt("id", it.toInt()) }
        bundle.putString("title", data.Title)
        bundle.putString("startDate", data.DateStart)
        bundle.putString("endDate", data.CustomerID)
        bundle.putString("comments", data.Active)
        bundle.putString("active", data.EquipmentID)
        bundle.putString("userId",data.UserID)
        bundle.putString("customerId",data.CustomerID)



        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment =CaseInsertFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()

    }


}