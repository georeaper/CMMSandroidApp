package com.gkprojects.cmmsandroidapp.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentCustomerSelect
import com.gkprojects.cmmsandroidapp.Models.EquipmentVM
import com.gkprojects.cmmsandroidapp.R


class EquipmentInsertFragment : Fragment() {
    //private lateinit var AppDb : CMMSDatabase
    private lateinit var equipmentViewModel:EquipmentVM

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        equipmentViewModel= ViewModelProvider(this)[EquipmentVM::class.java]
        // in the below Line i am trying to fetch customer Data id and Name so i can use pass it as a FgnKey in the table
        var customerSearch =ArrayList<EquipmentCustomerSelect>()
        context?.let { equipmentViewModel.getCustomersEquipment(it).observe(viewLifecycleOwner,
            Observer{
                Log.d("CustomerArray",it.toString())
                customerSearch= it as ArrayList<EquipmentCustomerSelect>

        }) }

        var stringCustomArray=ArrayList<String>()
        var idCustomArray = ArrayList<Int>()
        for (i in customerSearch.indices){
            stringCustomArray.add(customerSearch[i].name)
            idCustomArray.add((customerSearch[i].hospitalID))
        }
        Log.d("CustomerArray",customerSearch.toString())

        var equipmentID :Int? = null
        val serialNumber=view.findViewById<EditText>(R.id.et_equipment_sn)
        val model=view.findViewById<EditText>(R.id.et_equipment_model)
        val manufacturer=view.findViewById<EditText>(R.id.et_equipment_Manufacturer)
        val warranty=view.findViewById<EditText>(R.id.et_equipment_warranty)
        val category=view.findViewById<EditText>(R.id.et_equipment_category)
        val installation=view.findViewById<EditText>(R.id.et_equipment_installation)
        val status=view.findViewById<EditText>(R.id.et_equipment_status)
        val version_equipment=view.findViewById<EditText>(R.id.et_equipment_version)
        val customerNameSp=view.findViewById<Spinner>(R.id.spinner_select_customer)



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


        Log.d("editFragment",id.toString())





        val btnsubmit : Button = view.findViewById(R.id.btn_equipment_submit)
        val btnclear : Button =view.findViewById(R.id.btn_equipment_clear)
        btnsubmit.setOnClickListener {
            var equipment=Equipment(null,33,category.text.toString(),manufacturer.text.toString(),model.text.toString(),serialNumber.text.toString(),installation.text.toString(),warranty.text.toString(),version_equipment.text.toString(),"null",status.text.toString())
            if(equipmentID==null) {
                this.context?.let { it1 -> equipmentViewModel.insert(it1, equipment) }

                serialNumber.text.clear()
                model.text.clear()
                manufacturer.text.clear()
                warranty.text.clear()
                category.text.clear()
                installation.text.clear()
                status.text.clear()
                version_equipment.text.clear()
            }else{

                equipment=Equipment(equipmentID,fgId,category.text.toString(),manufacturer.text.toString(),model.text.toString(),serialNumber.text.toString(),installation.text.toString(),warranty.text.toString(),version_equipment.text.toString(),"null",status.text.toString())
                this.context?.let { it1 -> equipmentViewModel.updateEquipment(it1, equipment) }
                Toast.makeText(context,"Updated",Toast.LENGTH_SHORT).show()
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


}