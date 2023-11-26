package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.gkprojects.cmmsandroidapp.Adapter.RvAlertAdapter

import com.gkprojects.cmmsandroidapp.DataClasses.Contracts
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect


import com.gkprojects.cmmsandroidapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class ContractInsertFragment : Fragment() {
    private lateinit var contractViewModel: ContractsVM
    var dialog: Dialog? = null
    private var rvAdapter: RvAlertAdapter? = null
    lateinit var filterText : SearchView
    var hospId : Int?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FragmentCheck", "Fragment view is being created.")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contract_insert, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val customerName=view.findViewById<TextView>(R.id.tv_customerName_contract)
        val startDate =view.findViewById<EditText>(R.id.et_date_contract_start)
        val endDate =view.findViewById<EditText>(R.id.et_date_contract_end)
        val contractCustomer =view.findViewById<TextView>(R.id.tv_customerName_contract)
        val title = view.findViewById<EditText>(R.id.etTitleContracts)

        var contractId :Int? =null

        val btnsave : Button = view.findViewById(R.id.btn_submit_contract_insert)
        val btnclear : Button =view.findViewById(R.id.btn_clear_contract_insert)
        val args =this.arguments
        val id= args?.getInt("id")
        title.setText(args?.getString("title"))
        startDate.setText(args?.getString("startDate"))
        endDate.setText(args?.getString("endDate"))
        val typeContract : String?=args?.getString("contractType")
        val contractStatus: String? =args?.getString("contractStatus")
        val customerId =args?.getString("hospitalId")
        contractCustomer.text = customerId


        var value :Double? =args?.getDouble("value")

        contractId= id
        Log.d("debugContracts",contractId.toString())



        contractViewModel= ViewModelProvider(this)[ContractsVM::class.java]
        var customerSearch =ArrayList<CustomerSelect>()
        Log.d("here","Here")

        lifecycleScope.launch {
            try {
                withContext(Dispatchers.Main) {
                    contractViewModel.getCustomerId(requireContext()).observe(viewLifecycleOwner, Observer {
                        customerSearch = it as ArrayList<CustomerSelect>
                        Log.d("customerContract", contractId.toString())
                        Log.d("customerCSearch", customerSearch.toString())
                        Log.d("customerCContract", contractCustomer.toString())
                        getValuesFromdb(customerSearch, customerId?.toInt(), contractCustomer)
                    })
                }
            } catch (e: Exception) {
                Log.d("contractInsertErrors", e.toString())
            }
        }

//        context?.let { contractViewModel.getCustomerId(it).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            customerSearch = it as ArrayList<CustomerSelect>
//            Log.d("customerContract",contractId.toString())
//            Log.d("customerCSearch",customerSearch.toString())
//            Log.d("customerCContract",contractCustomer.toString())
//            getValuesFromdb(customerSearch, customerId?.toInt(),contractCustomer)
// }) }





        contractCustomer.setOnClickListener {
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

            rvAdapter!!.setOnClickListener(object :RvAlertAdapter.OnClickListener{
                override fun onClick(position: Int, model: CustomerSelect) {
                    var strtemp: String = model.CustomerName
                    hospId = model.CustomerID

                    contractCustomer.text = strtemp
                    dialog!!.dismiss();

                }

            })
        }





        btnsave.setOnClickListener {
            if(hospId!=null){
                var contract= Contracts(contractId,hospId.toString(),title.text.toString(),startDate.text.toString(),endDate.text.toString(),null,"try",null,typeContract,contractStatus,null,null,null,null,hospId)

                Log.d("contract",contract.toString())
                if(contractId==null){
                    Log.d("contract123",contract.toString())
                    GlobalScope.launch(Dispatchers.IO) {
                        context?.let { it1 -> contract?.let { it2 ->
                            contractViewModel.insert(it1,it2 )  } }
                    }

                }else{
                    Log.d("contract345",contract.toString())
                        GlobalScope.launch(Dispatchers.IO) {
                            context?.let { it1 -> contract?.let { it2 ->
                                contractViewModel.updateContract(it1,
                                    it2
                                )
                            } }
                        }

                }


            }else{

                Toast.makeText(context,"Select Customer", Toast.LENGTH_SHORT).show()


            }

        }
    }
    private fun filterList(query: String,searchCustomer : ArrayList<CustomerSelect>) {
        val filteredList= java.util.ArrayList<CustomerSelect>()
        for (i in searchCustomer){
            if (i.CustomerName.lowercase(Locale.ROOT).contains(query))
                filteredList.add(i)
            Log.d("datafilterDialogContract", filteredList.toString())
        }
        if (filteredList.isEmpty() ){
            Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

        }else{

            rvAdapter?.filterList(filteredList)
        }

    }

    fun getValuesFromdb(data : ArrayList<CustomerSelect>, id :Int?,tv :TextView){
        var customerNameIndexed = mutableMapOf<Int, String>()

        if(isInt(id)) {
            for (i in data.indices) {
                customerNameIndexed[data[i].CustomerID] = data[i].CustomerName
            }
            tv.text = customerNameIndexed[id]
        }else
            tv.text="empty"

    }

    private fun isInt(id: Int?): Boolean {

        return id is Int
    }

}



