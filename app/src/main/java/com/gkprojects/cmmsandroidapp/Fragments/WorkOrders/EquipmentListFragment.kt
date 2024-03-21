package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.AdapterRecyclerViewChecklist
import com.gkprojects.cmmsandroidapp.Adapter.AdapterRecyclerViewDialogEquipmentList
import com.gkprojects.cmmsandroidapp.Adapter.AdapterRecyclerViewEquipmentList
import com.gkprojects.cmmsandroidapp.Adapter.DropDownAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.CheckForms
import com.gkprojects.cmmsandroidapp.DataClasses.CustomDisplayDatFieldReportEquipments
import com.gkprojects.cmmsandroidapp.DataClasses.Equipments
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportCheckForm
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportEquipment
import com.gkprojects.cmmsandroidapp.DataClasses.Maintenances
import com.gkprojects.cmmsandroidapp.DataClasses.Tools
import com.gkprojects.cmmsandroidapp.Models.CheckFormVM
import com.gkprojects.cmmsandroidapp.Models.EquipmentVM
import com.gkprojects.cmmsandroidapp.Models.FieldReportCheckListVM
import com.gkprojects.cmmsandroidapp.Models.FieldReportEquipmentVM
import com.gkprojects.cmmsandroidapp.Models.MaintenancesVM
import com.gkprojects.cmmsandroidapp.Models.SharedViewModel
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.DialogChecklistWorkorderPerEquipmentBinding
import com.gkprojects.cmmsandroidapp.databinding.DialogEquipmentListWorkordersBinding
import com.gkprojects.cmmsandroidapp.databinding.FragmentEquipmentListBinding
import kotlinx.coroutines.*
import java.util.UUID


class EquipmentListFragment : Fragment() {
    private lateinit var binding : FragmentEquipmentListBinding
    private lateinit var adapterDialogEquipmentSelection : AdapterRecyclerViewDialogEquipmentList
    private lateinit var adapterEquipmentList : AdapterRecyclerViewEquipmentList
    private lateinit var adapterEquipmentChecklist: AdapterRecyclerViewChecklist

    private lateinit var adapterDropDownChecklist : DropDownAdapter
    private var reportId : String?=null
    private var customerId : String? =null
    private var isObserverSetUp = false
    private var customerObserverSetUp = false

    private lateinit var equipmentViewModel : EquipmentVM
    private lateinit var equipmentFieldReportViewModel : FieldReportEquipmentVM
    private lateinit var checkFormVM : CheckFormVM
    private lateinit var maintenancesVM : MaintenancesVM
    private lateinit var fieldReportCheckListVM : FieldReportCheckListVM


    private lateinit var recyclerViewEquipmentList: RecyclerView

    private var selectedEquipment: CustomDisplayDatFieldReportEquipments? =null
    private var equipmentsList = ArrayList<CustomDisplayDatFieldReportEquipments>()
    private var maintenancesList = ArrayList<Maintenances>()
    private var checkformList = ArrayList<FieldReportCheckForm>()

    private var maintenancesMap = ArrayList<Pair<String?, String?>>()
    private var checkFormItems = ArrayList<CheckForms>()
    private var maintenanceCheckListItems=ArrayList<FieldReportCheckForm>()

    private var dialogEquipmentList = ArrayList<Equipments>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEquipmentListBinding.inflate(inflater, container, false)
        return binding.root
       // return inflater.inflate(R.layout.fragment_equipment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        maintenancesVM=ViewModelProvider(this)[MaintenancesVM::class.java]
        checkFormVM=ViewModelProvider(this)[CheckFormVM::class.java]
        equipmentViewModel= ViewModelProvider(this)[EquipmentVM::class.java]
        equipmentFieldReportViewModel=ViewModelProvider(this)[FieldReportEquipmentVM::class.java]
        fieldReportCheckListVM =ViewModelProvider(this)[FieldReportCheckListVM::class.java]

        val sharedViewModel: SharedViewModel by activityViewModels()

