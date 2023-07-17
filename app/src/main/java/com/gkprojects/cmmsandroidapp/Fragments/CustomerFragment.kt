package com.gkprojects.cmmsandroidapp.Fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.CustomerAdapter
import com.gkprojects.cmmsandroidapp.Adapter.EquipmentAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.Models.CustomerVM
import com.gkprojects.cmmsandroidapp.Models.EquipmentVM
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


private lateinit var customerRecyclerView: RecyclerView


private lateinit var customerAdapter: CustomerAdapter
private lateinit var customerViewModel: CustomerVM


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


        return v
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerRecyclerView=view.findViewById(R.id.customer_recyclerview)
        customerAdapter= this.context?.let { CustomerAdapter(it, ArrayList<Hospital>()) }!!
        customerRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this.context)
            adapter= customerAdapter
        }
        customerViewModel= ViewModelProvider(this).get(CustomerVM::class.java)
        context?.let {
            customerViewModel.getAllCustomerData(it).observe(viewLifecycleOwner, Observer {
                customerAdapter.setData(it as ArrayList<Hospital>)
            })
        }

        val openbtn =view.findViewById<FloatingActionButton>(R.id.openCustomerFragment)


        openbtn?.setOnClickListener {


            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, EditCustomerFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }


    }







}