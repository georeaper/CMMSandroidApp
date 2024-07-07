package com.gkprojects.cmmsandroidapp.Fragments.Customers


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.CustomerAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.Fragments.dashboardCustomer.DashboardCustomerFragment

import com.gkprojects.cmmsandroidapp.Models.CustomerVM
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.filterPopWindow
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList


class CustomerFragment : Fragment() {
    private lateinit var customerRecyclerView: RecyclerView

    private var templist =ArrayList<Customer>()
    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var customerViewModel: CustomerVM
    private lateinit var bottomSheetFragment : filterPopWindow
    private var customerActive : Boolean? =null


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

                templist.clear() // clear the templist,because it keeps populate everytime we open and close Customer Drawer
                for(i in it.indices)(
                        templist.add(it[i])
                        )

            })
        }




        val searchView = view.findViewById<TextInputEditText>(R.id.searchEditTextCustomer)
        searchView.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s != null) {
                    filterList(s.toString().lowercase(Locale.ROOT))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

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

        val filterBtn =view.findViewById<ImageButton>(R.id.imageButtonFilterCustomer)

        filterBtn.setOnClickListener {
            bottomSheetFragment = filterPopWindow.newInstance(
                R.layout.filter_pop_customer
            ) { filterView ->
                val radioGroup = filterView.findViewById<RadioGroup>(R.id.radioGroupCustomer)
                var customerActive: Boolean? = null

                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    // Get the selected RadioButton
                    val radioButton = filterView.findViewById<RadioButton>(checkedId)



                    // Apply filtering based on the selected radio button
                    customerActive = when (checkedId) {
                        R.id.radioCustomerButtonActive -> true
                        R.id.radioCustomerButtonDeactive -> false
                        else -> null
                    }
                }

                val filterButton: MaterialButton = filterView.findViewById(R.id.applyCustomerButton)
                filterButton.setOnClickListener {
                    Log.d("FilterButtonClick", "Filter button clicked")
                    if (customerActive != null) {
                        val filteredList = templist.filter { it.CustomerStatus == customerActive }
                        Log.d("FilteredCustomerList", "$filteredList")
                        customerAdapter.setData(filteredList as ArrayList)
                    }
                }
                val clearButton : MaterialButton =filterView.findViewById(R.id.clearCustomerButton)
                clearButton.setOnClickListener {
                    customerAdapter.setData(templist)
                }
            }
            bottomSheetFragment.show(childFragmentManager, "FilterBottomSheetFragment")
        }

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
     data.CustomerID?.let { bundle.putString("id", it) }

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


