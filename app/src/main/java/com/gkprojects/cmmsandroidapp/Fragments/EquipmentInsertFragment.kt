package com.gkprojects.cmmsandroidapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.Models.EquipmentVM
import com.gkprojects.cmmsandroidapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
        val serialNumber=view.findViewById<EditText>(R.id.et_equipment_sn).text
        val model=view.findViewById<EditText>(R.id.et_equipment_model).text
        val manufacturer=view.findViewById<EditText>(R.id.et_equipment_Manufacturer).text
        val warranty=view.findViewById<EditText>(R.id.et_equipment_warranty).text
        val category=view.findViewById<EditText>(R.id.et_equipment_category).text
        val installation=view.findViewById<EditText>(R.id.et_equipment_installation).text
        val status=view.findViewById<EditText>(R.id.et_equipment_status).text
        val version_equipment=view.findViewById<EditText>(R.id.et_equipment_version).text

        val context= getActivity()?.getApplicationContext()
        equipmentViewModel= ViewModelProvider(this)[EquipmentVM::class.java]


        val btnsubmit : Button = view.findViewById(R.id.btn_equipment_submit)
        val btnclear : Button =view.findViewById(R.id.btn_equipment_clear)
        btnsubmit.setOnClickListener {
            val equipment=Equipment(null,2,category.toString(),manufacturer.toString(),model.toString(),serialNumber.toString(),installation.toString(),warranty.toString(),version_equipment.toString(),"null",status.toString())

            this.context?.let { it1 -> equipmentViewModel.insert(it1,equipment) }

            serialNumber.clear()
            model.clear()
            manufacturer.clear()
            warranty.clear()
            category.clear()
            installation.clear()
            status.clear()
            version_equipment.clear()


        }
        btnclear.setOnClickListener {
            serialNumber.clear()
            model.clear()
            manufacturer.clear()
            warranty.clear()
            category.clear()
            installation.clear()
            status.clear()
            version_equipment.clear()
        }


    }
}