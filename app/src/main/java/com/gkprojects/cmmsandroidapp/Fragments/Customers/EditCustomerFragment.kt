package com.gkprojects.cmmsandroidapp.Fragments.Customers


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment

import android.widget.CheckBox

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider


import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.Fragments.dashboardCustomer.DashboardCustomerFragment


import com.gkprojects.cmmsandroidapp.Models.CustomerVM

import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.FragmentEditCustomerBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID


class EditCustomerFragment : Fragment() {

    private lateinit var customerViewModel:CustomerVM
    private lateinit var toolbar: MaterialToolbar
    private var customerList : Customer? = null
    private var hospitalID : String? =null
    private lateinit var binding:FragmentEditCustomerBinding




    override  fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View
    {
//        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
//        bottomNavigationView.selectedItemId=R.id.action_home
        binding=FragmentEditCustomerBinding.inflate(inflater, container, false)

        return binding.root
    }
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerViewModel= ViewModelProvider(this)[CustomerVM::class.java]
        val args =this.arguments
        val id= args?.getString("id")
        hospitalID = id
        //in the block bellow i am calling toolbar so i can manipulate appbar menu
        // and set back action to move to previus fragment
        toolbar = requireActivity().findViewById(R.id.topAppBar)
        toolbar.title = " Edit Customer"
        val navigationIcon = toolbar.navigationIcon
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            val bundle = Bundle()
            if (id != null) {
                Log.d("id2",id.toString())
                bundle.putString("id", id)


                toolbar.navigationIcon = navigationIcon
                val fragmentManager =parentFragmentManager
                val fragmentTransaction=fragmentManager.beginTransaction()
                val fragment = DashboardCustomerFragment()
                fragment.arguments = bundle
                fragmentTransaction.replace(R.id.frameLayout1,fragment)
                fragmentTransaction.commit()
            }else{
                toolbar.navigationIcon = navigationIcon
                val fragmentManager =parentFragmentManager
                val fragmentTransaction=fragmentManager.beginTransaction()
                val fragment = CustomerFragment()
                fragmentTransaction.replace(R.id.frameLayout1,fragment)
                fragmentTransaction.commit()

            }

        }


        val name  =view.findViewById<TextInputEditText>(R.id.customerEdit_CustomerName_TextInput)

        val address =view.findViewById<TextInputEditText>(R.id.customerEdit_Address_TextInput)
        val phone1 =view.findViewById<TextInputEditText>(R.id.customerEdit_phone_TextInput)
        val zipcode =view.findViewById<TextInputEditText>(R.id.customerEdit_zipcode_TextInput)
        val description =view.findViewById<TextInputEditText>(R.id.customerEdit_description_TextInput)
        val email =view.findViewById<TextInputEditText>(R.id.customerEdit_Email_TextInput)
        val notes =view.findViewById<TextInputEditText>(R.id.customerEdit_notes_TextInput)
        val status =view.findViewById<CheckBox>(R.id.customerEdit_status_checkbox)
        val city =view.findViewById<TextInputEditText>(R.id.customerEdit_City_TextInput)
        var statusStr =""
        var remoteID : Int?= null
        var lastModified : String?=""
        var version : String? =""
        var dateCreated :String?=""

       if (id!=null){
           GlobalScope.launch(Dispatchers.Main) {
               customerViewModel.getCustomerDataByID(requireContext(),id).observe(viewLifecycleOwner,
                   Observer {

                       name.setText(it.Name)
                       phone1.setText(it.Phone)
                       description.setText(it.Description)
                       address.setText(it.Address)
                       zipcode.setText(it.ZipCode)
                       email.setText(it.Email)
                       notes.setText(it.Notes)
                       city.setText(it.City)
                       if(it.CustomerStatus!=null){
                           status.isChecked=it.CustomerStatus!!
                       }

                       remoteID=it.RemoteID
                       lastModified=it.LastModified
                       version=it.Version
                       dateCreated=it.DateCreated

                       customerList= Customer(hospitalID!!,remoteID,name.text.toString(),phone1.text.toString(),email.text.toString(),
                           address.text.toString(),zipcode.text.toString(),city.text.toString(),notes.text.toString(),
                           description.text.toString(),status.isChecked,lastModified,dateCreated,version)

                        })
                }


           }

       }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Clear the existing menu items
        menu.clear()

        // Inflate the new menu for the fragment
        inflater.inflate(R.menu.menu_main, menu)
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        super.onCreateOptionsMenu(menu, inflater)
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Notify the system that the fragment has an options menu
        setHasOptionsMenu(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.submit_menu_btn -> {

                Log.d("itemSelected",customerList.toString())

                if (customerList?.CustomerID!=null){
                    updateCustomer()
                }else{
                    insertCustomer()
                }

                return true
            }
            R.id.cancel_menu_btn -> {
                Toast.makeText(context,"Delete is UNAVAILABLE due to credentials , ", Toast.LENGTH_SHORT).show()
                // Handle menu item 2
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }



    @OptIn(DelicateCoroutinesApi::class)
    private fun insertCustomer(){
    Log.d("herehere","here")

        val remoteID : Int? =null
        val lastModified : String?=null
        val version : String? =null

        val status =binding.customerEditStatusCheckbox.isChecked
        Log.d("herehere2","here")
        val dateCreated =getCurrentDateAsString()
        Log.d("herehere3","${binding.customerEditCustomerNameTextInput.text.toString()}")
        customerList= Customer(
            UUID.randomUUID().toString(),
            remoteID,
            binding.customerEditCustomerNameTextInput.text.toString(),
            binding.customerEditPhoneTextInput.text.toString(),
            binding.customerEditEmailTextInput.text.toString(),
            binding.customerEditAddressTextInput.text.toString(),
            binding.customerEditZipcodeTextInput.text.toString(),
            binding.customerEditCityTextInput.text.toString(),
            binding.customerEditNotesTextInput.text.toString(),
            binding.customerEditDescriptionTextInput.text.toString(),
            status,lastModified,dateCreated,version)
        Log.d("InsertCustomer",customerList.toString())
        GlobalScope.launch(Dispatchers.Main) {
            customerViewModel.insert(requireContext(), customerList!!)
        }

    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun updateCustomer(){

        val status = binding.customerEditStatusCheckbox.isChecked
        val remoteID =customerList?.RemoteID
        val lastModified = getCurrentDateAsString()
        val dateCreated =customerList?.DateCreated
        val version =customerList?.Version

        customerList= Customer(hospitalID!!,
            remoteID,
            binding.customerEditCustomerNameTextInput.text.toString(),
            binding.customerEditPhoneTextInput.text.toString(),
            binding.customerEditEmailTextInput.text.toString(),
            binding.customerEditAddressTextInput.text.toString(),
            binding.customerEditZipcodeTextInput.text.toString(),
            binding.customerEditCityTextInput.text.toString(),
            binding.customerEditNotesTextInput.text.toString(),
            binding.customerEditDescriptionTextInput.text.toString(),
            status,
            lastModified,
            dateCreated,
            version)

        Log.d("updateCustomer",customerList.toString())
        GlobalScope.launch(Dispatchers.IO) {
            customerViewModel.updateCustomer(requireContext(), customerList!!) }

        }


    fun getCurrentDateAsString(): String {
        // Get the current date
        val currentDate = LocalDate.now()

        // Define a format for the date (optional, you can skip this step if you don't need a specific format)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        // Format the date to a string
        val formattedDate = currentDate.format(formatter)

        return formattedDate
    }

    }





