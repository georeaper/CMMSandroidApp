package com.gkprojects.cmmsandroidapp.Fragments



import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.CasesAdapter
import com.gkprojects.cmmsandroidapp.DataClasses.TicketCustomerName
import com.gkprojects.cmmsandroidapp.Models.CasesVM
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class CasesFragment : Fragment() {
    private lateinit var casesRecyclerView: RecyclerView

    private var templist = ArrayList<TicketCustomerName>()
    private lateinit var casesAdapter: CasesAdapter
    private lateinit var casesViewModel: CasesVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
//        bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(requireContext(), R.drawable.bottom_nav_item_selector)
//        bottomNavigationView.itemTextColor = ContextCompat.getColorStateList(requireContext(), R.drawable.bottom_nav_item_selector)
//

        
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cases, container, false)
    }

    override fun onResume() {
        super.onResume()
        var activity =requireActivity()

        var drawerLayout = activity.findViewById<DrawerLayout>(R.id.DrawLayout)
        val navView: NavigationView = activity.findViewById(R.id.navView)
        val toolbar: MaterialToolbar = activity.findViewById(R.id.topAppBar)
        toolbar.title="Technical Cases"

        var toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    @SuppressLint("SuspiciousIndentation", "UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        casesRecyclerView = view.findViewById(R.id.cases_recyclerview)
        casesAdapter = this.context?.let { CasesAdapter(it, ArrayList<TicketCustomerName>()) }!!

        casesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = casesAdapter
        }
        casesViewModel = ViewModelProvider(this)[CasesVM::class.java]


        try{
            lifecycleScope.launch { withContext(Dispatchers.Main){
                casesViewModel.getCustomerName(context!!).observe(viewLifecycleOwner, Observer {

                    casesAdapter.setData(it as ArrayList<TicketCustomerName>)
                    templist.clear()
                    for(i in it.indices){
                        templist.add(it[i])


                    }
                    Log.d("casesItems", templist.toString())
                })

            } }

        }catch (e:Exception){
            Log.d("casesDebug",e.toString())
        }


        val searchView = view.findViewById<SearchView>(R.id.searchView_cases)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    filterList(p0.lowercase(Locale.ROOT))
                }
                return true
            }
        })

        casesAdapter.setOnClickListener(object : CasesAdapter.OnClickListener {
            override fun onClick(position: Int, model: TicketCustomerName) {

                passDataCustomer(model)

            }
        })


        val btnFloat = view.findViewById<FloatingActionButton>(R.id.openCasesFragment)
        btnFloat.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, CaseInsertFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

    }



    private fun filterList(query:String){
        Log.d("query",query)
        if (query!=null){
            val filteredList= ArrayList<TicketCustomerName>()
            for (i in templist){
                if ((i.Title?.lowercase(Locale.ROOT)?.contains(query) == true)or(i.CustomerName?.lowercase(Locale.ROOT)?.contains(query) == true) )
                    filteredList.add(i)
                Log.d("filteredCases", filteredList.toString())
            }
            if (filteredList.isEmpty() ){
                Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

            }else{
                casesAdapter.setData(filteredList)
            }
        }


    }

    private fun passDataCustomer(data : TicketCustomerName){

        val bundle = Bundle()
        data.TicketID?.let { bundle.putInt("id", it.toInt()) }

       // bundle.putInt("customerId",data.CustomerID)



        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment =CaseInsertFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }


}