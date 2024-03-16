package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.gkprojects.cmmsandroidapp.DataClasses.Tools
import com.gkprojects.cmmsandroidapp.databinding.DropdownAdapterToolsWorkOrdersBinding
import java.util.Locale

class ArrayAdapterDropDownTools(context: Context, resource: Int, private val toolsList: List<Tools>) :
    ArrayAdapter<Tools>(context, resource, toolsList) {
    private lateinit var binding: DropdownAdapterToolsWorkOrdersBinding
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        binding = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            DropdownAdapterToolsWorkOrdersBinding.inflate(inflater, parent, false)
        } else {
            DropdownAdapterToolsWorkOrdersBinding.bind(convertView)
        }

        val tool = toolsList[position]

        binding.dropdownAdapterToolsWorkOrdersTitle .text = tool.Title
        binding.dropdownAdapterToolsCalDate.text = tool.CalibrationDate
        // ... set other fields as needed

        return binding.root
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrBlank()) {
                    toolsList
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                    toolsList.filter { tool ->
                        tool.Title?.toLowerCase(Locale.ROOT)?.contains(filterPattern) == true ||
                                tool.Description?.toLowerCase(Locale.ROOT)?.contains(filterPattern) == true
                                //tool.AnotherField?.toLowerCase()?.contains(filterPattern) == true
                        // Add more fields as needed
                    }
                }

                return FilterResults().apply { values = filteredList }
            }
            override fun convertResultToString(resultValue: Any?): CharSequence {
                return if (resultValue is Tools) {
                    "${resultValue.Title} - ${resultValue.CalibrationDate}"
                    // Modify this string to include the fields you want
                } else {
                    super.convertResultToString(resultValue)
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                addAll(results?.values as List<Tools>)
                notifyDataSetChanged()

                // Log the filtered values
                Log.d("Filter", "Filtered values: ${results.values}")
            }
        }
    }

}

