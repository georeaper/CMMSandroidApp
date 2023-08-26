package com.gkprojects.cmmsandroidapp.Fragments


import android.annotation.SuppressLint
import android.content.Intent
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
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.Models.CustomerVM
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*



//private lateinit var intent :Intent




class CustomerFragment : Fragment() {
    private lateinit var customerRecyclerView: RecyclerView

    private var templist =ArrayList<Hospital>()
    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var customerViewModel: CustomerVM


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


    @SuppressLint("UseRequireInsteadOfGet", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerRecyclerView = view.findViewById(R.id.customer_recyclerview)
        customerAdapter = this.context?.let { CustomerAdapter(it, ArrayList<Hospital>()) }!!
        customerRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = customerAdapter
        }
        customerViewModel = ViewModelProvider(this).get(CustomerVM::class.java)

          context?.let {
            customerViewModel.getAllCustomerData(it).observe(viewLifecycleOwner, Observer {
                customerAdapter.setData(it as ArrayList<Hospital>)
                Log.d("debug123",it.toString())
                templist.clear() // clear the templist,because it keeps populate everytime we open and close Customer Drawer
                for(i in it.indices)(
                        templist.add(it[i])
                        )
                Log.d("templist", templist.size.toString())
            })
        }




        val searchView = view.findViewById<SearchView>(R.id.searchView_customers)
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


        val openbtn =view.findViewById<FloatingActionButton>(R.id.openCustomerFragment)

        customerAdapter.setOnClickListener(object : CustomerAdapter.OnClickListener{
            override fun onClick(position: Int, model: Hospital) {
//                var temp: java.io.Serializable = model as java.io.Serializable
                Toast.makeText(context,model.toString(),Toast.LENGTH_LONG).show()
                passDataCustomer(model)

                //passDataCustomer()
            }
        })

        openbtn?.setOnClickListener {


            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, EditCustomerFragment())
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


                GlobalScope.launch(Dispatchers.IO) {

                    context?.let { customerViewModel.deleteCustomer(it, templist[viewHolder.absoluteAdapterPosition]) }

                }

                context?.let {
                    customerViewModel.getAllCustomerData(it).observe(viewLifecycleOwner, Observer {
                        customerAdapter.setData(it as ArrayList<Hospital>)

                    })
                }
            }


        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(customerRecyclerView)

    }

 private fun filterList(query:String){
     if (query!=null){
        val filteredList= ArrayList<Hospital>()
         for (i in templist){
             if (i.name.lowercase(Locale.ROOT).contains(query))
                 filteredList.add(i)
             Log.d("datacustomer", filteredList.toString())
         }
         if (filteredList.isEmpty() ){
            Toast.makeText(context,"Empty List",Toast.LENGTH_SHORT).show()

         }else{
             customerAdapter.setData(filteredList)
         }
     }


 }


 private fun passDataCustomer(data : Hospital){
        //var temp: java.io.Serializable = data as java.io.Serializable
        val bundle = Bundle()
        data.hospitalID?.let { bundle.putInt("id", it.toInt()) }
        //bundle.putString("id", data.hospitalID.toString())
        bundle.putString("name", data.name)
        bundle.putString("address", data.address)
        bundle.putString("city", data.city)
        bundle.putString("email", data.contactEmail)
        bundle.putString("contactPerson", data.contactPerson)
        bundle.putString("phone", data.contactPhone)
        bundle.putString("zipcode", data.zipCode)

        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment =EditCustomerFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.commit()

    }




}


