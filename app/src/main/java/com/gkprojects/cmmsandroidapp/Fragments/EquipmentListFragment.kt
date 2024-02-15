package com.gkprojects.cmmsandroidapp.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.EquipmentDropDownAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentListInCases
import com.gkprojects.cmmsandroidapp.Models.EquipmentVM
import com.gkprojects.cmmsandroidapp.Models.SharedViewModel
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.FragmentCustomerInfoBinding
import com.gkprojects.cmmsandroidapp.databinding.FragmentEquipmentListBinding


class EquipmentListFragment : Fragment() {
    private lateinit var binding : FragmentEquipmentListBinding
    private lateinit var adapterDropdown : EquipmentDropDownAdapter
    private var reportId : Int?=null
    private var isObserverSetUp = false
    private lateinit var equipmentViewModel : EquipmentVM
    private var equipmentList = ArrayList<EquipmentListInCases>()
    private lateinit var recyclerViewEquipmentList: RecyclerView
    private var selectedEquipment: EquipmentListInCases? = null

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
        
        equipmentViewModel= ViewModelProvider(this)[EquipmentVM::class.java]


        val sharedViewModel: SharedViewModel by activityViewModels()

        if (!isObserverSetUp) {
            sharedViewModel.reportId.observe(viewLifecycleOwner, Observer { id ->
                Log.d("sharedViewModelReportId", "$id")
                reportId = id
                getEquipments(reportId as Int)
            })
            isObserverSetUp = true
        }

        adapterDropdown = EquipmentDropDownAdapter(requireContext(), equipmentList)

        recyclerViewEquipmentList=binding.equipmentListRecyclerView
        val imageBtnEquipmentList=binding.equipmentListImgBtn
    }
    private fun getEquipments(id : Int){
        equipmentViewModel.getEquipmentByCustomerId(requireContext(),id).observe(viewLifecycleOwner,Observer{
            for (i in it.indices){
                equipmentList.add(it[i])
            }
            //equipmentList=it as ArrayList<EquipmentListInCases>
            Log.d("equipmentList","${equipmentList}")
            val dropdownEquipmentList =binding.equipmentListDropDown
            dropdownEquipmentList.setAdapter(adapterDropdown)
            dropdownEquipmentList.setOnItemClickListener { adapterView, view, position, id ->
                selectedEquipment = adapterDropdown.getItem(position) as EquipmentListInCases

                Toast.makeText(requireContext(),selectedEquipment.toString(), Toast.LENGTH_SHORT).show()
                // Now selectedEquipment holds the selected item
            }
            //adapterDropdown = EquipmentDropDownAdapter(requireContext(), equipmentList)
            adapterDropdown.notifyDataSetChanged()
        })
    }


}