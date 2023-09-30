package com.gkprojects.cmmsandroidapp.Fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import androidx.lifecycle.ViewModelProvider

import com.gkprojects.cmmsandroidapp.DataClasses.Customer


import com.gkprojects.cmmsandroidapp.Models.CustomerVM

import com.gkprojects.cmmsandroidapp.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EditCustomerFragment : Fragment() {

    private lateinit var customerViewModel:CustomerVM




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View?
    {



        return inflater.inflate(R.layout.fragment_edit_customer, container, false)
    }
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hospitalID: Int?
        val name  =view.findViewById<EditText>(R.id.et_customerName)
        val address =view.findViewById<EditText>(R.id.et_address)
        val phone1 =view.findViewById<EditText>(R.id.et_phone1)
        val city =view.findViewById<EditText>(R.id.et_phone2)
        val vat =view.findViewById<EditText>(R.id.et_vatNumber)
        val email =view.findViewById<EditText>(R.id.et_email)
        val comments =view.findViewById<EditText>(R.id.et_notes)
        customerViewModel= ViewModelProvider(this)[CustomerVM::class.java]

        val btnsave :Button= view.findViewById(R.id.btn_save)
        val btnclear :Button =view.findViewById(R.id.btn_clear)

        val args =this.arguments
        val id= args?.getInt("id")
        name.setText(args?.getString("name"))
        phone1.setText(args?.getString("phone"))
        city.setText(args?.getString("city"))
        address.setText(args?.getString("address"))
        vat.setText(args?.getString("vat"))
        email.setText(args?.getString("email"))
        comments.setText(args?.getString("zipcode"))
        Log.d("editFragment",id.toString())

        hospitalID= id
        Log.d("editFragment3",hospitalID.toString())



        btnsave.setOnClickListener {
            if(hospitalID==null) {
                val customer = Customer(
                    null,//hospitalID
                    null,
                    name.text.toString(),
                    phone1.text.toString(),
                    email.text.toString(),
                    address.text.toString(),
                    comments.text.toString(),
                    city.text.toString(),
                    vat.text.toString(),
                    null,
                    null,
                    null,
                    null,
                    null )
                GlobalScope.launch(Dispatchers.IO) {
                    context?.let { it1 -> customerViewModel.insert(it1, customer) }

                }


                 name.text.clear()
                 address.text.clear()// = view.findViewById<EditText>(R.id.et_address).text.clear()
                 phone1.text.clear()// = view.findViewById<EditText>(R.id.et_phone1).text.clear()
                 city.text.clear()// = view.findViewById<EditText>(R.id.et_phone2).text.clear()
                 vat.text.clear()// = view.findViewById<EditText>(R.id.et_vatNumber).text.clear()
                 email.text.clear()// = view.findViewById<EditText>(R.id.et_email).text.clear()
                 comments.text.clear()// = view.findViewById<EditText>(R.id.et_notes).text.clear()
            }else{
                Log.d("here","i am here")
                val customer = Customer(
                    hospitalID,
                    null,
                    name.text.toString(),
                    phone1.text.toString(),
                    email.text.toString(),
                    address.text.toString(),
                    comments.text.toString(),
                    city.text.toString(),
                    vat.text.toString(),
                    null,
                    null,
                    null,
                    null,
                    null )

                Log.d("updateFun",customer.toString())
                GlobalScope.launch(Dispatchers.IO) {
                    context?.let { it1 -> customerViewModel.updateCustomer(it1,customer) }
                }
                //Toast.makeText(context,"i am here$id",Toast.LENGTH_SHORT).show()

            }



            }




        btnclear.setOnClickListener {
             name.text.clear()
             address.text.clear()
             phone1.text.clear()
             city.text.clear()
             vat.text.clear()
             email.text.clear()
             comments.text.clear()
        }

        }



    }





