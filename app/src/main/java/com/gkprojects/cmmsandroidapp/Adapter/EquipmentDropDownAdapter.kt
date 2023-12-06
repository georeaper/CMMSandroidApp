package com.gkprojects.cmmsandroidapp.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentListInCases
import com.gkprojects.cmmsandroidapp.R

class EquipmentDropDownAdapter(context: Context, private val equipments: List<EquipmentListInCases>)
    : ArrayAdapter<EquipmentListInCases>(context, 0, equipments) {

    private val originalList = ArrayList(equipments)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.dropdown_item, parent, false)
        val equipment = equipments[position]

        view.findViewById<TextView>(R.id.textViewEquipmentID).text = equipment.EquipmentID.toString()
        view.findViewById<TextView>(R.id.textViewSerialNumber).text = equipment.SerialNumber
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_dropdown_item, parent, false)
        val equipment = getItem(position)
//        view.findViewById<TextView>(R.id.textViewEquipmentID).text =equipment!!.EquipmentID.toString()
//        view.findViewById<TextView>(R.id.textViewSerialNumber).text = equipment!!.SerialNumber

        view.findViewById<TextView>(R.id.customDropDownTextView)
        val serialNumberTextView = view.findViewById<TextView>(R.id.customDropDownTextView)
        serialNumberTextView.text = equipment?.SerialNumber
        Log.d("AdapterContractEquipment","$serialNumberTextView")
        // Populate your custom dropdown view
        //val equipmentIDTextView = view.findViewById<TextView>(R.id.textViewEquipmentID)

        //equipmentIDTextView.text = equipment?.EquipmentID.toString()


        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val filteredList = ArrayList<EquipmentListInCases>()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(originalList)
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()

                    for (item in originalList) {
                        if (item.SerialNumber?.lowercase()?.contains(filterPattern) == true) {
                            filteredList.add(item)
                        }
                    }
                }

                results.values = filteredList
                results.count = filteredList.size

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                if (results != null && results.count > 0) {
                    addAll(results.values as List<EquipmentListInCases>)
                }
                notifyDataSetChanged()
            }

        }
    }
}