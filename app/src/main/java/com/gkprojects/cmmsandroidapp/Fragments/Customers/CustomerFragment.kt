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
import com.gkprojects.cmmsandroidapp.databinding.FragmentCustomerBinding
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
    private lateinit var binding: FragmentCustomerBinding

    private var selectedRadioButtonId: Int = R.id.radioCustomerButtonAll

    private var customerList =ArrayList<Customer>()
    private var filteredCustomerList =ArrayList<Customer>()
    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var customerViewModel: CustomerVM
    private lateinit var bottomSheetFragment : filterPopWindow



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentCustomerBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment


        return binding.root
    }


    @SuppressLint("UseRequireInsteadOfGet", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerRecyclerView = view.findViewById(R.id.customer_recyclerview)
        customerAdapter = this.context?.let { CustomerAdapter(it, ArrayList()) }!!

        customerRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = customerAdapter
        }
        customerViewModel = ViewModelProvider(this)[CustomerVM::class.java]


            customerViewModel.getAllCustomerData(requireContext()).observe(viewLifecycleOwner ,
                Observer{ list->
                    customerList.clear()
                    customerList=list as ArrayList<Customer>
                    customerAdapter.setData(customerList)

            })


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
        val filterButton=binding.imageButtonFilterCustomer
        filterButton.setOnClickListener {
            bottomSheetFragment  = filterPopWindow.newInstance(
                R.layout.filter_pop_customer
            ) { filterView ->
                val radioChoices: RadioGroup = filterView.findViewById(R.id.radioGroupCustomer)
                radioChoices.check(selectedRadioButtonId)
                val applyButton: MaterialButton = filterView.findViewById(R.id.applyCustomerButton)
                val clearButton: MaterialButton = filterView.findViewById(R.id.clearCustomerButton)

                radioChoices.setOnCheckedChangeListener { _, checkedId ->
                    selectedRadioButtonId = checkedId
                    val filteredList = when (checkedId) {
                        R.id.radioCustomerButtonActive -> customerList.filter { it.CustomerStatus as Boolean }
                        R.id.radioCustomerButtonDeactive -> customerList.filter { !it.CustomerStatus!!}
                        R.id.radioCustomerButtonAll -> customerList
                        else -> customerList
                    }
                    applyButton.setOnClickListener {
                        customerAdapter.setData(filteredList as ArrayList<Customer>)
                        bottomSheetFragment.dismiss()
                    }

                    clearButton.setOnClickListener {
                        customerAdapter.setData(customerList)
                    }

                }
                }
                bottomSheetFragment.show(childFragmentManager, "FilterEquipment")


        }


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
     if (query.isNotEmpty()){
        val filteredList= ArrayList<Customer>()
         for (i in customerList){
             if (i.Name?.lowercase(Locale.ROOT)?.contains(query)==true)
                 filteredList.add(i)
             Log.d("dataCustomer", filteredList.toString())
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
        val activity =requireActivity()

        val drawerLayout = activity.findViewById<DrawerLayout>(R.id.DrawLayout)

        val toolbar: MaterialToolbar = activity.findViewById(R.id.topAppBar)
        toolbar.title="Customer"


        val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

 private fun passDataCustomer(data : Customer){

     val bundle = Bundle()
     data.CustomerID.let { bundle.putString("id", it) }

     bundle.putString("name", data.Name)
     bundle.putString("address", data.Address)
     bundle.putString("email", data.Email)
     bundle.putString("phone", data.Phone)

     Log.d("bundlecheck",bundle.toString())

        if (bundle.isEmpty){
            Toast.makeText(context,"Bundle is Null",Toast.LENGTH_SHORT).show()

        }else {

            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = DashboardCustomerFragment()
            fragment.arguments = bundle
            fragmentTransaction.replace(R.id.frameLayout1, fragment)
            fragmentTransaction.commit()
        }

    }




}


