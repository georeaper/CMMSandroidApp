package com.gkprojects.cmmsandroidapp.Fragments.Contracts

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.EquipmentDropDownAdapter

import com.gkprojects.cmmsandroidapp.Adapter.RvAlertAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.ContractEquipments

import com.gkprojects.cmmsandroidapp.DataClasses.Contracts
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.DetailedContract
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentListInCases
import com.gkprojects.cmmsandroidapp.Fragments.CasesFragment
import com.gkprojects.cmmsandroidapp.Fragments.CustomerFragment
import com.gkprojects.cmmsandroidapp.Models.EquipmentVM


import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.FragmentContractInsertBinding
import com.gkprojects.cmmsandroidapp.databinding.FragmentDashboardCustomerBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

import kotlin.collections.ArrayList


class ContractInsertFragment : Fragment() {
    private lateinit var binding: FragmentContractInsertBinding
    private lateinit var contractViewModel: ContractsVM
    private lateinit var equipmentViewModel : EquipmentVM
    private lateinit var toolbar: MaterialToolbar
    var dialog: Dialog? = null
    private var rvAdapter: RvAlertAdapter? = null
    private var rvAdapterEquipmentList : ContractInsertEquipmentListAdapter? =null
    private var recyclerViewEquipment :RecyclerView?= null
    private lateinit var filterText : SearchView
    private var customerId : Int?= null
    private var contractId :Int? =null
    private var dateCreated : String? = ""
    private var customerSearch =ArrayList<CustomerSelect>()
    private var contractEquipment= ArrayList<DetailedContract>()
    private var equipmentsByCustomer =ArrayList<EquipmentListInCases>()
    private var selectedEquipment: EquipmentListInCases? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContractInsertBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId=R.id.action_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contractViewModel= ViewModelProvider(this)[ContractsVM::class.java]
        equipmentViewModel= ViewModelProvider(this)[EquipmentVM::class.java]
        recyclerViewEquipment=binding.contractInsertRecyclerViewEquipmentList

