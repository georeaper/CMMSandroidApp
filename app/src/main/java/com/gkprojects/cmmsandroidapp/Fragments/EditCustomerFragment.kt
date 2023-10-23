package com.gkprojects.cmmsandroidapp.Fragments


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.CheckBox
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




    override  fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View?
    {



        return inflater.inflate(R.layout.fragment_edit_customer, container, false)
    }
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val hospitalID: Int?
        val name  =view.findViewById<EditText>(R.id.et_customerName)
        val address =view.findViewById<EditText>(R.id.et_address)
        val phone1 =view.findViewById<EditText>(R.id.et_phone1)
        val zipcode =view.findViewById<EditText>(R.id.et_zipcode)
        val description =view.findViewById<EditText>(R.id.et_description)
        val email =view.findViewById<EditText>(R.id.et_email)
        val notes =view.findViewById<EditText>(R.id.et_notes)
        val status =view.findViewById<CheckBox>(R.id.checkBoxStatusCustomer)
        val city =view.findViewById<EditText>(R.id.et_city)
        //status =view?.findViewById<CheckBox>(R.id.checkBoxStatusCustomer)
        var statusStr =""

        customerViewModel= ViewModelProvider(this)[CustomerVM::class.java]


        val btnsave :Button= view.findViewById(R.id.btn_save)
        val btnclear :Button =view.findViewById(R.id.btn_clear)

        val args =this.arguments
        val id= args?.getInt("id")
        name.setText(args?.getString("name"))
        phone1.setText(args?.getString("phone"))
        description.setText(args?.getString("description"))
        address.setText(args?.getString("address"))
        zipcode.setText(args?.getString("zipcode"))
        email.setText(args?.getString("email"))
        notes.setText(args?.getString("notes"))
        city.setText(args?.getString("city"))
        statusStr=args?.getString("status").toString()

         if (statusStr=="Active"){
            status.isChecked=true

        }


        hospitalID= id
        Log.d("editFragment3",hospitalID.toString())

        btnsave.setOnClickListener {
            statusStr = if (status.isChecked){
                "Active"
            }else{
                "NotActive"
            }

            if(hospitalID==null) {
                val customer = Customer(
                    null,//hospitalID
                    null,
                    name.text.toString(),
                    phone1.text.toString(),
                    email.text.toString(),
                    address.text.toString(),
                    zipcode.text.toString(),
                    city.text.toString(),
                    notes.text.toString(),
                    description.text.toString(),
                    statusStr,
                    null,
                    null,
                    null )
                GlobalScope.launch(Dispatchers.IO) {
                    context?.let { it1 -> customerViewModel.insert(it1, customer) }

                }


//                 name.text.clear()
//                 address.text.clear()// = view.findViewById<EditText>(R.id.et_address).text.clear()
//                 phone1.text.clear()// = view.findViewById<EditText>(R.id.et_phone1).text.clear()
//                 city.text.clear()// = view.findViewById<EditText>(R.id.et_phone2).text.clear()
//                 vat.text.clear()// = view.findViewById<EditText>(R.id.et_vatNumber).text.clear()
//                 email.text.clear()// = view.findViewById<EditText>(R.id.et_email).text.clear()
//                 comments.text.clear()// = view.findViewById<EditText>(R.id.et_notes).text.clear()
            }else{
                Log.d("here","i am here")
                val customer = Customer(
                    hospitalID,
                    null,
                    name.text.toString(),
                    phone1.text.toString(),
                    email.text.toString(),
                    address.text.toString(),
                    zipcode.text.toString(),
                    city.text.toString(),
                    notes.text.toString(),
                    description.text.toString(),
                    statusStr,
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
//             name.text.clear()
//             address.text.clear()
//             phone1.text.clear()
//             city.text.clear()
//             vat.text.clear()
//             email.text.clear()
//             comments.text.clear()
        }

        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }
    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.menu_dynamic_button)
        var status =view?.findViewById<CheckBox>(R.id.checkBoxStatusCustomer)

        if (status != null) {
            item.isVisible=status.isChecked
        }

    }





    }





