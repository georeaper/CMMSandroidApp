package com.gkprojects.cmmsandroidapp.Adapter


import android.content.Context
import android.widget.ArrayAdapter

class DropDownAdapter(context: Context, resource: Int, data: List<Pair<String?, String?>>) : ArrayAdapter<Pair<String?, String?>>(context, resource, data) {

    // Override this method to provide custom ids for each item
    override fun getItemId(position: Int): Long {
        // Use the Int from the Pair as the id
        return getItem(position)?.first?.toLong() ?: 0
    }

    // Override this method to display the String from the Pair
    override fun getItem(position: Int): Pair<String?, String?>? {
        return super.getItem(position)
    }
}