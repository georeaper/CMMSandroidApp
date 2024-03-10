package com.gkprojects.cmmsandroidapp.Fragments.TechnicalCases

import android.app.AlertDialog
import android.app.Dialog
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.RecyclerViewAdapterEquipmentDialog
import com.gkprojects.cmmsandroidapp.Adapter.RvAlertAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentListInCases
import com.gkprojects.cmmsandroidapp.DataClasses.Tickets
import com.gkprojects.cmmsandroidapp.Models.CasesVM
import com.gkprojects.cmmsandroidapp.Models.EquipmentVM
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


@OptIn(DelicateCoroutinesApi::class)
class CaseInsertFragment : Fragment() {

    private lateinit var casesViewModel : CasesVM
    private lateinit var equipmentViewModel : EquipmentVM
    private lateinit var toolbar: MaterialToolbar
    var dialog: Dialog? = null
    var dialogEquipments: Dialog? = null
    var check : String? = null
    private var customerEquipment = ArrayList<EquipmentListInCases>()
    private var customerSearch =ArrayList<CustomerSelect>()
    private var rvAdapter: RvAlertAdapter? = null
    private var dialogEquipmentRvAdapter : RecyclerViewAdapterEquipmentDialog?= null
    lateinit var filterText : SearchView
    private var customerId :String? =null
    private var equipmentID : String? =null
    private var casesID: String? = null
    private var selectedItem: String? = null
    private var openDate :String?= null
    private var modifiedDate :String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
//        bottomNavigationView.selectedItemId=R.id.action_home

    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_case_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        casesViewModel= ViewModelProvider(this)[CasesVM::class.java]
        equipmentViewModel=ViewModelProvider(this)[EquipmentVM::class.java]
        toolbar = requireActivity().findViewById(R.id.topAppBar)
        toolbar.title = " Edit Technical Case"
        val navigationIcon = toolbar.navigationIcon
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            toolbar.setNavigationIcon(navigationIcon)
            val fragmentManager =parentFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            val fragment = CasesFragment()
            fragmentTransaction.replace(R.id.frameLayout1,fragment)
            fragmentTransaction.commit()
        }
        //val simpleItems = resources.getStringArray(R.array.cases_types)
        val simpleItems =getCasesTypesFromJson("caseUrgency.json")
        Log.d("simpleItems","$simpleItems")
        val startDatePicker = view.findViewById<MaterialAutoCompleteTextView>(R.id.datePicker_casesInsert)
        val closeDatePicker = view.findViewById<MaterialAutoCompleteTextView>(R.id.closedDate_casesInsert)
        val dropdownMenu: MaterialAutoCompleteTextView = view.findViewById(R.id.sp_tickets_autocomplete)
        val adapterDrop = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, simpleItems)
        dropdownMenu.setAdapter(adapterDrop)


        val args = this.arguments
        //casesID = args?.getInt("id") //CaseId
        casesID = if (args != null && args.containsKey("id")) args.getString("id") else null
        val customerStr = args?.getString("customerId") //CustomerID
        val customID: String? = customerStr //making customerID to INT

        Log.d("CasesTest","$casesID , $customID ")



        val customer=view.findViewById<TextView>(R.id.tv_customer_case)
        val equipment=view.findViewById<TextView>(R.id.tv_sn_case)



        casesViewModel.getCustomerId(requireContext()).observe(viewLifecycleOwner,
            androidx.lifecycle.Observer{
                customerSearch= it as ArrayList<CustomerSelect>

                if(customID!=null) {
                    for (i in it.indices) {
                        Log.d("test3","${it[i].CustomerID} $customID")

                        if (it[i].CustomerID == customID) {
                            customer.text = it[i].CustomerName
                            customerId=it[i].CustomerID
                            Log.d("casesTestCustomer","${customer.text} $customerId")

                        }
                    }
                }else{
                    Log.d("TestCustom","nully")
                }

            })





        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        val picker2 =builder.build()
        closeDatePicker.setOnClickListener{
            fragmentManager?.let { it1 -> picker2.show(it1, picker2.toString()) }
        }
        picker2.addOnPositiveButtonClickListener {
            val calendar2 = Calendar.getInstance()
            calendar2.timeInMillis = it
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val selectedDate = format.format(calendar2.time)
            closeDatePicker.setText(selectedDate)
        }

        startDatePicker.setOnClickListener {
            fragmentManager?.let { it1 -> picker.show(it1, picker.toString()) }
        }
        picker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val selectedDate = format.format(calendar.time)
            startDatePicker.setText(selectedDate)
        }

        Log.d("CustomerSearch",customerSearch.toString())

        if(casesID!=null){
            Log.d("UrgencyIndex", "${casesID}")
            lifecycleScope.launch { withContext(Dispatchers.Main){
                context?.let {
                    casesViewModel.getTicketDataById(it, casesID!!).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//                        Log.d("tickets",it.toString())
                        setUpField(it as Tickets)
                        val urgencyIndex: Int = adapterDrop.getPosition(it.Urgency)
                        Log.d("UrgencyIndex", urgencyIndex.toString())
             })
                }
            } }

        }
        customer.setOnClickListener {

            var builder = AlertDialog.Builder(context)

            builder.setView(R.layout.dialog_searchable_spinner)

            dialog?.window?.setLayout(650,800);

            // set transparent background
            dialog?.window?.setBackgroundDrawableResource(com.google.android.material.R.drawable.m3_tabs_transparent_background)


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

            rvAdapter!!.setOnClickListener(object : RvAlertAdapter.OnClickListener{
                override fun onClick(position: Int, model: CustomerSelect) {
                    var strtemp: String = model.CustomerName!!
                    customerId = model.CustomerID
                    Toast.makeText(requireContext(),customerId.toString(),Toast.LENGTH_SHORT).show()

                    customer.text = strtemp
                    dialog!!.dismiss()

                    equipmentViewModel.getEquipmentByCustomerId(requireContext(),customerId!!).observe(viewLifecycleOwner,
                        androidx.lifecycle.Observer {
                            customerEquipment =it as ArrayList<EquipmentListInCases>
                        })
                    Log.d("dataEquipment","$customerId + $customerEquipment ")


                }

            })




        }
        //casesID=id
        equipment.setOnClickListener {
            Log.d("customID_cases",customerId.toString())
            if (customerId!=null){

                getEquipmentData()

            }else{
                Toast.makeText(requireContext(),"Select Customer is required, check the field above",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getEquipmentData(){
        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.dialog_equipment_searchview)
        dialogEquipments?.window?.setLayout(650,800);
        dialogEquipments?.window?.setBackgroundDrawableResource(com.google.android.material.R.drawable.m3_tabs_transparent_background)
        dialogEquipments=builder.create()
        dialogEquipments?.show()
        val searchEquipment :SearchView= dialogEquipments!!.findViewById(R.id.dialog_equipment_searchView_equipment)
        val recycleViewEquipment: RecyclerView = dialogEquipments!!.findViewById(R.id.dialog_equipment_recyclerView)
        dialogEquipmentRvAdapter= RecyclerViewAdapterEquipmentDialog(customerEquipment)
        recycleViewEquipment.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = dialogEquipmentRvAdapter
        }
        dialogEquipmentRvAdapter!!.filterList(customerEquipment)
        Log.d("logingloging",customerEquipment.toString())


        searchEquipment.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("Query",query.toString())
                if (query != null) {
                    Log.d("testtesttest",customerEquipment.toString())
                    equipmentFilterList(query.lowercase(Locale.ROOT), customerEquipment)
                }
                return true
            }


        })

        dialogEquipmentRvAdapter!!.setOnClickListener(object : RecyclerViewAdapterEquipmentDialog.OnClickListener{
            override fun onClick(position: Int, model: EquipmentListInCases) {

                //Toast.makeText(requireContext(),model.toString(),Toast.LENGTH_SHORT).show()
                equipmentID=model.EquipmentID
                val equipment=view!!.findViewById<TextView>(R.id.tv_sn_case)
                equipment.text = model.Model+ ": "+ model.SerialNumber


                dialogEquipments!!.dismiss()

            }

        })

    }
    fun setUpField(tickets: Tickets ){
        Log.d("ticketsSetUpFields","$tickets + ${tickets.EquipmentID}")

        customerId=tickets.CustomerID!!
        if (tickets.EquipmentID!=null){
            equipmentID=tickets.EquipmentID!!
        }
        equipmentViewModel.getEquipmentByCustomerId(requireContext(),customerId!!).observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                customerEquipment =it as ArrayList<EquipmentListInCases>
            })

        val customer=requireView().findViewById<TextView>(R.id.tv_customer_case)
        //val dropdownMenu: MaterialAutoCompleteTextView = view.findViewById(R.id.sp_tickets_autocomplete) // Replace with your actual AutoCompleteTextView ID
        val titleCase:TextInputEditText=requireView().findViewById(R.id.caseInsertTitleTextInput)
        val description :TextInputEditText =requireView().findViewById(R.id.caseInsertDescriptionTextInput)
        val comments :TextInputEditText =requireView().findViewById(R.id.caseInsertCommentsTextInput)
        val active :MaterialCheckBox =requireView().findViewById(R.id.caseInsertMaterialCheckbox)
        val user :MaterialAutoCompleteTextView = requireView().findViewById(R.id.caseInsertSelectUserEdit)
        val startDatePicker = requireView().findViewById<MaterialAutoCompleteTextView>(R.id.datePicker_casesInsert)
        val closeDatePicker = requireView().findViewById<MaterialAutoCompleteTextView>(R.id.closedDate_casesInsert)
        val equipmentTv =requireView().findViewById<TextView>(R.id.tv_sn_case)
        //var simpleItems = arrayOf("RED","YELLOW","BLUE")
        //val simpleItems = resources.getStringArray(R.array.cases_types)
        val simpleItems =getCasesTypesFromJson("caseUrgency.json")
        Log.d("simpleItems","$simpleItems")
        val dropdownMenu: MaterialAutoCompleteTextView = requireView().findViewById(R.id.sp_tickets_autocomplete)
        val adapterDrop = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, simpleItems)
        dropdownMenu.setAdapter(adapterDrop)
        openDate=tickets.DateCreated
        modifiedDate=tickets.LastModified
        var tempEquipments = ArrayList<EquipmentListInCases>()

        if(customerId!=null){
            equipmentViewModel.getEquipmentByCustomerId(requireContext(), customerId!!).observe(viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    tempEquipments= it as ArrayList<EquipmentListInCases>
                    Log.d("equipmentIDCases","$equipmentID  ")

                    if(equipmentID!=null){
                        val specificEquipment = tempEquipments.find { it.EquipmentID == equipmentID }

                        // If an item is found, retrieve Model and SerialNumber
                        specificEquipment?.let {
                            val model = it.Model
                            val serialNumber = it.SerialNumber

                            equipmentTv.text= model +": "+serialNumber

                            // Here, you can use model and serialNumber as needed
                        }

                    }


                })
        }





        val valueToFind = tickets.Urgency.toString() // Replace with the value you're searching for

        // Find the index of the value in the simpleItems array
        val index = simpleItems.indexOf(valueToFind)

        if (index != -1) {
            // The value was found in the array
            //val positionToSelect = index
            val itemToSelect = adapterDrop.getItem(index)

            if (itemToSelect != null) {
                dropdownMenu.setText(itemToSelect.toString(), false)
            } else {

                // Handle the case where the item at the specified position is null
            }
        } else {
            //dropdownMenu.setText((adapterDrop.getItem(1)),false)
            // The value was not found in the array
            // Handle this case accordingly
        }

        Log.d("CustomerIDCases",customerId.toString())

        // Set the text and manually show the dropdown

        titleCase.setText(tickets.Title)
        description.setText(tickets.Description)
        comments.setText(tickets.Notes)
        user.setText(tickets.UserID?.toString())
        startDatePicker.setText(tickets.DateStart)
        closeDatePicker.setText(tickets.DateEnd)

        if (tickets.Active!=null){
            active.isChecked=true
        }



    }

        private fun getCasesTypesFromJson(filename: String): Array<String> {
            try {
                val file = File(requireActivity().filesDir, filename)
                if (!file.exists()) return emptyArray()

                val jsonString = file.readText()
                val jsonArray = JSONArray(jsonString)
                val names = mutableListOf<String>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val name = jsonObject.optString("name")
                    names.add(name)
                }

                return names.toTypedArray()
            } catch (e: Exception) {
                e.printStackTrace()
                return emptyArray() // Return an empty array in case of any exception
            }
        }

    private fun filterList(query: String,searchCustomer : ArrayList<CustomerSelect>) {
        val filteredList= ArrayList<CustomerSelect>()
        for (i in searchCustomer){
            if (i.CustomerName!!.lowercase(Locale.ROOT).contains(query))
                filteredList.add(i)

        }
        if (filteredList.isEmpty() ){
            Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

        }else{

            rvAdapter?.filterList(filteredList)
        }

    }
    private fun equipmentFilterList(query : String , searchEquipment : ArrayList<EquipmentListInCases>){
        val filteredList= ArrayList<EquipmentListInCases>()
        filteredList.clear()


        for (i in searchEquipment){
            Log.d("equipmentFilteredList",filteredList.toString())
            if (i.SerialNumber!!.lowercase(Locale.ROOT).contains(query))
                filteredList.add(i)


            }
        if (filteredList.isEmpty() ){
            dialogEquipmentRvAdapter!!.filterList(filteredList)
            Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

        }else{

            dialogEquipmentRvAdapter!!.filterList(filteredList)
        }

    }

    private fun insertData (ticket :Tickets){
        Log.d("ticketing","$ticket")
        GlobalScope.launch(Dispatchers.IO) {
            context?.let { it1 -> casesViewModel.insert(it1, ticket) }
            val fragmentManager =parentFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            val fragment = CasesFragment()
            fragmentTransaction.replace(R.id.frameLayout1,fragment)
            fragmentTransaction.commit()

        }

    }
    private fun updateData(ticket :Tickets){
        Log.d("ticketing2","$ticket")
        GlobalScope.launch(Dispatchers.IO) {
            context?.let { it1 -> casesViewModel.updateCustomer(it1, ticket) }

            val fragmentManager =parentFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            val fragment = CasesFragment()
            fragmentTransaction.replace(R.id.frameLayout1,fragment)
            fragmentTransaction.commit()

        }
    }
    fun buttonPressed(casesID :String?,selectedItem :String?){
        val customer=requireView().findViewById<TextView>(R.id.tv_customer_case)

        val titleCase:TextInputEditText=requireView().findViewById(R.id.caseInsertTitleTextInput)
        val description :TextInputEditText =requireView().findViewById(R.id.caseInsertDescriptionTextInput)
        val comments :TextInputEditText =requireView().findViewById(R.id.caseInsertCommentsTextInput)
        val active :MaterialCheckBox =requireView().findViewById(R.id.caseInsertMaterialCheckbox)
        var user :MaterialAutoCompleteTextView = requireView().findViewById(R.id.caseInsertSelectUserEdit)
        val startDatePicker = requireView().findViewById<MaterialAutoCompleteTextView>(R.id.datePicker_casesInsert)
        val closeDatePicker = requireView().findViewById<MaterialAutoCompleteTextView>(R.id.closedDate_casesInsert)
        var simpleItems = arrayOf("RED","YELLOW","BLUE")
        val dropdownMenu: MaterialAutoCompleteTextView = requireView().findViewById(R.id.sp_tickets_autocomplete)

        if(customerId!=null) {

            var dateStr =getCurrentDateAsString()
            var dateSet =""

            //Log.d("debugCasesInsert", case.toString())
            if (openDate==""){
                openDate=getCurrentDateAsString()
                dateSet=getCurrentDateAsString()

            }else{
                dateSet=getCurrentDateAsString()
            }


            if (casesID == null) {
                val case = Tickets(UUID.randomUUID().toString(),null,
                    titleCase.text.toString(),
                    description.text.toString(),
                    comments.text.toString(),
                    dropdownMenu.text.toString(),
                    active.isChecked,
                    startDatePicker.text.toString(),
                    closeDatePicker.text.toString(),
                    dateSet,
                    openDate,
                    null,
                    null,
                    customerId,
                    equipmentID
                )
                Log.d("cases","$case")

                insertData(case)

            } else {
                val case = Tickets(casesID,null,
                    titleCase.text.toString(),
                    description.text.toString(),
                    comments.text.toString(),
                    dropdownMenu.text.toString(),
                    active.isChecked,
                    startDatePicker.text.toString(),
                    closeDatePicker.text.toString(),
                    dateSet,
                    openDate,
                    null,
                    null,
                    customerId,
                    equipmentID
                )
                updateData(case)
            }

        }
        else{
            Toast.makeText(context,"Select Customer",Toast.LENGTH_SHORT).show()
        }


    }
    private fun getCurrentDateAsString(): String {
        // Get the current date
        val currentDate = LocalDate.now()

        // Define a format for the date (optional, you can skip this step if you don't need a specific format)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        // Format the date to a string

        return currentDate.format(formatter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Clear the existing menu items
        menu.clear()

        // Inflate the new menu for the fragment
        inflater.inflate(R.menu.menu_main, menu)
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Notify the system that the fragment has an options menu
        setHasOptionsMenu(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.submit_menu_btn -> {

                Log.d("pressed","these are $casesID + $selectedItem")
                buttonPressed(casesID, selectedItem)

                return true
            }
            R.id.cancel_menu_btn -> {
                Toast.makeText(context,"Delete is UNAVAILABLE due to credentials , ",Toast.LENGTH_SHORT).show()
                // Handle menu item 2
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


}


