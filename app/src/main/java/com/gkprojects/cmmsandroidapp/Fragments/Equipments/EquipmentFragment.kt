package com.gkprojects.cmmsandroidapp.Fragments.Equipments


import android.annotation.SuppressLint
import android.content.Context

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.gkprojects.cmmsandroidapp.Adapter.EquipmentAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentSelectCustomerName

import com.gkprojects.cmmsandroidapp.DataClasses.Equipments

import com.gkprojects.cmmsandroidapp.Models.EquipmentVM

import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.FragmentEquipmentBinding
import com.gkprojects.cmmsandroidapp.databinding.FragmentWorkOrdersBinding
import com.gkprojects.cmmsandroidapp.filterPopWindow
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*

import java.util.*
import kotlin.collections.ArrayList


class EquipmentFragment : Fragment() {
    private lateinit var equipmentRecyclerView: RecyclerView
    @SuppressLint("StaticFieldLeak")
    private lateinit var equipmentAdapter: EquipmentAdapter
    private lateinit var equipmentViewModel: EquipmentVM
    private var equipmentList = ArrayList<EquipmentSelectCustomerName>()
    private var dataItems = ArrayList<EquipmentSelectCustomerName>()
    private lateinit var binding: FragmentEquipmentBinding
    private lateinit var filterWindow : filterPopWindow
    //private var eq = ArrayList<Equipments>()
    private var eq = Equipments("",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null)

    override fun onResume() {
        super.onResume()
        var activity =requireActivity()

        var drawerLayout = activity.findViewById<DrawerLayout>(R.id.DrawLayout)
        val navView: NavigationView = activity.findViewById(R.id.navView)
        val toolbar: MaterialToolbar = activity.findViewById(R.id.topAppBar)
        toolbar.title="Equipments"

        var toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }


    @SuppressLint("SuspiciousIndentation", "UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        equipmentRecyclerView=view.findViewById(R.id.equipment_recyclerview)
        equipmentAdapter= this.context?.let { EquipmentAdapter(it, ArrayList<EquipmentSelectCustomerName>()) }!!
        equipmentRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(this.context)
            adapter= equipmentAdapter
        }
        equipmentViewModel= ViewModelProvider(this)[EquipmentVM::class.java]


        try{
            lifecycleScope.launch {
                withContext(Dispatchers.Main){
                     equipmentViewModel.getCustomerName(requireContext()).observe(viewLifecycleOwner,
                        Observer {list->
                            equipmentList.clear()
                            equipmentList=list as ArrayList<EquipmentSelectCustomerName>
                            equipmentAdapter.setData(list)

                        })
                }
            }

            reloadData(context!!)

        }catch (e:java.lang.Exception){
            Log.d("debugE",e.toString())
        }

        val searchEditText = view.findViewById<TextInputEditText>(R.id.searchEditTextEquipment)

        searchEditText.addTextChangedListener (object :TextWatcher {
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

        equipmentAdapter.setOnClickListener(object : EquipmentAdapter.OnClickListener{
            override fun onClick(position: Int, model: EquipmentSelectCustomerName) {

                //Toast.makeText(context,model.toString(),Toast.LENGTH_LONG).show()
                passDataEquipment(model)


            }
        })
        val filterButton=binding.imageButtonFilterEquipment
        filterButton.setOnClickListener {
            filterWindow  = filterPopWindow.newInstance(
                R.layout.filter_pop_equipments
            ){filterView ->
//login that handles filtering

            }
            filterWindow.show(childFragmentManager, "FilterEquipment")


        }

    val btnFloat=view.findViewById<FloatingActionButton>(R.id.openEquipmentFragment)
        btnFloat.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, EquipmentInsertFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        val myCallback = object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT) {

            // More code here
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //var eq = ArrayList<Equipments>()
                val pos= equipmentList[viewHolder.absoluteAdapterPosition]

                Toast.makeText(context,pos.EquipmentID.toString(),Toast.LENGTH_SHORT).show()


                try{
                    lifecycleScope.launch {
                        withContext(Dispatchers.Main){
                        equipmentViewModel.getRecordById(context!!,pos.EquipmentID!!).observe(viewLifecycleOwner,
                            Observer {
                                if(it!=null) {
                                    eq = it

                                    if (eq.EquipmentID != null) {
                                        deleteRecord(context!!, eq)
                                    }
                                }
                            })

                        }
                    }

                }


                catch (e:java.lang.Exception){
                   Log.d("deleteDebug",e.toString())
                }
            }
        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(equipmentRecyclerView)

    }
    private fun filterList(query:String){
        val filteredList= java.util.ArrayList<EquipmentSelectCustomerName>()
        for (i in equipmentList){
            if((i.Model?.lowercase(Locale.ROOT)?.contains(query)==true) or (i.SerialNumber?.lowercase(Locale.ROOT)?.contains(query) == true)or(i.CustomerName?.lowercase(Locale.ROOT)?.contains(query) == true))
                filteredList.add(i)

        }
        if (filteredList.isEmpty() ){
            Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

        }else{
            equipmentAdapter.setData(filteredList)
        }


    }

    private fun passDataEquipment(data : EquipmentSelectCustomerName){
        //var temp: java.io.Serializable = data as java.io.Serializable
        val bundle = Bundle()
        //bundle.putString("")=data.EquipmentID
        data.EquipmentID?.let { bundle.putString("EquipmentId", it) }
        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment = EquipmentInsertFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()

    }
     fun deleteRecord(context: Context, equipments: Equipments){
        lifecycleScope.launch(Dispatchers.Main){
            equipmentViewModel.deleteEquipment(context,equipments)
        }
    }

    private fun reloadData(context: Context){
        lifecycleScope.launch(Dispatchers.Main){
            equipmentViewModel.getCustomerName(context).observe(viewLifecycleOwner,
                Observer {list->
                equipmentList.clear()
                equipmentList=list as ArrayList<EquipmentSelectCustomerName>

                equipmentAdapter.setData(list )


            })
        }


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentEquipmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }



}