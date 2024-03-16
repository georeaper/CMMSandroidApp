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
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportTools

import com.gkprojects.cmmsandroidapp.DataClasses.Tools

import com.gkprojects.cmmsandroidapp.Fragments.SpecialTools.SpecialToolsVM
import com.gkprojects.cmmsandroidapp.Fragments.TechnicalCases.CasesFragment
import com.gkprojects.cmmsandroidapp.Models.SharedViewModel
import com.gkprojects.cmmsandroidapp.R

import com.gkprojects.cmmsandroidapp.databinding.FragmentToolsListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID


class ToolsListFragment : Fragment() {

    private lateinit var binding :FragmentToolsListBinding
    private var isObserverSetUp = false
    private var reportId : String?=null

    private lateinit var toolsVM : SpecialToolsVM
    private lateinit var fieldReportToolsVM : FieldReportToolsVM

    private lateinit var fieldToolsAdapter : AdapterFieldReportRecyclerViewTools

    private var toolsList =ArrayList<Tools>()
    private var tool = Tools(UUID.randomUUID().toString(),null,null,null,null,null,null,null,null,null,null)

    private var customToolsList =ArrayList<FieldReportToolsCustomData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentToolsListBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedViewModel: SharedViewModel by activityViewModels()



        toolsVM = ViewModelProvider(this)[SpecialToolsVM::class.java]
        fieldReportToolsVM=ViewModelProvider(this)[FieldReportToolsVM::class.java]

        toolsVM.getTools(requireContext()).observe(
            viewLifecycleOwner,
            Observer {
                Log.d("toolsListVM","$it")
                toolsList= it as ArrayList<Tools>
                Log.d("toolsListVM2","$toolsList")
                populateDropDown()
            })

        fieldToolsAdapter = AdapterFieldReportRecyclerViewTools(customToolsList)
        binding.toolsListRecyclerView.apply {
            adapter=fieldToolsAdapter
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(this.context)
        }
        if (!isObserverSetUp) {
            sharedViewModel.reportId.observe(viewLifecycleOwner,
                Observer {
                    reportId=it
                    populateRecyclerviewTools()
                })

            isObserverSetUp = true
        }


        binding.toolsListButton.setOnClickListener {


            insertIntoDatabase(tool)
            populateRecyclerviewTools()
            binding.toolsListAutoComplete.setText("",false)
            //autoCompleteTextView.setText("", false)
        }




    }

    private fun insertIntoDatabase(tools: Tools) {

        val tempFieldReportTools=FieldReportTools(UUID.randomUUID().toString(),null,reportId,tools.ToolsID,null,null,null)

        GlobalScope.launch(Dispatchers.IO) {
            fieldReportToolsVM.insert(requireContext(), tempFieldReportTools)

        }


    }
    private fun populateDropDown(){
        val toolsAdapter = ArrayAdapterDropDownTools(requireContext(), R.layout.dropdown_adapter_tools_work_orders, toolsList)
        val autoCompleteTextView = binding.toolsListAutoComplete
        autoCompleteTextView.setAdapter(toolsAdapter)
        //autoCompleteTextView.isFocusableInTouchMode = false

        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedTool = parent.getItemAtPosition(position) as Tools
            // `selectedTool` is the selected item
            Log.d("toolsListDrop","$selectedTool")
            tool=selectedTool
            Log.d("toolsListDrop2","$tool")
        }
    }

    private fun populateRecyclerviewTools(){
        Log.d("reportIDinTools","$reportId")
        fieldReportToolsVM.getTollsByReportID(requireContext(),reportId!!).observe(
            viewLifecycleOwner,
            Observer {
                if(it.isEmpty()){

                }else{
                    customToolsList =it as ArrayList<FieldReportToolsCustomData>
                    Log.d("customToolsList","$customToolsList")
                    fieldToolsAdapter.setData(customToolsList)
                }

            }
        )


    }


}