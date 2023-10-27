package com.gkprojects.cmmsandroidapp.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.gkprojects.cmmsandroidapp.Adapter.RvAlertAdapter

import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect

import com.gkprojects.cmmsandroidapp.DataClasses.Tickets
import com.gkprojects.cmmsandroidapp.Models.CasesVM

import com.gkprojects.cmmsandroidapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class CaseInsertFragment : Fragment() {

    private lateinit var casesViewModel : CasesVM
    var dialog: Dialog? = null
    var check : String? = null
    private var rvAdapter: RvAlertAdapter? = null

    lateinit var filterText : SearchView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_case_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        casesViewModel= ViewModelProvider(this)[CasesVM::class.java]

        var casesID :Int? = null
        var customerId :Int? = null
        var rnds = (0..10).random()
        var userId :Int? = null

        val title=view.findViewById<EditText>(R.id.et_title_case)
        val startDate=view.findViewById<EditText>(R.id.et_case_startedDate)
        val user=view.findViewById<AutoCompleteTextView>(R.id.et_user_case)
        val customer=view.findViewById<TextView>(R.id.tv_customer_case)
        val endDate=view.findViewById<EditText>(R.id.et_case_endedDate)
        val statusCase=view.findViewById<Spinner>(R.id.sp_urgent_status)
        val activeCase=view.findViewById<CheckBox>(R.id.checkbox_active_case)
        val commentCase=view.findViewById<EditText>(R.id.et_cases_comments)
        val arrAdap = this.context?.let { ArrayAdapter.createFromResource(it,R.array.status_cases,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item).also{ adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            statusCase.adapter = adapter
        } }

        val btnsubmit : Button = view.findViewById(R.id.btn_submit_cases)
        val btnclear :Button =view.findViewById(R.id.btn_clear_cases)

        var customerSearch =ArrayList<CustomerSelect>()

        context?.let { casesViewModel.getCustomerId(it).observe(viewLifecycleOwner,
            androidx.lifecycle.Observer{
                customerSearch= it as ArrayList<CustomerSelect>

            })}


        val args =this.arguments
        var id= args?.getInt("id")

        var ticketById = ArrayList<Tickets>()

        try{
            lifecycleScope.launch {
                withContext(Dispatchers.Main){
                    if (id != null) {
                        casesViewModel.getTicketDataById(requireContext(),id).observe(viewLifecycleOwner,
                            androidx.lifecycle.Observer {
                                for (i in it.indices){
                                    ticketById.add(it[i])
                                }
                                setUpField(ticketById)
                                Log.d("ticket",ticketById.toString())
                            })
                    }else{
                        Log.d("ticket", "id is null")
                    }
                }
            }


        }catch (e:java.lang.Exception){
            Log.d("caseInsert",e.toString())
        }

        userId= args?.getInt("userId")
        customerId= args?.getInt("customerId")
        title.setText(args?.getString("title"))
        startDate.setText(args?.getString("startDate"))
        endDate.setText(args?.getString("endDate"))
        commentCase.setText(args?.getString("comments"))
        var active = args?.getString("active")
        var status=args?.getString("status")
        arrAdap?.getPosition(status)
        statusCase.setSelection(arrAdap?.getPosition(status)!!)
        Log.d("arrAdap",arrAdap?.getPosition(status).toString())
        Log.d("CaseID",active.toString()+"+"+status.toString())

        if(active=="Checked") {
            activeCase.isChecked
        }






        customer.setOnClickListener {

            val builder = AlertDialog.Builder(context)

            builder.setView(R.layout.dialog_searchable_spinner)

            dialog?.getWindow()?.setLayout(650,800);

            // set transparent background
            dialog?.getWindow()?.setBackgroundDrawableResource(com.google.android.material.R.drawable.m3_tabs_transparent_background)


            dialog=builder.create()
            // show dialog
            dialog?.show()


            val recycleView: RecyclerView = dialog!!.findViewById(R.id.rv_searchable_TextView)
            filterText= dialog!!.findViewById(R.id.searchView_rv_customers)

            rvAdapter = context?.let { it1 -> RvAlertAdapter(it1, customerSearch) }
            recycleView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this.context)
                adapter = rvAdapter
            }
            filterText?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0 != null) {
                        filterList(p0.lowercase(Locale.ROOT),customerSearch)
                    }
                    return true
                }

            })

            rvAdapter!!.setOnClickListener(object : RvAlertAdapter.OnClickListener{
                override fun onClick(position: Int, model: CustomerSelect) {
                    var strtemp: String = model.CustomerName
                    customerId = model.CustomerID

                    customer.text = strtemp
                    dialog!!.dismiss();

                }

            })


        }
        casesID=id


        btnsubmit.setOnClickListener {
            //customerId=hospId
            if(userId ==null){
                userId=rnds
            }

            check = if (activeCase.isChecked){
                " Checked"
            }else
                " Not Checked"

            if(customerId!=null) {
                val case = Tickets(
                    null,
                    null,
                    title.text.toString(),
                    null,
                    commentCase.text.toString(),
                    null,
                    check!!,
                    startDate.text.toString(),
                    endDate.text.toString(),
                    null,
                    null,
                    null,
                    null,
                    customerId.toString(),
                    null
                )
                Log.d("debugCasesInsert", case.toString())


                if (casesID == null) {


                    GlobalScope.launch(Dispatchers.IO) {
                        context?.let { it1 -> casesViewModel.insert(it1, case) }

                    }
                    title.text.clear()
                    startDate.text.clear()
                    endDate.text.clear()
                    commentCase.text.clear()
                    statusCase.setSelection(0)
                    activeCase.isChecked = false
                    user.text.clear()
                    customer.text = "Customer Select"
                } else {
                    Log.d("debugUpdateCase",case.toString())
                    GlobalScope.launch(Dispatchers.IO) {
                        context?.let { it1 -> casesViewModel.updateCustomer(it1, case) }

                    }
                }

            }
            else{
                Toast.makeText(context,"Select Customer",Toast.LENGTH_SHORT).show()
            }



        }

        btnclear.setOnClickListener {  title.text.clear()
            startDate.text.clear()
            endDate.text.clear()
            commentCase.text.clear()
            statusCase.setSelection(0)
            activeCase.isChecked = false
            user.text.clear()
            customer.text="Customer Select" }


    }
    fun setUpField(tickets: ArrayList<Tickets>){
        val title=requireView().findViewById<EditText>(R.id.et_title_case)
        val startDate=requireView().findViewById<EditText>(R.id.et_case_startedDate)
        val user=requireView().findViewById<AutoCompleteTextView>(R.id.et_user_case)
        val customer=requireView().findViewById<TextView>(R.id.tv_customer_case)
        val endDate=requireView().findViewById<EditText>(R.id.et_case_endedDate)
        val statusCase=requireView().findViewById<Spinner>(R.id.sp_urgent_status)
        val activeCase=requireView().findViewById<CheckBox>(R.id.checkbox_active_case)
        val commentCase=requireView().findViewById<EditText>(R.id.et_cases_comments)
        val arrAdap = this.context?.let { ArrayAdapter.createFromResource(it,R.array.status_cases,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item).also{ adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            statusCase.adapter = adapter
        } }
//        title.setText(tickets)
//        startDate.setText(tickets.DateStart)
//        endDate.setText(tickets.DateEnd)
//        customer.text = tickets.CustomerID.toString()




    }

    private fun filterList(query: String,searchCustomer : ArrayList<CustomerSelect>) {
        val filteredList= ArrayList<CustomerSelect>()
        for (i in searchCustomer){
            if (i.CustomerName.lowercase(Locale.ROOT).contains(query))
                filteredList.add(i)

        }
        if (filteredList.isEmpty() ){
            Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

        }else{

            rvAdapter?.filterList(filteredList)
        }

    }





}