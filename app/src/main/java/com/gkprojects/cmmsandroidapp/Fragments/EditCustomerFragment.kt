package com.gkprojects.cmmsandroidapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.gkprojects.cmmsandroidapp.DataClasses.Customers
import com.gkprojects.cmmsandroidapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class EditCustomerFragment : Fragment() {

    private lateinit var db : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v=inflater.inflate(R.layout.fragment_edit_customer, container, false)


        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name  =view.findViewById<EditText>(R.id.et_customerName).text
        val address =view.findViewById<EditText>(R.id.et_address).text
        val phone1 =view.findViewById<EditText>(R.id.et_phone1).text
        val phone2 =view.findViewById<EditText>(R.id.et_phone2).text
        val vat =view.findViewById<EditText>(R.id.et_vatNumber).text
        val email =view.findViewById<EditText>(R.id.et_email).text
        val comments =view.findViewById<EditText>(R.id.et_notes).text
        val btn :Button= view.findViewById(R.id.btn_save)
        db=FirebaseDatabase.getInstance().getReference("customers")

        btn.setOnClickListener {
            val customers = Customers(name.toString(),address.toString(),phone1.toString(),phone2.toString(),vat.toString(),email.toString(), comments.toString())
            //val dbInsert : Customers = Customers(name,address)
            db.child(name.toString()).setValue(customers).addOnSuccessListener {
                name.clear()
                address.clear()
                Toast.makeText(this.context,"Success",Toast.LENGTH_LONG).show()

            }.addOnFailureListener {
                Toast.makeText(this.context,"Failed",Toast.LENGTH_LONG).show()
            }

        }



    }


}