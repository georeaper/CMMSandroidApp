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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.RvAdapterFindCustomers
import com.gkprojects.cmmsandroidapp.Adapter.RvAlertAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.Contract
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentCustomerSelect
import com.gkprojects.cmmsandroidapp.Models.CustomerVM
import com.gkprojects.cmmsandroidapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contract_insert, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contractViewModel= ViewModelProvider(this)[ContractsVM::class.java]
        var customerSearch =ArrayList<CustomerSelect>()

        context?.let { contractViewModel.getCustomerId(it).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            customerSearch = it as ArrayList<CustomerSelect>
        }) }

        var startDate =view.findViewById<EditText>(R.id.et_date_contract_start)
        var endDate =view.findViewById<EditText>(R.id.et_date_contract_end)
        //var typeContract =view.findViewById<Spinner>(R.id.contractTypeSpinner)
        //var contractStatus =view.findViewById<Spinner>(R.id.contractStatusSpinner)
        var contractCustomer =view.findViewById<TextView>(R.id.tv_customerName_contract)
        var title = view.findViewById<EditText>(R.id.etTitleContracts)

        var contractId :Int? =null

        val btnsave : Button = view.findViewById(R.id.btn_submit_contract_insert)
        val btnclear : Button =view.findViewById(R.id.btn_clear_contract_insert)
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
                    var strtemp: String = model.name
                    hospId = model.hospitalID

                    contractCustomer.text = strtemp
                    dialog!!.dismiss();

                }

            })
        }

        val args =this.arguments
        var id= args?.getInt("id")
        title.setText(args?.getString("title"))
        startDate.setText(args?.getString("startDate"))
        endDate.setText(args?.getString("endDate"))
        var typeContract : String?=args?.getString("contractType")
        var contractStatus: String? =args?.getString("contractStatus")
        contractCustomer.text = args?.getString("hospitalId")

        Log.d("contractFragment",id.toString())
        var value :Double? =args?.getDouble("value")

        contractId= id
        Log.d("contractFragment3",contractId.toString())

        btnsave.setOnClickListener {
            if(hospId!=null){
                var contract= Contract(contractId,hospId.toString(),startDate.text.toString(),endDate.text.toString(),"GO",2.00,"try")

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
                println("I am here Contracts")
                Toast.makeText(context,"Select Customer", Toast.LENGTH_SHORT).show()
                Log.d("contractElse","hospID is NULL")

            }

        }
    }
    private fun filterList(query: String,searchCustomer : ArrayList<CustomerSelect>) {
        val filteredList= java.util.ArrayList<CustomerSelect>()
        for (i in searchCustomer){
            if (i.name.lowercase(Locale.ROOT).contains(query))
                filteredList.add(i)
            Log.d("datafilterDialogContract", filteredList.toString())
        }
        if (filteredList.isEmpty() ){
            Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

        }else{

            rvAdapter?.filterList(filteredList)
        }

    }

}


