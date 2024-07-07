package com.gkprojects.cmmsandroidapp.Fragments.SpecialTools

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkprojects.cmmsandroidapp.DataClasses.Inventory
import com.gkprojects.cmmsandroidapp.DataClasses.Tools
import com.gkprojects.cmmsandroidapp.databinding.DialogToolsInsertBinding
import com.gkprojects.cmmsandroidapp.databinding.FragmentSpecialToolsBinding
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import java.util.UUID


class SpecialTools : Fragment() {
    private lateinit var binding :FragmentSpecialToolsBinding
    private lateinit var adapterTools : AdapterSpecialToolsRecyclerView
    private var toolsList =ArrayList<Tools>()
    private lateinit var specialToolsVM :SpecialToolsVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSpecialToolsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        specialToolsVM= ViewModelProvider(this)[SpecialToolsVM::class.java]

        adapterTools = AdapterSpecialToolsRecyclerView(toolsList,this)


        binding.specialToolsFloatingBtn.setOnClickListener {
            openDialogInsertTools()
        }
      //  binding.specialToolsTextInputEditTextSearch
        binding.specialToolsRecyclerview.apply {
            adapter=adapterTools
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this.context)
        }

        try {
            specialToolsVM.getTools(requireContext()).observe(viewLifecycleOwner, Observer {
                Log.d("specialTools","$it")
                val tempList : ArrayList<Tools> = it as ArrayList<Tools>
                Log.d("specialTools2","$tempList")
                toolsList=tempList
                Log.d("specialTools3","$toolsList")

                adapterTools.setData(toolsList)
            })
        }catch (e :Exception){
            Log.d("toolsExceptionCatch","$e")
        }
        binding.specialToolsTextInputEditTextSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s != null) {
                    filterList(s.toString().lowercase(Locale.ROOT))
                }
            }

            override fun afterTextChanged(s: Editable?) {
//                filterList(s.toString())
            }

        })


    }

    private fun openDialogInsertTools() {
        val dialogBinding = DialogToolsInsertBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()

        dialogBinding.calibrationDateEditText.setOnClickListener{
            fragmentManager?.let { it1 -> picker.show(it1, picker.toString()) }
        }
        picker.addOnPositiveButtonClickListener {
            val calendar2 = Calendar.getInstance()
            calendar2.timeInMillis = it
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val selectedDate = format.format(calendar2.time)
            dialogBinding.calibrationDateEditText.setText(selectedDate)
        }



        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("OK") { _, _ ->
                val tools = Tools(UUID.randomUUID().toString() ,null,null,null,null,null,null,null,null,null,null)

                tools.Title=dialogBinding.dialogToolsInsertEditTextTitle.text.toString()
                tools.SerialNumber=dialogBinding.dialogToolsInsertEditTextSerialNumber.text.toString()
                tools.Model=dialogBinding.dialogToolsInsertEditTextModel.text.toString()
                tools.Manufacturer=dialogBinding.dialogToolsInsertEditTextManufacturer.text.toString()
                tools.Description=dialogBinding.dialogToolsInsertEditTextDescription.text.toString()
                tools.CalibrationDate=dialogBinding.calibrationDateEditText.text.toString()
                //Toast.makeText(requireContext(),"$temp",Toast.LENGTH_SHORT).show()

                try{
                    insertToolsIntoDatabase(tools)

                    toolsList.add(tools)
                    adapterTools.setData(toolsList)
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

     fun openDialogDisplayTools(tools: Tools) {
        val dialogBinding = DialogToolsInsertBinding.inflate(LayoutInflater.from(requireContext()))

        // Set the fields in the dialog to the values from the selected Tools object
        dialogBinding.dialogToolsInsertEditTextTitle.setText(tools.Title)
        dialogBinding.dialogToolsInsertEditTextSerialNumber.setText(tools.SerialNumber)
        dialogBinding.dialogToolsInsertEditTextModel.setText(tools.Model)
        dialogBinding.dialogToolsInsertEditTextManufacturer.setText(tools.Manufacturer)
        dialogBinding.dialogToolsInsertEditTextDescription.setText(tools.Description)
        dialogBinding.calibrationDateEditText.setText(tools.CalibrationDate)

        // Disable the EditTexts so the user can't edit the values
        dialogBinding.dialogToolsInsertEditTextTitle.isEnabled = false
        dialogBinding.dialogToolsInsertEditTextSerialNumber.isEnabled = false
        dialogBinding.dialogToolsInsertEditTextModel.isEnabled = false
        dialogBinding.dialogToolsInsertEditTextManufacturer.isEnabled = false
        dialogBinding.dialogToolsInsertEditTextDescription.isEnabled = false
        dialogBinding.calibrationDateEditText.isEnabled = false

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialog.show()

        // Set the dimensions of the dialog
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(width, height)
    }

    private fun insertToolsIntoDatabase(tools :Tools){
        specialToolsVM.insertTools(requireContext(),tools)

    }
    private fun filterList(query:String){
        if (query!=null){
            val filteredList= ArrayList<Tools>()
            for (i in toolsList){
                if (i.Title!!.lowercase(Locale.ROOT).contains(query)||i.Description!!.lowercase(Locale.ROOT).contains(query))
                    filteredList.add(i)
                Log.d("filteredInventory", filteredList.toString())
            }
            if (filteredList.isEmpty() ){
                adapterTools.setData(filteredList)
                Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

            }else{
                adapterTools.setData(filteredList)
            }
        }

    }
}