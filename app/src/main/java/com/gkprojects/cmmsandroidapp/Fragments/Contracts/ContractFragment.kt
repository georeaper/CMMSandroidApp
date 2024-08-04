package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.SearchView
import android.widget.TextView
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
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.DetailedContract
import com.gkprojects.cmmsandroidapp.MainActivity.Companion.TAG_CONTRACTS
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.FragmentContractBinding
import com.gkprojects.cmmsandroidapp.filterPopWindow
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

    private var contractList = ArrayList<ContractsCustomerName>()
    private var filteredContractsList = ArrayList<ContractsCustomerName>()
    private lateinit var contractAdapter: ContractAdapter
    private lateinit var contractViewModel: ContractsVM
    private lateinit var toolbar: MaterialToolbar
    private lateinit var filterWindow : filterPopWindow
    private lateinit var binding : FragmentContractBinding
    private var uniqueContractType : List<String> = emptyList()
    private var uniqueCustomerName : List<String> = emptyList()
    private lateinit var startDateTextView: TextView
    private lateinit var endDateTextView: TextView
    private var filteredContractType : ArrayList<String> = ArrayList()
    private var filteredCustomerName : ArrayList<String> = ArrayList()
    private var selectedRadioButtonId: Int = R.id.statusContractAll
    private var contractStatusRadio : String = ""
    private var endDateRangeValue :String =""
    private var startDateRangeValue :String =""





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding=FragmentContractBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val activity =requireActivity()

        val drawerLayout = activity.findViewById<DrawerLayout>(R.id.DrawLayout)

        val toolbar: MaterialToolbar = activity.findViewById(R.id.topAppBar)
        toolbar.title="Contract"


        val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    @SuppressLint("UseRequireInsteadOfGet", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = requireActivity().findViewById(R.id.topAppBar)
        //val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        //bottomNavigationView.selectedItemId=R.id.action_home
        toolbar.title =TAG_CONTRACTS

        contractRecyclerView=view.findViewById(R.id.contract_recyclerview)
        contractAdapter = this.context?.let { ContractAdapter( ArrayList<ContractsCustomerName>()) }!!
        contractRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = contractAdapter
        }
        contractViewModel = ViewModelProvider(this)[ContractsVM::class.java]

        try {
            lifecycleScope.launch { withContext(Dispatchers.Main){
                contractViewModel.getCustomerName(requireContext()).observe(viewLifecycleOwner,
                    Observer{list ->
                    contractList.clear()
                    contractList= list as ArrayList<ContractsCustomerName>
                    contractAdapter.setData(contractList)
                    uniqueContractType= contractList.map { it.ContractType.toString() }.distinct()
                    uniqueCustomerName=contractList.map{it.CustomerName.toString()}.distinct()
                    Log.d("uniqueType","$uniqueContractType")
                    Log.d("uniqueCustomer","$uniqueCustomerName")
                })
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

        val filterButton=binding.imageButtonFilterContract
        filterButton.setOnClickListener {

            filterWindow  = filterPopWindow.newInstance(
                R.layout.filter_pop_contracts
            ){filterView ->
                startDateTextView = filterView.findViewById(R.id.startDateTextView)
                endDateTextView = filterView.findViewById(R.id.endDateTextView)
                startDateTextView.text=startDateRangeValue
                endDateTextView.text=endDateRangeValue

                startDateTextView.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(selectedYear, selectedMonth, selectedDay)
                        val dateFormat = SimpleDateFormat(getString(R.string.app_date_format), Locale.getDefault())
                        startDateRangeValue=dateFormat.format(selectedDate.time)
                        startDateTextView.text = startDateRangeValue
                    }, year, month, day)

                    datePickerDialog.show()

                }

                endDateTextView.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(selectedYear, selectedMonth, selectedDay)
                        val dateFormat = SimpleDateFormat(getString(R.string.app_date_format), Locale.getDefault())
                        endDateRangeValue=dateFormat.format(selectedDate.time)
                        endDateTextView.text = endDateRangeValue
                    }, year, month, day)

                    datePickerDialog.show()
                }
                val contractTypeListView: ListView = filterView.findViewById(R.id.contractTypeListView)
                val customerListView: ListView = filterView.findViewById(R.id.customerListView)

                val contractTypeAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_multiple_choice,
                    uniqueContractType
                )
                contractTypeListView.adapter = contractTypeAdapter

                val customerAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_multiple_choice,
                    uniqueCustomerName
                )
                customerListView.adapter = customerAdapter

                contractTypeListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
                customerListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

