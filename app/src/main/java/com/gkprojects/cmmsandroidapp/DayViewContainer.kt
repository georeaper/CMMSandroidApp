package com.gkprojects.cmmsandroidapp

import android.view.View
import android.widget.TextView
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val textViewTV = view.findViewById<TextView>(R.id.calendarDayText)
}