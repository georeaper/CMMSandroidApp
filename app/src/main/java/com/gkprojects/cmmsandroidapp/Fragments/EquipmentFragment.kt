package com.gkprojects.cmmsandroidapp.Fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.CustomerAdapter
import com.gkprojects.cmmsandroidapp.Adapter.EquipmentAdapter
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.Models.EquipmentVM

import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

private lateinit var equipmentRecyclerView: RecyclerView
private lateinit var equipmentAdapter: EquipmentAdapter
private lateinit var equipmentViewModel: EquipmentVM
private var templist = ArrayList<Equipment>()

class EquipmentFragment : Fragment() {



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
                templist.clear() // clear the templist,because it keeps populate everytime we open and close Customer Drawer
                for(i in it.indices)(
                        templist.add(it[i])
                        )
                Log.d("templistEquip", templist.size.toString())
            })
        }
        val searchView = view.findViewById<SearchView>(R.id.searchView_equipment)
       searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
               if (p0 != null) {
                    filterList(p0.lowercase(Locale.ROOT))
                }
                return true
            }
        })
        equipmentAdapter.setOnClickListener(object : EquipmentAdapter.OnClickListener{
            override fun onClick(position: Int, model: Equipment) {

                //Toast.makeText(context,model.toString(),Toast.LENGTH_LONG).show()
                passDataEquipment(model)


            }
        })

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



            try {
                GlobalScope.launch(Dispatchers.IO) {

                    context?.let {
                        equipmentViewModel.deleteEquipment(
                            it,
                            templist[viewHolder.absoluteAdapterPosition]
                        )
                    }

                }
            }catch (e:java.lang.Exception){
                Log.d("deleteEquipment",e.toString())
            }

                context?.let {
                    equipmentViewModel.getAllUserData(it).observe(viewLifecycleOwner, Observer {
                        equipmentAdapter.setData(it as java.util.ArrayList<Equipment>)

                    })
                }
            }


        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(equipmentRecyclerView)

    }
    private fun filterList(query:String){
        if (query!=null){
            val filteredList= java.util.ArrayList<Equipment>()
            for (i in templist){
                if(i.model.lowercase(Locale.ROOT).contains(query))
                //if (i..lowercase(Locale.ROOT).contains(query))
                    filteredList.add(i)
                Log.d("dataEquipment", filteredList.toString())
            }
            if (filteredList.isEmpty() ){
                Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

            }else{
                equipmentAdapter.setData(filteredList)
            }
        }


    }

    private fun passDataEquipment(data : Equipment){
        //var temp: java.io.Serializable = data as java.io.Serializable
        val bundle = Bundle()
        data.hospitalID?.let { bundle.putInt("HospitalId", it.toInt()) }
        data.equipmentID?.let{bundle.putInt("EquipmentId",it.toInt())}
        //bundle.putString("id", data.hospitalID.toString())
        bundle.putString("model", data.model)
        bundle.putString("category", data.category)
        bundle.putString("manufacturer", data.manufacturer)
        bundle.putString("sn", data.serialNumber)
        bundle.putString("status", data.status)
        bundle.putString("installationDate", data.installationDate)
        bundle.putString("ExpirationDate", data.warrantyExpirationDate)
        bundle.putString("nextMaintenance", data.nextMaintenanceDueDate)
        bundle.putString("lastMaintenance", data.lastMaintenanceDate)//version



        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment =EquipmentInsertFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()

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