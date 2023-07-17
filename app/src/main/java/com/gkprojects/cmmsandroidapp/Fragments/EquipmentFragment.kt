package com.gkprojects.cmmsandroidapp.Fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.EquipmentAdapter
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.Models.EquipmentVM

import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private lateinit var equipmentRecyclerView: RecyclerView
private lateinit var equipmentAdapter: EquipmentAdapter
private lateinit var equipmentViewModel: EquipmentVM

class EquipmentFragment : Fragment() {


    //@SuppressLint("SuspiciousIndentation")
    @SuppressLint("SuspiciousIndentation", "UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        equipmentRecyclerView=view.findViewById(R.id.equipment_recyclerview)
        equipmentAdapter= this.context?.let { EquipmentAdapter(it, ArrayList<Equipment>()) }!!
        equipmentRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(this.context)
            adapter= equipmentAdapter
        }
        equipmentViewModel= ViewModelProvider(this).get(EquipmentVM::class.java)
        context?.let {
            equipmentViewModel.getAllUserData(it).observe(viewLifecycleOwner, Observer {
                equipmentAdapter.setData(it as ArrayList<Equipment>)
            })
        }

    val btnFloat=view.findViewById<FloatingActionButton>(R.id.openEquipmentFragment)
        btnFloat.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, EquipmentInsertFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_equipment, container, false)
    }



}