// Initialize selection state
                for (i in uniqueCustomerName.indices) {
                    val customer = uniqueCustomerName[i]
                    if (filteredCustomerName.contains(customer)) {
                        customerListView.setItemChecked(i, true)
                        customerListView.getChildAt(i)?.setBackgroundColor(0x9934B5E4.toInt()) // Highlight color
                    }
                }
                // Handle customer selection
                customerListView.setOnItemClickListener {
                                                        _, view, position, _ ->
                    val selectedCustomer = uniqueCustomerName[position]
                    if (filteredCustomerName.contains(selectedCustomer)) {
                        filteredCustomerName.remove(selectedCustomer)

                        view.setBackgroundColor(0x00000000) // Transparent background
                    } else {
                        filteredCustomerName.add(selectedCustomer)
                        view.setBackgroundColor(0x9934B5E4.toInt()) // Highlight color
                    }
                }
                // Initialize selection state
                for (i in uniqueContractType.indices) {
                    val customer = uniqueContractType[i]
                    if (filteredContractType.contains(customer)) {
                        customerListView.setItemChecked(i, true)
                        customerListView.getChildAt(i)?.setBackgroundColor(0x9934B5E4.toInt()) // Highlight color
                    }
                }
                contractTypeListView.setOnItemClickListener{
                                                           _,view ,position,_ ->
                    val selectedContractType =uniqueContractType[position]
                    if (filteredContractType.contains(selectedContractType)){
                        filteredContractType.remove(selectedContractType)
                        view.setBackgroundColor(0x00000000) // Transparent background
                    }else{
                        filteredContractType.add(selectedContractType)
                        view.setBackgroundColor(0x9934B5E4.toInt()) // Highlight color

                    }
                    Log.d("filteredContractType","$filteredContractType")
                }
                val customerAllButton :Button =filterView.findViewById(R.id.customerAll)
                val radioChoices: RadioGroup = filterView.findViewById(R.id.statusRadioGroup)
                radioChoices.check(selectedRadioButtonId)
                radioChoices.setOnCheckedChangeListener { _, checkedId ->
                    selectedRadioButtonId = checkedId


                        when (checkedId) {
                            R.id.statusContractAll -> contractStatusRadio = "All"
                            R.id.statusContractActive -> contractStatusRadio= "Active"
                            R.id.statusContractExpired -> contractStatusRadio= "Expired"
                            else -> contractStatusRadio = "All"
                        }

                }
                customerAllButton.setOnClickListener {
                    filteredCustomerName.clear()
                    for (i in 0 until uniqueCustomerName.size) {
                        filteredCustomerName.add(uniqueCustomerName[i])
                        customerListView.setItemChecked(i, true)
                        customerListView.getChildAt(i)?.setBackgroundColor(0x9934B5E4.toInt())
                    }
                }
                val cancelButton : Button =filterView.findViewById(R.id.cancelButton)
                val applyButton : Button =filterView.findViewById(R.id.applyButton)
                cancelButton.setOnClickListener {
                    contractAdapter.setData(contractList)
                    filterWindow.dismiss()
                }
                applyButton.setOnClickListener {
                    filteredContractsList= contractList.filter {it.CustomerName in filteredCustomerName } as ArrayList<ContractsCustomerName>
                    filteredContractsList.filter { it.ContractType in filteredContractType } as ArrayList<ContractsCustomerName>
                    if (contractStatusRadio=="Active" || contractStatusRadio=="Expired"){
                        filteredContractsList.filter { it.ContractStatus==contractStatusRadio }
                    }
                    contractAdapter.setData(filteredContractsList)
                    filterWindow.dismiss()
                }


            }
            filterWindow.show(childFragmentManager, "FilterContract")


        }
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



    private fun showDatePickerDialog(textView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDay)
            val dateFormat = SimpleDateFormat(getString(R.string.app_date_format), Locale.getDefault())
            textView.text = dateFormat.format(selectedDate.time)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun filterList(query:String){
        val filteredList= ArrayList<ContractsCustomerName>()
        for (i in contractList){
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
    private fun passDataCustomer(data : ContractsCustomerName){

        val bundle = Bundle()
         bundle.putString("id",data.ContractID!!)


        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment = ContractInsertFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()

    }



}