        if (!isObserverSetUp) {
            sharedViewModel.reportId.observe(viewLifecycleOwner, Observer { id ->
                Log.d("sharedViewModelReportId", "$id")
                reportId = id
                getEquipmentsByID(reportId as String)
            })
            isObserverSetUp = true
        }
        if (!customerObserverSetUp) {
            sharedViewModel.customerId.observe(viewLifecycleOwner, Observer { id ->
                Log.d("sharedViewModelCustomerId", "$id")
                customerId = id
                //getEquipmentsByID(reportId as Int)
            })
            customerObserverSetUp = true
        }
        //adapterDropDownChecklist = DropDownAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, maintenancesMap)
        maintenancesVM.getAllMaintenances(requireContext()).observe(
            viewLifecycleOwner,
            Observer{
                maintenancesList =it as ArrayList<Maintenances>

                Log.d("testMaintenanceMap", "$maintenancesList")

                //adapterDropDownChecklist.clear()
               // adapterDropDownChecklist.notifyDataSetChanged()

            })


        recyclerViewEquipmentList=binding.equipmentListRecyclerView
        adapterEquipmentList= AdapterRecyclerViewEquipmentList(equipmentsList)
        recyclerViewEquipmentList.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this.context)
            adapter= adapterEquipmentList
        }
        adapterEquipmentList.setOnItemClickListener(object : AdapterRecyclerViewEquipmentList.OnItemClickListener {
            override fun onItemClick(equipment: CustomDisplayDatFieldReportEquipments) {
                // Handle the click
                insertCheckListDialog(equipment.idFieldReportEquipment)
            }
        })
        //getEquipmentsByID(reportId!!)
        val btnOpenDialogAdd =binding.equipmentListAdd //textview

        btnOpenDialogAdd.setOnClickListener {
            openCustomDialog()
        }

    }
