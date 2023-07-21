package com.gkprojects.cmmsandroidapp.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider

import com.gkprojects.cmmsandroidapp.CMMSDatabase

import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.Models.CustomerVM
import com.gkprojects.cmmsandroidapp.Models.EquipmentVM
import com.gkprojects.cmmsandroidapp.R

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EditCustomerFragment : Fragment() {

    private lateinit var customerViewModel:CustomerVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View?
    {
        val v=inflater.inflate(R.layout.fragment_edit_customer, container, false)


        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name  =view.findViewById<EditText>(R.id.et_customerName).text
        val address =view.findViewById<EditText>(R.id.et_address).text
        val phone1 =view.findViewById<EditText>(R.id.et_phone1).text
        val city =view.findViewById<EditText>(R.id.et_phone2).text
        val vat =view.findViewById<EditText>(R.id.et_vatNumber).text
        val email =view.findViewById<EditText>(R.id.et_email).text
        val comments =view.findViewById<EditText>(R.id.et_notes).text
        customerViewModel= ViewModelProvider(this)[CustomerVM::class.java]
        //val context= getActivity()?.getApplicationContext()
        val btnsave :Button= view.findViewById(R.id.btn_save)
        val btnclear :Button =view.findViewById(R.id.btn_clear)
        //AppDb= context?.let { CMMSDatabase.getCMMSDatabase(it) }!!


        btnsave.setOnClickListener {
            val customer = Hospital(null,name.toString(),address.toString(),phone1.toString(),city.toString(),vat.toString(),email.toString(), comments.toString())
            GlobalScope.launch(Dispatchers.IO) {
                context?.let { it1 -> customerViewModel.insert(it1,customer) }
//                this.context?.let { it1 -> customerViewModel.insert(it1,customer) }
            }


            val name  =view.findViewById<EditText>(R.id.et_customerName).text.clear()
            val address =view.findViewById<EditText>(R.id.et_address).text.clear()
            val phone1 =view.findViewById<EditText>(R.id.et_phone1).text.clear()
            val city =view.findViewById<EditText>(R.id.et_phone2).text.clear()
            val vat =view.findViewById<EditText>(R.id.et_vatNumber).text.clear()
            val email =view.findViewById<EditText>(R.id.et_email).text.clear()
            val comments =view.findViewById<EditText>(R.id.et_notes).text.clear()

            }

        btnclear.setOnClickListener {
            val name  =view.findViewById<EditText>(R.id.et_customerName).text.clear()
            val address =view.findViewById<EditText>(R.id.et_address).text.clear()
            val phone1 =view.findViewById<EditText>(R.id.et_phone1).text.clear()
            val city =view.findViewById<EditText>(R.id.et_phone2).text.clear()
            val vat =view.findViewById<EditText>(R.id.et_vatNumber).text.clear()
            val email =view.findViewById<EditText>(R.id.et_email).text.clear()
            val comments =view.findViewById<EditText>(R.id.et_notes).text.clear()
        }

        }



    }



