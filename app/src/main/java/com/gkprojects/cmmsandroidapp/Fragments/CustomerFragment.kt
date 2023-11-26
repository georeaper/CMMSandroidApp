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
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.CustomerAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.Customer

import com.gkprojects.cmmsandroidapp.Models.CustomerVM
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*








class CustomerFragment : Fragment() {
    private lateinit var customerRecyclerView: RecyclerView

    private var templist =ArrayList<Customer>()
    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var customerViewModel: CustomerVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId=R.id.action_home

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
        customerAdapter = this.context?.let { CustomerAdapter(it, ArrayList<Customer>()) }!!

        customerRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = customerAdapter
        }
        customerViewModel = ViewModelProvider(this).get(CustomerVM::class.java)

          context?.let {
            customerViewModel.getAllCustomerData(it).observe(viewLifecycleOwner, Observer {
                customerAdapter.setData(it as ArrayList<Customer>)
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
            override fun onClick(position: Int, model: Customer) {
//                var temp: java.io.Serializable = model as java.io.Serializable
                //Toast.makeText(context,model.toString(),Toast.LENGTH_LONG).show()
                passDataCustomer(model)


            }
        })

        openbtn?.setOnClickListener {


            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, EditCustomerFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }


    }

 private fun filterList(query:String){
     if (query!=null){
        val filteredList= ArrayList<Customer>()
         for (i in templist){
             if (i.Name?.lowercase(Locale.ROOT)?.contains(query)==true)
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
    override fun onResume() {
        super.onResume()
        var activity =requireActivity()

        var drawerLayout = activity.findViewById<DrawerLayout>(R.id.DrawLayout)
        val navView: NavigationView = activity.findViewById(R.id.navView)
        val toolbar: MaterialToolbar = activity.findViewById(R.id.topAppBar)
        toolbar.title="Customer"


        var toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

 private fun passDataCustomer(data : Customer){

     val bundle = Bundle()
     data.CustomerID?.let { bundle.putInt("id", it) }

     bundle.putString("name", data.Name)
     bundle.putString("address", data.Address)
     bundle.putString("email", data.Email)
     bundle.putString("phone", data.Phone)

     Log.d("bundlecheck",bundle.toString())

        if (bundle==null){
            Toast.makeText(context,"Bundle is Null",Toast.LENGTH_SHORT)

        }else {
            Log.d("YourTag", "Before fragment transaction");
            val fragmentManager = parentFragmentManager
            Log.d("YourTag", "After fragmentManager");
            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = DashboardCustomerFragment()
            fragment.arguments = bundle
            fragmentTransaction.replace(R.id.frameLayout1, fragment)
            fragmentTransaction.commit()
        }

    }




}


