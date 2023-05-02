package com.gkprojects.cmmsandroidapp

//import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CustomerFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v=inflater.inflate(R.layout.fragment_customer, container, false)
        // Inflate the layout for this fragment
        val openbtn =v?.findViewById<FloatingActionButton>(R.id.openCustomerFragment)

        openbtn?.setOnClickListener {
           // Toast.makeText(context,"OKKKKKKKKK",Toast.LENGTH_LONG).show()
//            val tvtest=v?.findViewById<TextView>(R.id.fragment_tv_test)
//            tvtest?.text="testinggggg"

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, EditCustomerFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
             }

        return v
    }




}