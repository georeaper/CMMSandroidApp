package com.gkprojects.cmmsandroidapp.Fragments.TechnicalCases



import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.gkprojects.cmmsandroidapp.databinding.FragmentCasesBinding
import com.gkprojects.cmmsandroidapp.filterPopWindow
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class CasesFragment : Fragment() {
    private lateinit var casesRecyclerView: RecyclerView

    private var casesList = ArrayList<TicketCustomerName>()
    private lateinit var casesAdapter: CasesAdapter
    private lateinit var casesViewModel: CasesVM
    private lateinit var binding : FragmentCasesBinding
    private lateinit var filterWindow : filterPopWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentCasesBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val activity =requireActivity()

        val drawerLayout = activity.findViewById<DrawerLayout>(R.id.DrawLayout)

        val toolbar: MaterialToolbar = activity.findViewById(R.id.topAppBar)
        toolbar.title="Technical Cases"

        val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
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
                casesViewModel.getCustomerName(requireContext()).observe(viewLifecycleOwner,
                    Observer {list->
                    casesList.clear()
                    casesList=list as ArrayList<TicketCustomerName>
                    casesAdapter.setData(casesList)

                })

            } }

        }catch (e:Exception){
            Log.d("casesDebug",e.toString())
        }


        val searchView = view.findViewById<TextInputEditText>(R.id.searchEditTextCase)
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s != null) {
                    filterList(s.toString().lowercase(Locale.ROOT))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        val filterButton=binding.imageButtonFilterCase
        filterButton.setOnClickListener {
            filterWindow  = filterPopWindow.newInstance(
                R.layout.filter_pop_equipments
            ){filterView ->
//login that handles filtering

            }
            filterWindow.show(childFragmentManager, "FilterEquipment")


        }

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
        val filteredList= ArrayList<TicketCustomerName>()
        for (i in casesList){
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

    private fun passDataCustomer(data : TicketCustomerName){

        val bundle = Bundle()
        data.TicketID?.let { bundle.putString("id", it) }

       // bundle.putInt("customerId",data.CustomerID)



        val fragmentManager =parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val fragment = CaseInsertFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout1,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }


}