//
    private fun getEquipmentsByID(id : String){
        Log.d("CheckID","$id")
        try {
            equipmentFieldReportViewModel.getFieldReportEquipmentCustomDisplay(requireContext(), id)
                .observe(
                    viewLifecycleOwner, Observer {
                Log.d("EquipmentList","$it")
                equipmentsList= it as ArrayList<CustomDisplayDatFieldReportEquipments>
                if (equipmentsList.isEmpty()) {
                    // The list is empty
                    Log.d("EquipmentList", "No data returned")
                } else {
                    // The list is not empty
                    adapterEquipmentList.setData(equipmentsList)
                }

                    }
                )
        }catch (e:Exception){
            Log.d("equipmentList","$e")
        }
    }

    private fun openCustomDialog() {
        // Create a dialog
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        // Set the custom layout for the dialog
        //dialog.setContentView(R.layout.dialog_equipment_list_workorders_recyclerview_row)
        val binding = DialogEquipmentListWorkordersBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        // Get the SearchView and RecyclerView from the layout
        val searchView = binding.dialogEquipmentListWorkordersSearchview
        val recyclerView = binding.dialogEquipmentListWorkordersRv
        adapterDialogEquipmentSelection=AdapterRecyclerViewDialogEquipmentList(dialogEquipmentList)
        // Set the adapter for the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapterDialogEquipmentSelection // replace with your adapter
        adapterDialogEquipmentSelection.setOnClickListener(object :AdapterRecyclerViewDialogEquipmentList.OnClickListener{
            override fun onClick(position: Int, model: Equipments) {
                Log.d("test","test123")
                insertFieldReportEquipments(model.EquipmentID!!)
            }

        })
        if (customerId!=null) {
            equipmentViewModel.getAllEquipmentDataByCustomerID(requireContext(), customerId!!)
                .observe(
                    viewLifecycleOwner, Observer {
                        Log.d("dialogEquipmentList", "$it")
                        dialogEquipmentList = it as ArrayList<Equipments>
                        adapterDialogEquipmentSelection.setData(dialogEquipmentList)
                    }
                )
        }
        else{
            Log.d("DebugEquipmentList","$customerId")
        }

        // Set a query listener on the SearchView to filter the data when the user types
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Implement if needed
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter the data based on newText
                // yourRecyclerViewAdapter.filter.filter(newText)
                return false
            }
        })



        // Show the dialog
        dialog.show()
    }
    private fun insertFieldReportEquipments(equipmentId :String){
        val tempEquipments=FieldReportEquipment(UUID.randomUUID().toString(),null,false,null,null,null,reportId,equipmentId,null)
        Toast.makeText(requireContext(),"$tempEquipments",Toast.LENGTH_SHORT).show()
        GlobalScope.launch(Dispatchers.IO) { equipmentFieldReportViewModel.insertFieldReportEquipment(requireContext(),tempEquipments) }

    }
    private fun insertCheckListDialog(maintenanceEquipmentCheckListID :String?){
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Title") // Set the title of the dialog if needed

// Inflate the custom layout and set it as the content of the dialog
        val binding = DialogChecklistWorkorderPerEquipmentBinding.inflate(layoutInflater)
        dialogBuilder.setView(binding.root)

        // Get the SearchView and RecyclerView from the layout

        val recyclerView = binding.dialogChecklistWorkorderPerEquipmentRecyclerView
        adapterEquipmentChecklist=AdapterRecyclerViewChecklist(checkformList)

        // Set the adapter for the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapterEquipmentChecklist
        recyclerView.setHasFixedSize(true)
        adapterDropDownChecklist = DropDownAdapter(requireContext(), R.layout.dropdown_adapter_tools_work_orders, maintenancesList)
        binding.dialogChecklistWorkorderPerEquipmentSelectCheckFormAutoComplete.setAdapter(adapterDropDownChecklist)
        binding.dialogChecklistWorkorderPerEquipmentSelectCheckFormAutoComplete.setOnItemClickListener { parent, _, position, _ ->
            val selectedTool = parent.getItemAtPosition(position) as Maintenances
            // `selectedTool` is the selected item
            maintenanceCheckListItems.clear()
            Log.d("toolsListDrop","$selectedTool")
            populateChecklistByCheckFormID(selectedTool.MaintenanceID,maintenanceEquipmentCheckListID!!)

        }


//        maintenancesVM.getAllMaintenances(requireContext()).observe(
//            viewLifecycleOwner,
//            Observer{
//                maintenancesList =it as ArrayList<Maintenances>
//
//                Log.d("testMaintenanceMap", "$maintenancesList")
//
//                //adapterDropDownChecklist.clear()
//                adapterDropDownChecklist.notifyDataSetChanged()
//
//            })

// Set the positive button
        dialogBuilder.setPositiveButton("Positive") { dialog, which ->
        val list = adapterEquipmentChecklist.getData()
            Log.d("checkChanged","$list")
            if (list.all { it.Result != null }) {
                for (item in list){
                    item.FieldReportCheckFormID=UUID.randomUUID().toString()

                    insertFieldCheckInDB(item)
                    updateFieldReportStatus(1 , maintenanceEquipmentCheckListID)
                }
            } else {
                // Handle the case where some Result values are null
                // For example, you can show a message to the user
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

// Set the negative (clear) button
        dialogBuilder.setNegativeButton("Close") { dialog, which ->
            // Clear the dialog or do something else
            dialog.dismiss()
        }

// Create and show the dialog
        val dialog = dialogBuilder.create()
        dialog.show()

        val window = dialog.window
        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        val width = size.x
        val height = size.y
        window?.setLayout((width * 0.9).toInt(), (height * 0.9).toInt())
    }

    private fun updateFieldReportStatus(value: Int, maintenanceEquipmentCheckListID: String?) {
        Log.d("UpdateSuccesful","$maintenanceEquipmentCheckListID")
        GlobalScope.launch(Dispatchers.IO) {
            equipmentFieldReportViewModel.updateStatusFieldReportEquipment(requireContext(),value,maintenanceEquipmentCheckListID!!)
        }

    }

    private fun insertFieldCheckInDB(item: FieldReportCheckForm) {
        GlobalScope.launch(Dispatchers.IO) {
            fieldReportCheckListVM.insertFieldReportCheckList(requireContext(),item)
        }

    }

    private fun populateChecklistByCheckFormID(selectedId: String,maintenanceFieldEquipmentID :String) {
        Log.d("fieldchecklist","here")
        checkFormVM.getCheckFormFields(requireContext(),selectedId).removeObservers(viewLifecycleOwner)
        checkFormVM.getCheckFormFields(requireContext(),selectedId).observe(
            viewLifecycleOwner,Observer{items->

                for (item in items){

                    val tempListCheckList =FieldReportCheckForm("",null,
                        maintenanceFieldEquipmentID,item.Description,item.ValueExpected,
                        null,null,null,null,null)
                    maintenanceCheckListItems.add(tempListCheckList)
                }
                adapterEquipmentChecklist.setData(maintenanceCheckListItems)

                Log.d("fieldchecklist","$items")



            }
        )


    }




}