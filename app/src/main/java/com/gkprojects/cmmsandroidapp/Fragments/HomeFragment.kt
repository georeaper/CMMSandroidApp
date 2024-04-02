package com.gkprojects.cmmsandroidapp.Fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageButton

import android.widget.TextView

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.gkprojects.cmmsandroidapp.Adapter.MainOverviewAdapter
import com.gkprojects.cmmsandroidapp.AppData
import com.gkprojects.cmmsandroidapp.DataClasses.EventItem

import com.gkprojects.cmmsandroidapp.DataClasses.OverviewMainData
import com.gkprojects.cmmsandroidapp.DataClasses.TicketCalendar

import com.gkprojects.cmmsandroidapp.DayViewContainer
import com.gkprojects.cmmsandroidapp.Fragments.Contracts.ContractsVM
import com.gkprojects.cmmsandroidapp.Models.CasesVM

import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle

import java.util.Locale
import kotlin.Exception



class HomeFragment : Fragment() {

    private lateinit var contractsViewModel: ContractsVM
    private lateinit var ticketsViewModel :CasesVM
    private lateinit var workOrdersRecyclerView: RecyclerView
    private var templist = ArrayList<OverviewMainData>()
    private lateinit var adapterHomeWorkOrder: MainOverviewAdapter
    private lateinit var calendarView: com.kizitonwose.calendar.view.CalendarView
    private lateinit var tvCurrentMonth: TextView
    private var currentMonth = YearMonth.now()
    private var calendarEvents: List<TicketCalendar> = emptyList()
    private var ticketTypes =ArrayList<EventItem>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId=R.id.action_home

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        super.onResume()
        val activity =requireActivity()

        val drawerLayout = activity.findViewById<DrawerLayout>(R.id.DrawLayout)
        val navView: NavigationView = activity.findViewById(R.id.navView)
        val toolbar: MaterialToolbar = activity.findViewById(R.id.topAppBar)
        toolbar.title="Home"


        val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        Log.d("testAppData","${AppData.userId}")

        templist.clear()
       val currentDateTime = LocalDateTime.now()
//
       val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
       val today = currentDateTime.format(formatter)


        calendarView = view.findViewById(R.id.kiziCalendar)
        tvCurrentMonth=view.findViewById(R.id.tvHomeKiziCalendar)
        val tvGreeting =view.findViewById<TextView>(R.id.tv_greeting_view_top)
        tvGreeting.text= today
        currentMonth = YearMonth.now()
        ticketsViewModel= ViewModelProvider(this)[CasesVM::class.java]
        //ticketsViewModel= ViewModelProvider(this)[CasesVM::class.java]

