package com.gkprojects.cmmsandroidapp.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.gkprojects.cmmsandroidapp.Adapter.RvAdapterFindCustomers
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentCustomerSelect

import com.gkprojects.cmmsandroidapp.Models.EquipmentVM
import com.gkprojects.cmmsandroidapp.R
import java.time.Duration
import java.util.*


class EquipmentInsertFragment : Fragment() {

    private lateinit var equipmentViewModel:EquipmentVM
    var dialog: Dialog? = null
    private var rvAdapter: RvAdapterFindCustomers? = null
    lateinit var filterText : SearchView
    var hospId : Int?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_equipment_insert, container, false)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        equipmentViewModel= ViewModelProvider(this)[EquipmentVM::class.java]
        // in the below Line i am trying to fetch customer Data id and Name so i can use pass it as a FgnKey in the table
        var customerSearch =ArrayList<EquipmentCustomerSelect>()

        context?.let { equipmentViewModel.getCustomersEquipment(it).observe(viewLifecycleOwner,
            Observer{

                customerSearch= it as ArrayList<EquipmentCustomerSelect>

        }) }


        var stringCustomArray=ArrayList<String>()
        var idCustomArray = ArrayList<Int>()
        for (i in customerSearch.indices){
            stringCustomArray.add(customerSearch[i].name)
            idCustomArray.add((customerSearch[i].hospitalID))
        }


        var equipmentID :Int? = null
        val serialNumber=view.findViewById<EditText>(R.id.et_equipment_sn)
        val model=view.findViewById<EditText>(R.id.et_equipment_model)
        val manufacturer=view.findViewById<EditText>(R.id.et_equipment_Manufacturer)
        val warranty=view.findViewById<EditText>(R.id.et_equipment_warranty)
        val category=view.findViewById<EditText>(R.id.et_equipment_category)
        val installation=view.findViewById<EditText>(R.id.et_equipment_installation)
        val status=view.findViewById<EditText>(R.id.et_equipment_status)
        val version_equipment=view.findViewById<EditText>(R.id.et_equipment_version)
        val customerNameTV=view.findViewById<TextView>(R.id.tv_select_customer)



        val args =this.arguments
        val id= args?.getInt("EquipmentId")
        val fgId=args?.getInt("HospitalId")
        serialNumber.setText(args?.getString("sn"))
        model.setText(args?.getString("model"))
        manufacturer.setText(args?.getString("manufacturer"))
        warranty.setText(args?.getString("ExpirationDate"))
        category.setText(args?.getString("category"))
        installation.setText(args?.getString("installationDate"))
        status.setText(args?.getString("status"))
        version_equipment.setText(args?.getString("lastMaintenance"))

        //________________________________________________
        equipmentID=id





            customerNameTV.setOnClickListener {

                val builder =AlertDialog.Builder(context)

                builder.setView(R.layout.dialog_searchable_spinner)

                dialog?.getWindow()?.setLayout(650,800);

                // set transparent background
                dialog?.getWindow()?.setBackgroundDrawableResource(com.google.android.material.R.drawable.m3_tabs_transparent_background)


                dialog=builder.create()
                // show dialog
                dialog?.show();


                val recycleView: RecyclerView = dialog!!.findViewById(R.id.rv_searchable_TextView)
                 filterText= dialog!!.findViewById(R.id.searchView_rv_customers)

                rvAdapter = context?.let { it1 -> RvAdapterFindCustomers(it1, customerSearch) }
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

                rvAdapter!!.setOnClickListener(object :RvAdapterFindCustomers.OnClickListener{
                    override fun onClick(position: Int, model: EquipmentCustomerSelect) {
                        var strtemp: String = model.name
                        hospId = model.hospitalID

                        customerNameTV.text = strtemp
                        dialog!!.dismiss();

                    }

                })


            }













        val btnsubmit : Button = view.findViewById(R.id.btn_equipment_submit)
        val btnclear : Button =view.findViewById(R.id.btn_equipment_clear)
        btnsubmit.setOnClickListener {
            if(hospId!=null) {
                var equipment = Equipment(
                    null,
                    hospId,
                    category.text.toString(),
                    manufacturer.text.toString(),
                    model.text.toString(),
                    serialNumber.text.toString(),
                    installation.text.toString(),
                    warranty.text.toString(),
                    version_equipment.text.toString(),
                    "null",
                    status.text.toString()
                )
                if (equipmentID == null) {
                    this.context?.let { it1 -> equipmentViewModel.insert(it1, equipment) }

                    serialNumber.text.clear()
                    model.text.clear()
                    manufacturer.text.clear()
                    warranty.text.clear()
                    category.text.clear()
                    installation.text.clear()
                    status.text.clear()
                    version_equipment.text.clear()
                    customerNameTV.text="Select Customer"
                } else {

                    equipment = Equipment(
                        equipmentID,
                        fgId,
                        category.text.toString(),
                        manufacturer.text.toString(),
                        model.text.toString(),
                        serialNumber.text.toString(),
                        installation.text.toString(),
                        warranty.text.toString(),
                        version_equipment.text.toString(),
                        "null",
                        status.text.toString()
                    )
                    this.context?.let { it1 -> equipmentViewModel.updateEquipment(it1, equipment) }
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(context,"Select Customer",Toast.LENGTH_SHORT).show()
            }


        }
        btnclear.setOnClickListener {
            if(equipmentID==null){
            serialNumber.text.clear()
            model.text.clear()
            manufacturer.text.clear()
            warranty.text.clear()
            category.text.clear()
            installation.text.clear()
            status.text.clear()
            version_equipment.text.clear()
            }else{
                Toast.makeText(context,"You can't Delete now",Toast.LENGTH_SHORT).show()
            }
        }


    }



    private fun filterList(query: String,searchCustomer : ArrayList<EquipmentCustomerSelect>) {
        val filteredList= java.util.ArrayList<EquipmentCustomerSelect>()
        for (i in searchCustomer){
            if (i.name.lowercase(Locale.ROOT).contains(query))
                filteredList.add(i)
            Log.d("datafilterDialog", filteredList.toString())
        }
        if (filteredList.isEmpty() ){
            Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

        }else{

            rvAdapter?.filterList(filteredList)
        }

    }




}






