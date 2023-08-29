package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.gkprojects.cmmsandroidapp.DataClasses.Contract
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class ContractFragment : Fragment() {
    private lateinit var contractRecyclerView: RecyclerView

    private var templist = ArrayList<Contract>()
    private lateinit var contractAdapter: ContractAdapter
    private lateinit var contractViewModel: ContractsVM



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment_contract, container, false)
        // Inflate the layout for this fragment

        return v
    }

    @SuppressLint("UseRequireInsteadOfGet", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contractRecyclerView=view.findViewById(R.id.contract_recyclerview)
        contractAdapter = this.context?.let { ContractAdapter( ArrayList<Contract>()) }!!
        contractRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = contractAdapter
        }
        contractViewModel = ViewModelProvider(this).get(ContractsVM::class.java)

        context?.let {
            contractViewModel.getAllContractData(it).observe(viewLifecycleOwner, Observer {
                contractAdapter.setData(it as ArrayList<Contract>)
                Log.d("debug123",it.toString())
                templist.clear() // clear the templist,because it keeps populate everytime we open and close Customer Drawer
                for(i in it.indices)(
                        templist.add(it[i])
                        )
                Log.d("templist", templist.size.toString())
            })
        }
        val searchView = view.findViewById<SearchView>(R.id.searchView_contract)
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
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
        contractAdapter.setOnClickListener(object : ContractAdapter.OnClickListener{
            override fun onClick(position: Int, model: Contract) {
//                var temp: java.io.Serializable = model as java.io.Serializable
                Toast.makeText(context,model.toString(),Toast.LENGTH_LONG).show()
                passDataCustomer(model)

                //passDataCustomer()
            }
        })

        val myCallback = object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT) {

            // More code here
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {



                try {
                    GlobalScope.launch(Dispatchers.IO) {

                        context?.let {
                            contractViewModel.deleteContract(
                                it, templist[viewHolder.absoluteAdapterPosition]
                            )
                        }

                    }
                }catch (e:java.lang.Exception){
                    Log.d("deleteEquipment",e.toString())
                }

                context?.let {

                    contractViewModel.getAllContractData(it).observe(viewLifecycleOwner, Observer {
                        contractAdapter.setData(it as ArrayList<Contract>)

                    })
                }
            }


        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(contractRecyclerView)


        val btnFloat=view.findViewById<FloatingActionButton>(R.id.openContractFragment)
        btnFloat.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, ContractInsertFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }
    private fun filterList(query:String){
        if (query!=null){
            val filteredList= ArrayList<Contract>()
            for (i in templist){
                if (i.contractType.lowercase(Locale.ROOT).contains(query))
                    filteredList.add(i)
                Log.d("datacustomer", filteredList.toString())
            }
            if (filteredList.isEmpty() ){
                Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

            }else{
                contractAdapter.setData(filteredList)
            }
        }


    }
    private fun passDataCustomer(data : Contract){

        val bundle = Bundle()
        data.contractID?.let { bundle.putInt("id", it.toInt()) }

        bundle.putString("hospitalId", data.hospitalID)
        bundle.putString("contractType", data.contractType)
        bundle.putString("contractStatus", data.contractStatus)
        bundle.putString("endDate", data.endDate)
        bundle.putString("startDate", data.startDate)
        bundle.putDouble("contractValue", data.contractValue)
        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment = ContractInsertFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()

    }


}