        val firstMonth = currentMonth.minusMonths(360) //20years
        val lastMonth = currentMonth.plusMonths(360)
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.SUNDAY)
        val titlesContainer = view.findViewById<ViewGroup>(R.id.titlesContainer)
        titlesContainer.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                textView.text = title
            }
        calendarView.setup(firstMonth, lastMonth, daysOfWeek.first())
        calendarView.scrollToMonth(currentMonth)



        val btnPrevious = view.findViewById<ImageButton>(R.id.btnHomeNavigationPrevious)
        val btnNext = view.findViewById<ImageButton>(R.id.btnHomeNavigationNext)


        updateCalendar()

        btnPrevious.setOnClickListener {
            // Subtract one month
            currentMonth=currentMonth.minusMonths(1)
            updateCalendar()
        }

        btnNext.setOnClickListener {
            // Add one month
            currentMonth=currentMonth.plusMonths(1)
            updateCalendar()
        }

        bindEventsToCalendar(calendarView)


        workOrdersRecyclerView =view.findViewById(R.id.recyclerViewHomeWorkOrder)
        val btnCalendar =view.findViewById<ImageButton>(R.id.img_calendar_view)
        val btnContent =view.findViewById<ImageButton>(R.id.img_content_item_view)

        btnContent.setOnClickListener {
            when(workOrdersRecyclerView.visibility){
                View.GONE->{
                    workOrdersRecyclerView.visibility=View.VISIBLE
                    btnContent.setImageResource(R.drawable.remove_expandable_icon)
                }
                View.VISIBLE->{
                    workOrdersRecyclerView.visibility=View.GONE
                    btnContent.setImageResource(R.drawable.add_expandable_icon)
                }
            }
        }
        adapterHomeWorkOrder= context?.let { MainOverviewAdapter(it,ArrayList<OverviewMainData>()) }!!
        contractsViewModel = ViewModelProvider(this)[ContractsVM::class.java]
        ticketsViewModel = ViewModelProvider(this)[CasesVM::class.java]

        try {
            context?.let {
                ticketsViewModel.getOverviewData(it).observe(viewLifecycleOwner,Observer{it->
                    for (i in it.indices){
                        templist.add(it[i])

                    }
                    setdatatoRv(workOrdersRecyclerView,adapterHomeWorkOrder,templist)
                })
            }

        }catch (e:Exception){
            Log.d("ticketOverview",e.toString())
        }

    }



    private fun updateCalendar() {
        // Update your calendar view and text view here
        tvCurrentMonth.text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
        // Setup or update CalendarView with the new month
        calendarView.setup(currentMonth.minusMonths(360), currentMonth.plusMonths(360), DayOfWeek.SUNDAY)
        calendarView.scrollToMonth(currentMonth)

    }
    private fun setdatatoRv( recyclerview : RecyclerView , adapterRv : MainOverviewAdapter,  input :ArrayList<OverviewMainData>){
        Log.d("debugInput",input.toString())
        recyclerview.apply { setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = adapterRv }
        adapterRv.setData(input)

    }

    fun bindEventsToCalendar(calendarView: com.kizitonwose.calendar.view.CalendarView) {

        val dataFromJson=AppDataLoader(requireContext())
        ticketTypes =dataFromJson.getDataColorsFromJson("caseUrgency.json")
        Log.d("typesEvents","$ticketTypes")

        try {
            ticketsViewModel.getInformationForCalendar(requireContext()).observe(viewLifecycleOwner,
                Observer {events->
                    calendarEvents=events
                    calendarView.notifyCalendarChanged()
                    calendarEvents.forEach{
                        event->
                        Log.d("events","${event.DateStart} , ${LocalDate.now()}")

                    }

                })
        }catch (e:Exception){
            Log.d("CalendarTickets","$e")
        }


        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textViewTV.text = day.date.dayOfMonth.toString()
                //container.textViewTV.setTextColor(Color.BLACK)
                val drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.circle_background, null)
                container.textViewTV.text = day.date.dayOfMonth.toString()
                Log.d("setUpCalendar","${day.date.dayOfMonth}")
                Log.d("setUpCalendar2","${day.date.month}")
                if (day.date.month == currentMonth.month) {
                    container.textViewTV.setTextColor(Color.BLACK) // Change this to your desired color for the current month
                } else {
                    container.textViewTV.setTextColor(Color.LTGRAY) // Change this to your desired color for other months
                }


                // Default no events
                if(day.date==LocalDate.now()){
                    container.textViewTV.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
                }

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                calendarEvents.forEach { event ->
                    event.DateStart?.let { dateStartString ->
                        try {
                            val eventDate = LocalDate.parse(dateStartString, dateFormatter)

                            // Assuming day.date is already a LocalDate, compare directly
                            if (eventDate == day.date) {

                                val matchingItem = ticketTypes.firstOrNull { it.type == event.Urgency }
                                matchingItem?.let { item ->
                                    // Use the color from the matching item
                                    val colorInt = item.color.toInt()
                                    container.textViewTV.backgroundTintList = ColorStateList.valueOf(colorInt)
//                                    container.textViewTV.backgroundTintList = ColorStateList.valueOf(item.color)
                                    Log.d("MatchedEvent", "Event on ${eventDate} matches day ${day.date} with urgency ${event.Urgency}")
                                }
                            }
                        } catch (e: DateTimeParseException) {
                            // Handle the case where the date string is not in the expected format
                            Log.e("DateParseError", "Failed to parse date: $dateStartString", e)
                        }
                    }
                }

            }
        }
    }




}