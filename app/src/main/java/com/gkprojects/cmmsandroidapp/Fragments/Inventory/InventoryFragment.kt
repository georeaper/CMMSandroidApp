package com.gkprojects.cmmsandroidapp.Fragments.Inventory

import android.app.AlertDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.Inventory
import com.gkprojects.cmmsandroidapp.DataClasses.Tools
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.DialogInventoryInsertBinding
import com.gkprojects.cmmsandroidapp.databinding.DialogToolsInsertBinding
import com.gkprojects.cmmsandroidapp.databinding.FragmentInventoryBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar
import java.util.Locale
import java.util.UUID


class InventoryFragment : Fragment() {
    private lateinit var binding: FragmentInventoryBinding
    private lateinit var inventoryVM :InventoryVM
    private lateinit var adapterInventory : AdapterInventoryRecyclerView
    private var inventoryList =ArrayList<Inventory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInventoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inventoryVM=ViewModelProvider(this)[InventoryVM ::class.java]
        adapterInventory = AdapterInventoryRecyclerView(inventoryList,this)
        binding.inventoryRecyclerview.apply {
            adapter=adapterInventory
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this.context)
        }
        binding.inventoryFloatingBtn.setOnClickListener {
            openDialogInsertTools()
        }
        binding.searchEditTextInventory.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s != null) {
                    filterList(s.toString().lowercase(Locale.ROOT))
                }
            }

            override fun afterTextChanged(s: Editable?) {
                filterList(s.toString())

            }

        })
        try {
            inventoryVM.getAllInventory(requireContext()).observe(viewLifecycleOwner, Observer{
                Log.d("inventory","$it")
                val tempList : ArrayList<Inventory> = it as ArrayList<Inventory>
                Log.d("inventory2","$tempList")
                inventoryList=tempList
                Log.d("inventory3","$inventoryList")

                adapterInventory.setData(inventoryList)
            })
        }catch (e :Exception){
            Log.d("inventoryExceptionCatch","$e")
        }



    }
    private fun openDialogInsertTools() {
        val dialogBinding = DialogInventoryInsertBinding.inflate(LayoutInflater.from(requireContext()))


        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("OK") { _, _ ->
                val inventory = Inventory(UUID.randomUUID().toString() ,null,null,null,null,null,null,null,null,null)

                inventory.Title=dialogBinding.dialogInventoryInsertEditTextPartNumber.text.toString()
                inventory.Description=dialogBinding.dialogInventoryInsertEditTextDescription.text.toString()
                //inventory.Value=dialogBinding.dialogInventoryInsertEditTextValue.text.to
                //inventory.Quantity


                try{
                    insertInventoryIntoDatabase(inventory)

                    inventoryList.add(inventory)
                    adapterInventory.setData(inventoryList)
                }catch (e:Exception){
                    Log.d("toolsInsertCatchError","$e")
                }
                //Log.d("toolsInsert","$toolsList $tools")


            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()

        // Set the dimensions of the dialog
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(width, height)
    }

    fun openDialogDisplayTools(inventory: Inventory) {
        val dialogBinding = DialogInventoryInsertBinding.inflate(LayoutInflater.from(requireContext()))

        // Set the fields in the dialog to the values from the selected Tools object
        dialogBinding.dialogInventoryInsertEditTextPartNumber.setText(inventory.Title)
        dialogBinding.dialogInventoryInsertEditTextDescription.setText(inventory.Description)


        // Disable the EditTexts so the user can't edit the values
        dialogBinding.dialogInventoryInsertEditTextPartNumber.isEnabled = false
        dialogBinding.dialogInventoryInsertEditTextDescription.isEnabled = false
        dialogBinding.dialogInventoryInsertEditTextQuantity.isEnabled=false
        dialogBinding.dialogInventoryInsertEditTextValue.isEnabled=false


        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialog.show()

        // Set the dimensions of the dialog
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(width, height)
    }

    private fun insertInventoryIntoDatabase(inventory: Inventory){
        inventoryVM.insertInventory(requireContext(),inventory)

    }
    private fun filterList(query:String){
        if (query!=null){
            val filteredList= ArrayList<Inventory>()
            for (i in inventoryList){
                if (i.Title!!.lowercase(Locale.ROOT).contains(query)||i.Description!!.lowercase(Locale.ROOT).contains(query))
                    filteredList.add(i)
                Log.d("filteredInventory", filteredList.toString())
            }
            if (filteredList.isEmpty() ){
                adapterInventory.setData(filteredList)
                Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

            }else{
                adapterInventory.setData(filteredList)
            }
        }

    }


}