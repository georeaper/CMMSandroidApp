package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportInventory
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportTools
import com.gkprojects.cmmsandroidapp.DataClasses.Inventory
import com.gkprojects.cmmsandroidapp.DataClasses.Tools
import com.gkprojects.cmmsandroidapp.Fragments.Inventory.InventoryVM
import com.gkprojects.cmmsandroidapp.Fragments.SpecialTools.SpecialToolsVM
import com.gkprojects.cmmsandroidapp.Models.SharedViewModel
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.FragmentSparePartListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID


class SparePartListFragment : Fragment() {

    private lateinit var binding :FragmentSparePartListBinding
    private var isObserverSetUp = false
    private var reportId : String?=null

    private lateinit var inventoryVM : InventoryVM
    private lateinit var fieldReportInventoryVM : FieldReportInventoryVM

    private lateinit var fieldInventoryAdapter : AdapterFieldReportRecyclerViewInventory

    private var inventoryList =ArrayList<Inventory>()
    private var inventory = Inventory(UUID.randomUUID().toString(),null,null,null,null,null,null,null,null,null)

    private var customInventoryList =ArrayList<FieldReportInventoryCustomData>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSparePartListBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedViewModel: SharedViewModel by activityViewModels()
        inventoryVM = ViewModelProvider(this)[InventoryVM::class.java]
        fieldReportInventoryVM= ViewModelProvider(this)[FieldReportInventoryVM::class.java]
        inventoryVM.getAllInventory(requireContext()).observe(
            viewLifecycleOwner,
            Observer {
                Log.d("inventoryListVM","$it")
                inventoryList= it as ArrayList<Inventory>
                Log.d("inventoryListVM2","$inventoryList")
                populateDropDown()
            }
        )
        fieldInventoryAdapter= AdapterFieldReportRecyclerViewInventory(customInventoryList)
        binding.sparePartListRecyclerview.apply { 
            layoutManager=LinearLayoutManager(this.context)
            setHasFixedSize(true)
            adapter=fieldInventoryAdapter
        }
        
        if (!isObserverSetUp) {
            sharedViewModel.reportId.observe(viewLifecycleOwner,
                Observer { id ->
                Log.d("sharedViewModelReportId", "$id")
                reportId = id
                    populateRecyclerviewInventory()
            })
            isObserverSetUp = true
        }
        
        binding.sparePartListAddBtn.setOnClickListener {
            insertIntoDatabase(inventory)
            populateRecyclerviewInventory()
            binding.sparePartListDropdownList.setText("",false)
        }
    }

    private fun insertIntoDatabase(inventory: Inventory) {
        val tempFieldReportTools=
            FieldReportInventory(UUID.randomUUID().toString(),null,null,null,null,reportId,inventory.InventoryID)

        GlobalScope.launch(Dispatchers.IO) {
//            fieldReportToolsVM.insert(requireContext(), tempFieldReportTools)
            fieldReportInventoryVM.insert(requireContext(),tempFieldReportTools)

        }
    }

    private fun  populateDropDown(){
        val inventoryAdapter=ArrayAdapterDropDownSpareParts(requireContext(),R.layout.dropdown_adapter_spare_parts_work_orders,inventoryList)
        val autocomplete =binding.sparePartListDropdownList
        autocomplete.setAdapter(inventoryAdapter)
        autocomplete.setOnItemClickListener { parent, _, position, _ ->
            val selectedTool = parent.getItemAtPosition(position) as Inventory
            // `selectedTool` is the selected item
            Log.d("inventoryListDrop","$selectedTool")
            inventory=selectedTool
            Log.d("inventoryListDrop2","$inventory")
        }
    }

    private fun populateRecyclerviewInventory() {
        Log.d("reportIDinInventory","$reportId")
        fieldReportInventoryVM.getInventoryByFieldReportID(requireContext(),reportId!!).observe(
            viewLifecycleOwner, Observer {
                if(it.isEmpty()){

                }else{
                    customInventoryList=it as ArrayList<FieldReportInventoryCustomData>
                    Log.d("customInventoryList","$customInventoryList")
                    fieldInventoryAdapter.setData(customInventoryList)
                }
            }
        )
    }


}