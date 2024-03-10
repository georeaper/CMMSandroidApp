package com.gkprojects.cmmsandroidapp.Fragments.Configuration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.CustomizedField
import com.gkprojects.cmmsandroidapp.Adapter.CustomizedFieldsAdapter
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.FragmentCustomizedFieldBinding
import org.json.JSONArray
import org.json.JSONObject
import yuku.ambilwarna.AmbilWarnaDialog
import java.io.File


class CustomizedFieldFragment : Fragment() {

    // Properties for the adapter, list of items, and view binding.
    private lateinit var contractStatusRecyclerView: RecyclerView
    private lateinit var contractTypeRecyclerView: RecyclerView
    private lateinit var customerStatusRecyclerView: RecyclerView
    private lateinit var equipmentModelRecyclerView: RecyclerView
    private lateinit var equipmentManufacturerRecyclerView: RecyclerView
    private lateinit var equipmentCategoryRecyclerView: RecyclerView
    private lateinit var equipmentStatusRecyclerView: RecyclerView
    private lateinit var caseUrgencyRecyclerView: RecyclerView
    private lateinit var caseActiveRecyclerView: RecyclerView

    private var defaultColor: Int = 0xFFFFFF // Default Color
    private lateinit var binding: FragmentCustomizedFieldBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomizedFieldBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment using view binding.

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews(binding.root)
        setupListeners()
    }
    private fun setupRecyclerViews(view: View) {
        contractStatusRecyclerView = view.findViewById(R.id.customizedFields_contractStatus_RecyclerView)
        contractTypeRecyclerView = view.findViewById(R.id.customizedFields_contractType_RecyclerView)
        customerStatusRecyclerView= binding.customizedFieldsCustomerStatusRecyclerView
        equipmentModelRecyclerView=binding.customizedFieldsEquipmentModelRecyclerView
        equipmentManufacturerRecyclerView=binding.customizedFieldsEquipmentManufacturerRecyclerView
        equipmentCategoryRecyclerView=binding.customizedFieldsEquipmentCategoryRecyclerView
        equipmentStatusRecyclerView=binding.customizedFieldsEquipmentStatusRecyclerView
        caseUrgencyRecyclerView=binding.customizedFieldsCaseUrgencyRecyclerView
        caseActiveRecyclerView=binding.customizedFieldsCaseActiveRecyclerView
        // ... initialize other RecyclerViews

        // case Active status
        val caseActiveAdapter =CustomizedFieldsAdapter(mutableListOf())
        caseActiveAdapter.setOnDeleteAction { itemToDelete->
            caseActiveAdapter.removeItem(itemToDelete)
            removeItemFromJsonFile(itemToDelete,"caseActive.json")
        }
        caseActiveRecyclerView.adapter=caseActiveAdapter
        caseActiveRecyclerView.layoutManager=LinearLayoutManager(context)
        loadFromJsonFile(caseActiveAdapter,"caseActive.json")


        //contractStatus

        val contractStatusAdapter = CustomizedFieldsAdapter(mutableListOf())
        contractStatusAdapter.setOnDeleteAction { itemToDelete ->
             // Handle the delete action, e.g., update the backend or show confirmation dialog
             contractStatusAdapter.removeItem(itemToDelete)
             removeItemFromJsonFile(itemToDelete,"contractStatus.json")
         }
        contractStatusRecyclerView.adapter = contractStatusAdapter
        contractStatusRecyclerView.layoutManager = LinearLayoutManager(context)
        loadFromJsonFile(contractStatusAdapter,"contractStatus.json")

        // Repeat setup for other RecyclerViews
        val contractTypeAdapter = CustomizedFieldsAdapter(mutableListOf())
        contractTypeAdapter.setOnDeleteAction { itemToDelete ->
            // Handle the delete action, e.g., update the backend or show confirmation dialog
            contractTypeAdapter.removeItem(itemToDelete)
            removeItemFromJsonFile(itemToDelete,"contractType.json")
        }
        contractTypeRecyclerView.adapter=contractTypeAdapter
        contractTypeRecyclerView.layoutManager=LinearLayoutManager(context)
        loadFromJsonFile(contractTypeAdapter,"contractType.json")

        val customerStatusAdapter =CustomizedFieldsAdapter(mutableListOf())
        customerStatusAdapter.setOnDeleteAction { itemToDelete ->
            customerStatusAdapter.removeItem(itemToDelete)
            removeItemFromJsonFile(itemToDelete,"customerStatus.json")
        }
        customerStatusRecyclerView.adapter=customerStatusAdapter
        customerStatusRecyclerView.layoutManager=LinearLayoutManager(context)
        loadFromJsonFile(customerStatusAdapter,"customerStatus.json")

        // Equipment Model
        val equipmentModelAdapter =CustomizedFieldsAdapter(mutableListOf())
        equipmentModelAdapter.setOnDeleteAction { itemToDelete ->
            equipmentModelAdapter.removeItem(itemToDelete)
            removeItemFromJsonFile(itemToDelete,"equipmentModel.json")
        }
        equipmentModelRecyclerView.adapter=equipmentModelAdapter
        equipmentModelRecyclerView.layoutManager=LinearLayoutManager(context)
        loadFromJsonFile(equipmentModelAdapter,"equipmentModel.json")
        //Equipment Manufacturer
        val equipmentManufacturerAdapter =CustomizedFieldsAdapter(mutableListOf())
        equipmentManufacturerAdapter.setOnDeleteAction { itemToDelete ->
            equipmentManufacturerAdapter.removeItem(itemToDelete)
            removeItemFromJsonFile(itemToDelete,"equipmentManufacturer.json")
        }
        equipmentManufacturerRecyclerView.adapter=equipmentManufacturerAdapter
        equipmentManufacturerRecyclerView.layoutManager=LinearLayoutManager(context)
        loadFromJsonFile(equipmentManufacturerAdapter,"equipmentManufacturer.json")
        // Equipment Category
        val equipmentCategoryAdapter =CustomizedFieldsAdapter(mutableListOf())
        equipmentCategoryAdapter.setOnDeleteAction { itemToDelete ->
            equipmentCategoryAdapter.removeItem(itemToDelete)
            removeItemFromJsonFile(itemToDelete,"equipmentCategory.json")
        }
        equipmentCategoryRecyclerView.adapter=equipmentCategoryAdapter
        equipmentCategoryRecyclerView.layoutManager=LinearLayoutManager(context)
        loadFromJsonFile(equipmentCategoryAdapter,"equipmentCategory.json")
        //Equipment Status
        val equipmentStatusAdapter =CustomizedFieldsAdapter(mutableListOf())
        equipmentStatusAdapter.setOnDeleteAction { itemToDelete ->
            equipmentStatusAdapter.removeItem(itemToDelete)
            removeItemFromJsonFile(itemToDelete,"equipmentStatus.json")
        }
        equipmentStatusRecyclerView.adapter=equipmentStatusAdapter
        equipmentStatusRecyclerView.layoutManager=LinearLayoutManager(context)
        loadFromJsonFile(equipmentStatusAdapter,"equipmentStatus.json")
        //Case Urgency
        val caseUrgencyAdapter =CustomizedFieldsAdapter(mutableListOf())
        caseUrgencyAdapter.setOnDeleteAction { itemToDelete ->
            caseUrgencyAdapter.removeItem(itemToDelete)
            removeItemFromJsonFile(itemToDelete,"caseUrgency.json")
        }
        caseUrgencyRecyclerView.adapter=caseUrgencyAdapter
        caseUrgencyRecyclerView.layoutManager=LinearLayoutManager(context)
        loadFromJsonFile(caseUrgencyAdapter,"caseUrgency.json")

    }
    private fun setupListeners() {
        // Example for Contract Status
        binding.customizedFieldsContractStatusButton.setOnClickListener {
            val name = binding.customizedFieldsContractStatusInputEditText.text.toString()
            if(name.isNotEmpty()) {
                val customField = CustomizedField(name, defaultColor)
                (contractStatusRecyclerView.adapter as CustomizedFieldsAdapter).addItem(customField)
                saveToJsonFile(customField, "contractStatus.json")
                binding.customizedFieldsContractStatusInputEditText.text?.clear()
                defaultColor=0xFFFFFF
                binding.customizedFieldsContractStatusColorPicker.setBackgroundColor(defaultColor)
                // Update your RecyclerView adapter if necessary
            } else {
                Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.customizedFieldsContractStatusColorPicker.setOnClickListener {
            openColorPicker(binding.customizedFieldsContractStatusColorPicker)
        }

        //For contract Type
        binding.customizedFieldsContractTypeButton.setOnClickListener {
            val name = binding.customizedFieldsContractTypeInputEditText.text.toString()
            if(name.isNotEmpty()) {
                val customField = CustomizedField(name, defaultColor)
                (contractTypeRecyclerView.adapter as CustomizedFieldsAdapter).addItem(customField)
                saveToJsonFile(customField, "contractType.json")
                binding.customizedFieldsContractTypeInputEditText.text?.clear()
                defaultColor=0xFFFFFF
                binding.customizedFieldsContractTypeColorPicker.setBackgroundColor(defaultColor)
                // Update your RecyclerView adapter if necessary
            } else {
                Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.customizedFieldsContractTypeColorPicker.setOnClickListener {
            openColorPicker(binding.customizedFieldsContractTypeColorPicker)
        }

        binding.customizedFieldsCustomerStatusButton.setOnClickListener {
            val name = binding.customizedFieldsCustomerStatusInputEditText.text.toString()
            if(name.isNotEmpty()) {
                val customField = CustomizedField(name, defaultColor)
                (contractTypeRecyclerView.adapter as CustomizedFieldsAdapter).addItem(customField)
                saveToJsonFile(customField, "customerStatus.json")
                binding.customizedFieldsCustomerStatusInputEditText.text?.clear()
                defaultColor=0xFFFFFF
                binding.customizedFieldsCustomerStatusColorPicker.setBackgroundColor(defaultColor)
                // Update your RecyclerView adapter if necessary
            } else {
                Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.customizedFieldsCustomerStatusColorPicker.setOnClickListener {
            openColorPicker(binding.customizedFieldsCustomerStatusColorPicker)
        }


        binding.customizedFieldsEquipmentModelButton.setOnClickListener {
            val name = binding.customizedFieldsEquipmentModelInputEditText.text.toString()
            if(name.isNotEmpty()) {
                val customField = CustomizedField(name, defaultColor)
                (equipmentModelRecyclerView.adapter as CustomizedFieldsAdapter).addItem(customField)
                saveToJsonFile(customField, "equipmentModel.json")
                binding.customizedFieldsEquipmentModelInputEditText.text?.clear()
                defaultColor=0xFFFFFF
                binding.customizedFieldsEquipmentModelColorPicker.setBackgroundColor(defaultColor)
                // Update your RecyclerView adapter if necessary
            } else {
                Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.customizedFieldsEquipmentModelColorPicker.setOnClickListener {
            openColorPicker(binding.customizedFieldsEquipmentModelColorPicker)
        }


//        //equipment Manufacturer
        binding.customizedFieldsEquipmentManufacturerButton.setOnClickListener {
            val name = binding.customizedFieldsEquipmentManufacturerInputEditText.text.toString()
            if(name.isNotEmpty()) {
                val customField = CustomizedField(name, defaultColor)
                (equipmentManufacturerRecyclerView.adapter as CustomizedFieldsAdapter).addItem(customField)
                saveToJsonFile(customField, "equipmentManufacturer.json")
                binding.customizedFieldsEquipmentManufacturerInputEditText.text?.clear()
                defaultColor=0xFFFFFF
                binding.customizedFieldsEquipmentManufacturerColorPicker.setBackgroundColor(defaultColor)
                // Update your RecyclerView adapter if necessary
            } else {
                Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.customizedFieldsEquipmentManufacturerColorPicker.setOnClickListener {
            openColorPicker(binding.customizedFieldsEquipmentManufacturerColorPicker)
        }

//        //equipment Category
        binding.customizedFieldsEquipmentCategoryButton.setOnClickListener {
            val name = binding.customizedFieldsEquipmentCategoryInputEditText.text.toString()
            if(name.isNotEmpty()) {
                val customField = CustomizedField(name, defaultColor)
                (equipmentCategoryRecyclerView.adapter as CustomizedFieldsAdapter).addItem(customField)
                saveToJsonFile(customField, "equipmentCategory.json")
                binding.customizedFieldsEquipmentCategoryInputEditText.text?.clear()
                defaultColor=0xFFFFFF
                binding.customizedFieldsEquipmentCategoryColorPicker.setBackgroundColor(defaultColor)
                // Update your RecyclerView adapter if necessary
            } else {
                Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.customizedFieldsEquipmentCategoryColorPicker.setOnClickListener {
            openColorPicker(binding.customizedFieldsEquipmentCategoryColorPicker)
        }
//        //equipment Status
        binding.customizedFieldsEquipmentStatusButton.setOnClickListener {
            val name = binding.customizedFieldsEquipmentStatusInputEditText.text.toString()
            if(name.isNotEmpty()) {
                val customField = CustomizedField(name, defaultColor)
                (equipmentStatusRecyclerView.adapter as CustomizedFieldsAdapter).addItem(customField)
                saveToJsonFile(customField, "equipmentStatus.json")
                binding.customizedFieldsEquipmentStatusInputEditText.text?.clear()
                defaultColor=0xFFFFFF
                binding.customizedFieldsEquipmentStatusColorPicker.setBackgroundColor(defaultColor)
                // Update your RecyclerView adapter if necessary
            } else {
                Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.customizedFieldsEquipmentStatusColorPicker.setOnClickListener {
            openColorPicker(binding.customizedFieldsEquipmentStatusColorPicker)
        }
//        //case Urgency
        binding.customizedFieldsCaseUrgencyButton.setOnClickListener {
            val name = binding.customizedFieldsCaseUrgencyInputEditText.text.toString()
            if(name.isNotEmpty()) {
                val customField = CustomizedField(name, defaultColor)
                (caseUrgencyRecyclerView.adapter as CustomizedFieldsAdapter).addItem(customField)
                saveToJsonFile(customField, "caseUrgency.json")
                binding.customizedFieldsCaseUrgencyInputEditText.text?.clear()
                defaultColor=0xFFFFFF
                binding.customizedFieldsCaseUrgencyColorPicker.setBackgroundColor(defaultColor)

                // Update your RecyclerView adapter if necessary
            } else {
                Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.customizedFieldsCaseUrgencyColorPicker.setOnClickListener {
            openColorPicker(binding.customizedFieldsCaseUrgencyColorPicker)
        }
        //case Active
        binding.customizedFieldsCaseActiveButton.setOnClickListener {
            val name = binding.customizedFieldsCaseActiveInputEditText.text.toString()
            if(name.isNotEmpty()) {
                val customField = CustomizedField(name, defaultColor)
                (caseActiveRecyclerView.adapter as CustomizedFieldsAdapter).addItem(customField)
                saveToJsonFile(customField, "caseActive.json")
                binding.customizedFieldsCaseActiveInputEditText.text?.clear()
                defaultColor=0xFFFFFF
                binding.customizedFieldsCaseActiveColorPicker.setBackgroundColor(defaultColor)
                // Update your RecyclerView adapter if necessary
            } else {
                Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.customizedFieldsCaseActiveColorPicker.setOnClickListener {
            openColorPicker(binding.customizedFieldsCaseActiveColorPicker)
        }



    }

    private fun openColorPicker(button : Button) {
        val colorPicker = AmbilWarnaDialog(context, defaultColor, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {
                // Handle cancel action if needed
            }

            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                // color is the selected color
                defaultColor = color  // Save the selected color
                // Perform actions with the selected color
                button.setBackgroundColor(defaultColor)
            }
        })
        colorPicker.show()
    }


    // Function to remove an item from the JSON file.
    private fun removeItemFromJsonFile(itemToDelete: CustomizedField, filename: String) {
        try {
            val file = File(requireActivity().filesDir, filename)
            if (!file.exists()) return

            val jsonString = file.readText()
            val jsonArray = JSONArray(jsonString)
            val newJsonArray = JSONArray()

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val itemName = item.optString("name")
                val itemColor = item.optInt("color")
                if (itemName != itemToDelete.text || itemColor != itemToDelete.color) {
                    newJsonArray.put(item)
                }
            }

            file.writeText(newJsonArray.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // Function to save a new item to the JSON file.
    private fun saveToJsonFile(item: CustomizedField, filename: String) {
        try {
            val file = File(requireActivity().filesDir, filename)
            val jsonArray = if (file.exists()) JSONArray(file.readText()) else JSONArray()

            val jsonObject = JSONObject().apply {
                put("name", item.text)
                put("color", item.color)
            }
            jsonArray.put(jsonObject)

            file.writeText(jsonArray.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // Function to load items from the JSON file.
    private fun loadFromJsonFile(adapter: CustomizedFieldsAdapter, filename: String) {
        try {
            val file = File(requireActivity().filesDir, filename)
            if (!file.exists()) return

            val jsonString = file.readText()
            val jsonArray = JSONArray(jsonString)
            val items = mutableListOf<CustomizedField>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.optString("name")
                val color = jsonObject.optInt("color")
                items.add(CustomizedField(name, color))
            }

            adapter.updateData(items)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}
