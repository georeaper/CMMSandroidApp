package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.gkprojects.cmmsandroidapp.DataClasses.Contracts
import com.gkprojects.cmmsandroidapp.DataClasses.ContractsCustomerName
import com.gkprojects.cmmsandroidapp.DataClasses.DetailedContract
import com.gkprojects.cmmsandroidapp.MainActivity.Companion.TAG_CONTRACTS
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class ContractFragment : Fragment() {
    private lateinit var contractRecyclerView: RecyclerView

    private var templist = ArrayList<ContractsCustomerName>()
    private lateinit var contractAdapter: ContractAdapter
    private lateinit var contractViewModel: ContractsVM
    private lateinit var toolbar: MaterialToolbar




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment_contract, container, false)
        // Inflate the layout for this fragment

        return v
    }

    override fun onResume() {
        super.onResume()
        var activity =requireActivity()

        var drawerLayout = activity.findViewById<DrawerLayout>(R.id.DrawLayout)
        val navView: NavigationView = activity.findViewById(R.id.navView)
        val toolbar: MaterialToolbar = activity.findViewById(R.id.topAppBar)
        toolbar.title="Contract"


        var toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    @SuppressLint("UseRequireInsteadOfGet", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = requireActivity().findViewById(R.id.topAppBar)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        //bottomNavigationView.selectedItemId=R.id.action_home
        toolbar.title =TAG_CONTRACTS

        contractRecyclerView=view.findViewById(R.id.contract_recyclerview)
        contractAdapter = this.context?.let { ContractAdapter( ArrayList<ContractsCustomerName>()) }!!
        contractRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = contractAdapter
        }
        contractViewModel = ViewModelProvider(this).get(ContractsVM::class.java)

        try {
            lifecycleScope.launch {
                withContext(Dispatchers.Main){
                    context?.let {
                        contractViewModel.getCustomerName(it).observe(viewLifecycleOwner, Observer {
                            contractAdapter.setData(it as ArrayList<ContractsCustomerName>)

                            templist.clear() // clear the templist,because it keeps populate everytime we open and close Customer Drawer
                            for(i in it.indices) {
                                templist.add(it[i])
                            }
                        })
                    }

                }
            }

        }catch (e: java.lang.Exception){
            Log.d("Contracts_e",e.toString())
        }


        val searchView = view.findViewById<TextInputEditText>(R.id.searchEditTextContract)
        searchView.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s != null) {
                    filterList(s.toString().lowercase(Locale.ROOT))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        contractAdapter.setOnClickListener(object : ContractAdapter.OnClickListener{
            override fun onClick(position: Int, model: ContractsCustomerName) {

                passDataCustomer(model)


            }
        })


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
            val filteredList= ArrayList<ContractsCustomerName>()
            for (i in templist){
                if (i.ContractType?.lowercase(Locale.ROOT)?.contains(query) == true)
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
    private fun passDataCustomer(data : ContractsCustomerName){

        val bundle = Bundle()
        data.ContractID?.let { bundle.putInt("id", it.toInt()) }


        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment = ContractInsertFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()

    }


}