package com.gkprojects.cmmsandroidapp.Fragments.SpecialTools

import android.app.AlertDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkprojects.cmmsandroidapp.DataClasses.Tools
import com.gkprojects.cmmsandroidapp.databinding.DialogToolsInsertBinding
import com.gkprojects.cmmsandroidapp.databinding.FragmentSpecialToolsBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar
import java.util.Locale
import java.util.UUID


class SpecialTools : Fragment() {
    private lateinit var binding :FragmentSpecialToolsBinding
    private lateinit var adapterTools : AdapterSpecialToolsRecyclerView
    private var toolsList =ArrayList<Tools>()


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
                val temp = Tools(UUID.randomUUID().toString() ,null,null,null,null,null,null,null,null,null,null)

                temp.Title=dialogBinding.dialogToolsInsertEditTextTitle.text.toString()
                temp.SerialNumber=dialogBinding.dialogToolsInsertEditTextSerialNumber.text.toString()
                temp.Model=dialogBinding.dialogToolsInsertEditTextModel.text.toString()
                temp.Manufacturer=dialogBinding.dialogToolsInsertEditTextManufacturer.text.toString()
                temp.Description=dialogBinding.dialogToolsInsertEditTextDescription.text.toString()
                temp.CalibrationDate=dialogBinding.calibrationDateEditText.text.toString()
                Toast.makeText(requireContext(),"$temp",Toast.LENGTH_SHORT).show()
                toolsList.add(temp)
                Log.d("toolsInsert","$toolsList $temp")
                adapterTools.setData(toolsList)

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
}