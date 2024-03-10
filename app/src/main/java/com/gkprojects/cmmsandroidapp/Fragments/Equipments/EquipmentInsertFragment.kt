package com.gkprojects.cmmsandroidapp.Fragments.Equipments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.CasesListEquipmentInsertAdapter

import com.gkprojects.cmmsandroidapp.Adapter.RvAdapterFindCustomers
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect

import com.gkprojects.cmmsandroidapp.DataClasses.Equipments
import com.gkprojects.cmmsandroidapp.DataClasses.Tickets
import com.gkprojects.cmmsandroidapp.Fragments.AppDataLoader


import com.gkprojects.cmmsandroidapp.Models.EquipmentVM
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.FragmentEquipmentInsertBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class EquipmentInsertFragment : Fragment() {

    private lateinit var equipmentViewModel:EquipmentVM
    private lateinit var toolbar: MaterialToolbar
    private lateinit var binding : FragmentEquipmentInsertBinding
    private var dialog: Dialog? = null
    private var rvAdapter: RvAdapterFindCustomers? = null
    private var rvAdapterTickets : CasesListEquipmentInsertAdapter? =null
    private lateinit var filterText : SearchView
    private var customerId : String?= null
    private var equipmentId : String?= null
    private var lastModified : String? = null
    private var dateCreated : String? = null
    private var version : String? = null
    private var customerSearch =ArrayList<CustomerSelect>()
    private var tickets =ArrayList<Tickets>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
//        bottomNavigationView.selectedItemId=R.id.action_home
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEquipmentInsertBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        equipmentViewModel= ViewModelProvider(this)[EquipmentVM::class.java]
        val appDataLoader = AppDataLoader(requireContext())
        val manufacturerArray = appDataLoader.getDataFromJson("equipmentManufacturer.json")
        val modelArray = appDataLoader.getDataFromJson("equipmentModel.json")
        val categoryArray = appDataLoader.getDataFromJson("equipmentCategory.json")
        val statusEquipmentArray = appDataLoader.getDataFromJson("equipmentStatus.json")

        
        val args =this.arguments
        equipmentId= args?.getString("EquipmentId")
        toolbar = requireActivity().findViewById(R.id.topAppBar)
        toolbar.title = " Edit Equipment"
        val navigationIcon = toolbar.navigationIcon
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            toolbar.navigationIcon = navigationIcon
            val fragmentManager =parentFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            val fragment = EquipmentFragment()
            fragmentTransaction.replace(R.id.frameLayout1,fragment)
            fragmentTransaction.commit()

        }


        val serialNumber=view.findViewById<TextInputEditText>(R.id.equipment_insert_textInputEdittext_sn)

        val model=view.findViewById<MaterialAutoCompleteTextView>(R.id.equipment_insert_textInputEdittext_model)
        val adapterDropModel = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, modelArray)
        model.setAdapter(adapterDropModel)

        val manufacturer=view.findViewById<MaterialAutoCompleteTextView>(R.id.equipment_insert_autoCompleteTextView_manufacturer)
        val adapterDropManufacturer = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, manufacturerArray)
        manufacturer.setAdapter(adapterDropManufacturer)

        val category=view.findViewById<MaterialAutoCompleteTextView>(R.id.equipment_insert_autoCompleteTextView_category)
        val adapterDropCategory = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categoryArray)
        category.setAdapter(adapterDropCategory)

        val status=view.findViewById<MaterialAutoCompleteTextView>(R.id.equipment_insert_autoCompleteTextView_status)
        val adapterDropStatus = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, statusEquipmentArray)
        status.setAdapter(adapterDropStatus)

        val installation=view.findViewById<MaterialAutoCompleteTextView>(R.id.equipment_insert_autoCompleteTextView_installation)
        val warranty=view.findViewById<TextInputEditText>(R.id.equipment_insert_textInputEdittext_warranty)
        val version_equipment=view.findViewById<TextInputEditText>(R.id.equipment_insert_textInputEdittext_version)
        val customerNameTV=view.findViewById<TextView>(R.id.tv_select_customer)

        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        installation.setOnClickListener {
            fragmentManager?.let { it1 -> picker.show(it1, picker.toString()) }
        }
        picker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val selectedDate = format.format(calendar.time)
            installation.setText(selectedDate)
        }

        val imgButtonEquipmentInfo =binding.equipmentInsertLinearLayoutEquipmentInformationImgButton
        val infoLayoutEquipmentInfo =binding.equipmentInsertLinearLayoutEquipmentInfo
        val imgButtonEquipmentList =binding.equipmentInsertLinearLayoutEquipmentListImgButton
        val infoLayoutEquipmentList =binding.equipmentInsertLinearLayoutRecyclerView
        val isVisibleEquipmentList = getSavedVisibilityState("equipmentList",true)

        val isVisibleEquipmentInfo = getSavedVisibilityState("equipmentInfo",true)
        if (isVisibleEquipmentInfo){
            infoLayoutEquipmentInfo.visibility=View.VISIBLE
            imgButtonEquipmentInfo.setImageResource(R.drawable.remove_expandable_icon)

        }else{
            infoLayoutEquipmentInfo.visibility=View.GONE
            imgButtonEquipmentInfo.setImageResource(R.drawable.add_expandable_icon)

        }
        if (isVisibleEquipmentList){
            infoLayoutEquipmentList.visibility=View.VISIBLE
            imgButtonEquipmentList.setImageResource(R.drawable.remove_expandable_icon)

        }else{
            infoLayoutEquipmentList.visibility=View.GONE
            imgButtonEquipmentList.setImageResource(R.drawable.add_expandable_icon)

        }
        imgButtonEquipmentInfo.setOnClickListener {
            if (infoLayoutEquipmentInfo.visibility == View.VISIBLE) {
                infoLayoutEquipmentInfo.visibility = View.GONE
                saveVisibilityState("equipmentInfo",false)
                imgButtonEquipmentInfo.setImageResource(R.drawable.add_expandable_icon)
            } else {
                infoLayoutEquipmentInfo.visibility = View.VISIBLE
                saveVisibilityState("equipmentInfo",true)
                imgButtonEquipmentInfo.setImageResource(R.drawable.remove_expandable_icon)
            }
        }
        imgButtonEquipmentList.setOnClickListener {
            if (infoLayoutEquipmentList.visibility == View.VISIBLE) {
                infoLayoutEquipmentList.visibility = View.GONE
                saveVisibilityState("equipmentInfo",false)
                imgButtonEquipmentList.setImageResource(R.drawable.add_expandable_icon)
            } else {
                infoLayoutEquipmentList.visibility = View.VISIBLE
                saveVisibilityState("equipmentInfo",true)
                imgButtonEquipmentList.setImageResource(R.drawable.remove_expandable_icon)
            }
        }
        //fetching data based on equipmentID

        if (equipmentId!=null){
            equipmentViewModel.getRecordById(requireContext(),equipmentId!!).observe(viewLifecycleOwner,
                Observer {
                    serialNumber.setText(it.SerialNumber);
                    model.setText(it.Model)
                    manufacturer.setText(it.Manufacturer)
                    warranty.setText(it.Warranty)
                    category.setText(it.EquipmentCategory)
                    installation.setText(it.InstallationDate)
                    status.setText(it.EquipmentStatus)
                    version_equipment.setText(it.EquipmentVersion)
                    equipmentId=it.EquipmentID
                    customerId=it.CustomerID!!
                    dateCreated=it.DateCreated
                    Log.d("CustomerID2","$customerId")
                    setCustomer(customerId!!)
                })
            equipmentViewModel.getTicketByEquipmentId(requireContext(),equipmentId!!).observe(viewLifecycleOwner,
                Observer {
                    tickets=it as ArrayList<Tickets>
                    setCaseLists(tickets)
                })

        }else{
            lifecycleScope.launch {
                try {
                    withContext(Dispatchers.Main){
                        equipmentViewModel.getCustomerId(requireContext()).observe(viewLifecycleOwner,
                            Observer {
                                customerSearch = it as ArrayList<CustomerSelect>


                            })
                    }

                }catch (e: Exception){
                    Log.d("catchEquipment",e.toString())
                }
            }
        }





            customerNameTV.setOnClickListener {

                val builder =AlertDialog.Builder(context)

                builder.setView(R.layout.dialog_searchable_spinner)

                dialog?.window?.setLayout(650,800);

                // set transparent background
                dialog?.window?.setBackgroundDrawableResource(com.google.android.material.R.drawable.m3_tabs_transparent_background)


                dialog=builder.create()
                // show dialog
                dialog?.show();


                val recycleView: RecyclerView = dialog!!.findViewById(R.id.rv_searchable_TextView)
                 this.filterText = dialog!!.findViewById(R.id.searchView_rv_customers)

                rvAdapter = context?.let { it1 -> RvAdapterFindCustomers(it1, customerSearch) }
                recycleView.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this.context)
                    adapter = rvAdapter
                }
                filterText.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
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

                rvAdapter!!.setOnClickListener(object :RvAdapterFindCustomers.OnClickListener{
                    override fun onClick(position: Int, model: CustomerSelect) {
                        var strtemp: String = model.CustomerName!!
                        customerId = model.CustomerID

                        customerNameTV.text = strtemp
                        dialog!!.dismiss();

                    }

                })


            }


    }

    private fun setCaseLists(tickets: ArrayList<Tickets>) {
        val recyclerTickets = binding.equipmentInsertRecyclerView
        val rvAdapterTickets = CasesListEquipmentInsertAdapter(tickets)
        recyclerTickets.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = rvAdapterTickets
        }
        rvAdapterTickets.setData(tickets)

    }

    private fun saveVisibilityState(key: String, isVisible: Boolean) {
        val sharedPreferences = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(key, isVisible).apply()
    }

    private fun getSavedVisibilityState(key: String, defaultVisibility: Boolean): Boolean {
        val sharedPreferences = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, defaultVisibility)
    }


    private fun filterList(query: String,searchCustomer : ArrayList<CustomerSelect>) {
        val filteredList= java.util.ArrayList<CustomerSelect>()
        for (i in searchCustomer){
            if (i.CustomerName!!.lowercase(Locale.ROOT).contains(query))
                filteredList.add(i)
            Log.d("datafilterDialog", filteredList.toString())
        }
        if (filteredList.isEmpty() ){
            Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

        }else{

            rvAdapter?.filterList(filteredList)
        }

    }
    fun getValuesFromdb(data : ArrayList<CustomerSelect>, id :String?, tv :TextView) {
        val customerNameIndexed = mutableMapOf<String?, String?>()

        for (i in data.indices) {

//            data[i].CustomerID.toString()
            customerNameIndexed[data[i].CustomerID] = data[i].CustomerName
        }
        tv.text = customerNameIndexed[id]
    }
    private fun setCustomer(id : String){
        val customerTextView=binding.tvSelectCustomer

        lifecycleScope.launch {
            try {
                withContext(Dispatchers.Main){
                    equipmentViewModel.getCustomerId(requireContext()).observe(viewLifecycleOwner,
                        Observer {
                            customerSearch = it as ArrayList<CustomerSelect>
                            Log.d("CustomerID3","$customerId")
                            getValuesFromdb(customerSearch, id, customerTextView)
                        })
                }

            }catch (e: Exception){
                Log.d("catchEquipment",e.toString())
            }
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


                if (equipmentId!=null){
                    Log.d("here!=null","equipmentHere")
                    updateData()

                }else{
                    Log.d("here","equipmentHere")
                    insertData()

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

    private fun insertData() {
        lastModified = getCurrentDateAsString()
        dateCreated = getCurrentDateAsString()
        val insertEquipment = Equipments(UUID.randomUUID().toString(),
            null,
            null,
            binding.equipmentInsertTextInputEdittextSn.text.toString(),
            binding.equipmentInsertTextInputEdittextModel.text.toString(),
            binding.equipmentInsertAutoCompleteTextViewManufacturer.text.toString(),
            null,
            binding.equipmentInsertTextInputEdittextDescription.text.toString(),
            binding.equipmentInsertTextInputEdittextVersion.text.toString(),
            binding.equipmentInsertAutoCompleteTextViewCategory.text.toString(),
            binding.equipmentInsertTextInputEdittextWarranty.text.toString(),
            binding.equipmentInsertAutoCompleteTextViewStatus.text.toString(),
            binding.equipmentInsertAutoCompleteTextViewInstallation.text.toString(),
            lastModified,
            dateCreated,
            version,
            customerId

            )
        Log.d("equipmentInsert","$insertEquipment")

        GlobalScope.launch(Dispatchers.IO) {equipmentViewModel.insert(requireContext(),insertEquipment)  }
        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment = EquipmentFragment()
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()
    }

    private fun updateData() {
        lastModified = getCurrentDateAsString()
        //dateCreated = getCurrentDateAsString()
        val updateEquipment = Equipments(equipmentId!!,
            null,
            null,
            binding.equipmentInsertTextInputEdittextSn.text.toString(),
            binding.equipmentInsertTextInputEdittextModel.text.toString(),
            binding.equipmentInsertAutoCompleteTextViewManufacturer.text.toString(),
            null,
            binding.equipmentInsertTextInputEdittextDescription.text.toString(),
            binding.equipmentInsertTextInputEdittextVersion.text.toString(),
            binding.equipmentInsertAutoCompleteTextViewCategory.text.toString(),
            binding.equipmentInsertTextInputEdittextWarranty.text.toString(),
            binding.equipmentInsertAutoCompleteTextViewStatus.text.toString(),
            binding.equipmentInsertAutoCompleteTextViewInstallation.text.toString(),
            lastModified,
            dateCreated,
            version,
            customerId
        )

        GlobalScope.launch(Dispatchers.IO) {equipmentViewModel.updateEquipment(requireContext(),updateEquipment)  }

        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment = EquipmentFragment()
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()
    }


}