        toolbar = requireActivity().findViewById(R.id.topAppBar)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId=R.id.action_home
        toolbar.title = " Edit Contract"
        val navigationIcon = toolbar.navigationIcon
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            toolbar.navigationIcon = navigationIcon
            val fragmentManager =parentFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            val fragment = ContractFragment()
            fragmentTransaction.replace(R.id.frameLayout1,fragment)
            fragmentTransaction.commit()

        }
        val imgButtonContractInfo =binding.contractInsertLinearLayoutInfoImgButton
        val infoLayoutContractInfo =binding.contractInsertLinearLayoutInfo

        val isVisibleContractInfo = getSavedVisibilityState("contractInfo",true)
        if (isVisibleContractInfo){
            infoLayoutContractInfo.visibility=View.VISIBLE
            imgButtonContractInfo.setImageResource(R.drawable.remove_expandable_icon)

        }else{
            infoLayoutContractInfo.visibility=View.GONE
            imgButtonContractInfo.setImageResource(R.drawable.add_expandable_icon)

        }


        val startDate =view.findViewById<MaterialAutoCompleteTextView>(R.id.contractInsert_TextInputEditText_StartDate)
        val endDate =view.findViewById<MaterialAutoCompleteTextView>(R.id.contractInsert_TextInputEditText_EndDate)
        val contractCustomer =view.findViewById<TextView>(R.id.tv_customerName_contract)
        val title = view.findViewById<TextInputEditText>(R.id.contractInsert_TextInputEditText_Title)
        val description = view.findViewById<TextInputEditText>(R.id.contractInsert_TextInputEditText_Description)
        val notes = view.findViewById<TextInputEditText>(R.id.contractInsert_TextInputEditText_Notes)
        val contactName = view.findViewById<TextInputEditText>(R.id.contractInsert_TextInputEditText_ContactName)
        val contractType = view.findViewById<MaterialAutoCompleteTextView>(R.id.contractInsert_TextInputEditText_ContractType)
        val contractStatus = view.findViewById<MaterialAutoCompleteTextView>(R.id.contractInsert_TextInputEditText_ContractStatus)
        val contractValue = view.findViewById<TextInputEditText>(R.id.contractInsert_TextInputEditText_Value)

        imgButtonContractInfo.setOnClickListener {
            if (infoLayoutContractInfo.visibility == View.VISIBLE) {
                infoLayoutContractInfo.visibility = View.GONE
                saveVisibilityState("contractInfo",false)
                imgButtonContractInfo.setImageResource(R.drawable.add_expandable_icon)
            } else {
                infoLayoutContractInfo.visibility = View.VISIBLE
                saveVisibilityState("contractInfo",true)
                imgButtonContractInfo.setImageResource(R.drawable.remove_expandable_icon)
            }
        }

        lifecycleScope.launch {
            contractViewModel.getCustomerId(requireContext()).observe(viewLifecycleOwner, Observer {
                customerSearch= it as ArrayList<CustomerSelect>
            })
        }

        val args =this.arguments
         contractId= args?.getInt("id")

        if(contractId!=null){

            contractViewModel.getContractByID(requireContext(),contractId!!).observe(viewLifecycleOwner,
                Observer {
                    startDate.setText(it.DateStart)
                    endDate.setText(it.DateEnd)
                    title.setText(it.Title)
                    description.setText(it.Description)
                    notes.setText(it.Notes)
                    contactName.setText(it.ContactName)
                    contractStatus.setText(it.ContractStatus)
                    contractType.setText(it.ContractType)
                    contractValue.setText(it.Value.toString())
                    customerId=it.CustomerID!!.toInt()
                    dateCreated=it.DateCreated
                    setCustomer(customerId!!)
                    getEquipmentByCustomerId(it.CustomerID!!.toInt())

                })
            val btnAddEquipments : ImageButton=binding.contractInsertImgButtonListEquipments
            val visits : TextInputEditText=binding.contractInsertTextInputEditTextVistsEquipmentList
            val dropdown : AutoCompleteTextView=binding.contractInsertAutocomplateEquipmentList
            btnAddEquipments.setOnClickListener {

                selectedEquipment?.let {
                    val dateCreated =getCurrentDateAsString()
                    val data = ContractEquipments(null,null,null,visits.text.toString().toDoubleOrNull(),contractId,
                        selectedEquipment!!.EquipmentID,null,dateCreated,null)
                    Toast.makeText(requireContext(),"$data",Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        contractViewModel.insertContractEquipment(requireContext(),data)
                    }

                } ?: Toast.makeText(requireContext(), "No item selected", Toast.LENGTH_SHORT).show()
                dropdown.clearListSelection()
                visits.setText("")

            }


            try{

            contractViewModel.getDetailedContractByID(requireContext(),contractId!!.toInt()).observe(viewLifecycleOwner,
                Observer {
                    contractEquipment.clear()
                    for (i in it.indices){

                        contractEquipment.add(it[i])
                    }
                    setUpContractEquipment()
                    Log.d("contractDetailedInfo","$contractEquipment")

                })





            }
            catch (e:Exception){
                Log.d("LogEcontractDetailedInfo","$e")
            }

        }
        val imgButtonContractEquipment =binding.contractInsertLinearLayoutEquipmentListImgButton
        val infoLayoutContractEquipment =binding.contractInserLinearLayoutEquipmentList

        val isVisibleContractEquipment = getSavedVisibilityState("contractEquipment",true)
        if (isVisibleContractEquipment){
            infoLayoutContractEquipment.visibility=View.VISIBLE
            imgButtonContractEquipment.setImageResource(R.drawable.remove_expandable_icon)

        }else{
            infoLayoutContractEquipment.visibility=View.GONE
            imgButtonContractEquipment.setImageResource(R.drawable.add_expandable_icon)

        }

        imgButtonContractEquipment.setOnClickListener {
            if (infoLayoutContractEquipment.visibility == View.VISIBLE) {
                infoLayoutContractEquipment.visibility = View.GONE
                saveVisibilityState("contractEquipment",false)
                imgButtonContractEquipment.setImageResource(R.drawable.add_expandable_icon)
            } else {
                infoLayoutContractEquipment.visibility = View.VISIBLE
                saveVisibilityState("contractEquipment",true)
                imgButtonContractEquipment.setImageResource(R.drawable.remove_expandable_icon)
            }
        }


        contractCustomer.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            builder.setView(R.layout.dialog_searchable_spinner)

            dialog?.getWindow()?.setLayout(650,800);

            // set transparent background
            dialog?.getWindow()?.setBackgroundDrawableResource(com.google.android.material.R.drawable.m3_tabs_transparent_background)


            dialog=builder.create()

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
                    customerId = model.CustomerID

                    contractCustomer.text = strtemp
                    dialog!!.dismiss();

                }

            })
        }


    }

    private fun saveVisibilityState(key: String, isVisible: Boolean) {
        val sharedPreferences = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(key, isVisible).apply()
    }

    private fun getSavedVisibilityState(key: String, defaultVisibility: Boolean): Boolean {
        val sharedPreferences = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, defaultVisibility)
    }

    private fun setCustomer(id : Int){
        val customerTextView=binding.tvCustomerNameContract

        lifecycleScope.launch {
            try {
                withContext(Dispatchers.Main){
                    contractViewModel.getCustomerId(requireContext()).observe(viewLifecycleOwner,
                        Observer {
                            customerSearch = it as java.util.ArrayList<CustomerSelect>
                            Log.d("CustomerID3","$customerId")
                            getValuesFromdb(customerSearch, id, customerTextView)
                        })
                }

            }catch (e: Exception){
                Log.d("catchEquipment",e.toString())
            }
        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun getEquipmentByCustomerId(id : Int){
        equipmentViewModel.getEquipmentByCustomerId(requireContext(),id).observe(viewLifecycleOwner,
            Observer {
                Log.d("contractContract","$it")
                if (it!=null){
                equipmentsByCustomer= it as ArrayList<EquipmentListInCases>
                    Log.d("ContractEquipments","$equipmentsByCustomer")
                    val equipmentMap = HashMap<Int, String>()
                    equipmentsByCustomer.forEach { equipment ->
                        equipment.EquipmentID?.let { id ->
                            equipment.SerialNumber?.let { serialNumber ->
                                equipmentMap[id] = serialNumber
                            }
                        }
                    }


                    val dropdownEquipments = binding.contractInsertAutocomplateEquipmentList
                    val adapterDropdown = EquipmentDropDownAdapter(requireContext(), equipmentsByCustomer)
                    dropdownEquipments.setAdapter(adapterDropdown)
                    dropdownEquipments.setOnItemClickListener { adapterView, view, position, id ->
                        selectedEquipment = adapterDropdown.getItem(position) as EquipmentListInCases

                        Toast.makeText(requireContext(),selectedEquipment.toString(),Toast.LENGTH_SHORT).show()
                        // Now selectedEquipment holds the selected item
                    }
            }

            })
    }

    private fun updateData(){
        val value : String =binding.contractInsertTextInputEditTextValue.text.toString()
        val dValue = value.toDoubleOrNull()
        val dateCurrent = getCurrentDateAsString()
        val updateContracts = Contracts(contractId!!,null ,
            binding.contractInsertTextInputEditTextTitle.text.toString(),
            binding.contractInsertTextInputEditTextStartDate.text.toString(),
            binding.contractInsertTextInputEditTextEndDate.text.toString(),
            dValue,
            binding.contractInsertTextInputEditTextNotes.text.toString(),
            binding.contractInsertTextInputEditTextDescription.text.toString(),
            binding.contractInsertTextInputEditTextContractType.text.toString(),
            binding.contractInsertTextInputEditTextContractStatus.text.toString(),
            binding.contractInsertTextInputEditTextContactName.text.toString(),
            dateCurrent,
            dateCreated,
            null,
            customerId

            )
        GlobalScope.launch(Dispatchers.IO) {
            contractViewModel.updateContract(requireContext(),updateContracts)
        }
        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment = ContractFragment()
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()
    }
    private fun insertData(){
        val value : String =binding.contractInsertTextInputEditTextValue.text.toString()
        val dValue = value.toDoubleOrNull()
        val dateCurrent = getCurrentDateAsString()
        val updateContracts = Contracts(contractId!!,null ,
            binding.contractInsertTextInputEditTextTitle.text.toString(),
            binding.contractInsertTextInputEditTextStartDate.text.toString(),
            binding.contractInsertTextInputEditTextEndDate.text.toString(),
            dValue,
            binding.contractInsertTextInputEditTextNotes.text.toString(),
            binding.contractInsertTextInputEditTextDescription.text.toString(),
            binding.contractInsertTextInputEditTextContractType.text.toString(),
            binding.contractInsertTextInputEditTextContractStatus.text.toString(),
            binding.contractInsertTextInputEditTextContactName.text.toString(),
            dateCreated,
            dateCurrent,
            null,
            customerId

        )
        GlobalScope.launch(Dispatchers.IO) {
            contractViewModel.insert(requireContext(),updateContracts)
        }
        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment = ContractFragment()
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Clear the existing menu items
        menu.clear()

        // Inflate the new menu for the fragment
        inflater.inflate(R.menu.menu_main, menu)
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        super.onCreateOptionsMenu(menu, inflater)
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Notify the system that the fragment has an options menu
        setHasOptionsMenu(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.submit_menu_btn -> {



                if (contractId!=null){
                    updateData()

                }else{
                    if (customerId!=null) {
                        insertData()
                    }else{
                        Toast.makeText(requireContext(),"The selection of Customer is Required",Toast.LENGTH_SHORT).show()
                    }

                }

                return true
            }
            R.id.cancel_menu_btn -> {
                Toast.makeText(context,"Delete is UNAVAILABLE due to credentials , ", Toast.LENGTH_SHORT).show()
                // Handle menu item 2
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
    fun getCurrentDateAsString(): String {
        // Get the current date
        val currentDate = LocalDate.now()

        // Define a format for the date (optional, you can skip this step if you don't need a specific format)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        // Format the date to a string
        val formattedDate = currentDate.format(formatter)

        return formattedDate
    }

    private fun setUpContractEquipment(){
        recyclerViewEquipment=binding.contractInsertRecyclerViewEquipmentList
        if (contractEquipment!=null) {
            rvAdapterEquipmentList = ContractInsertEquipmentListAdapter(contractEquipment)
            rvAdapterEquipmentList!!.setOnClickListener(object : ContractInsertEquipmentListAdapter.OnClickListener {
                override fun onDeleteItem(position: Int, item: DetailedContract) {
                    deleteEquipmentFromContractEquipment(item)
                    rvAdapterEquipmentList!!.removeItemAt(position)
                }
            })
            recyclerViewEquipment?.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this.context)
                adapter = rvAdapterEquipmentList
            }
            rvAdapterEquipmentList!!.setData(contractEquipment)


        }

    }

    private fun deleteEquipmentFromContractEquipment(item: DetailedContract) {
        val tempId = item.ContractEquipmentID
        var tempContractEquipment : ContractEquipments
        if (tempId!=null) {
            contractViewModel.getContractEquipmentByContractEquipmentID(requireContext(), tempId).observe(viewLifecycleOwner,
                Observer {
                    Log.d("deleteMethod","$it")
                    if(it!=null){
                         tempContractEquipment =it as ContractEquipments
                        Log.d("deleteMethodEmpty","$tempContractEquipment")
                        deleteItem(tempContractEquipment)
                    }

                })
        }
    }

    private fun deleteItem(tempContractEquipment: ContractEquipments) {
        lifecycleScope.launch(Dispatchers.IO) {
            contractViewModel.deleteContractEquipment(requireContext(),tempContractEquipment)
        }


    }

}



