package com.gkprojects.cmmsandroidapp.Fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.MyAdapter
import com.gkprojects.cmmsandroidapp.Fragments.EquipmentFragment
import com.gkprojects.cmmsandroidapp.DataClasses.Customers
import com.gkprojects.cmmsandroidapp.Models.CustomersViewModel
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

private lateinit var viewModel :CustomersViewModel
private lateinit var customerRecyclerView: RecyclerView
lateinit var adapter: MyAdapter
private lateinit var search :SearchView
//private lateinit var tempList :MutableList<Customers>


class CustomerFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v=inflater.inflate(R.layout.fragment_customer, container, false)
        // Inflate the layout for this fragment
        val openbtn =v?.findViewById<FloatingActionButton>(R.id.openCustomerFragment)



        openbtn?.setOnClickListener {


            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, EditCustomerFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
             }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val counter = adapter.itemCount
        search=view.findViewById(R.id.searchView_customers)
        customerRecyclerView=view.findViewById(R.id.customer_recyclerview)
        customerRecyclerView.layoutManager=LinearLayoutManager(context)
        customerRecyclerView.setHasFixedSize(true)
        adapter=MyAdapter()

        customerRecyclerView.adapter= adapter
        viewModel=ViewModelProvider(this).get(CustomersViewModel::class.java)




       viewModel.allCustomers.observe(viewLifecycleOwner, Observer {

            adapter.updateCustomerList(it)


        })

        search.setOnQueryTextListener(object :OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val vmAll= viewModel.allCustomers.observe(viewLifecycleOwner, Observer {

                    filterSearch(it,p0)


                })
                //filterList(p0, counter)

                return true
            }

        })



        adapter.setOnClickListener(object :MyAdapter.OnClickListener {
            override fun onClick(position: Int, model: Customers) {
                val bundle =Bundle()
                bundle.putString("key",model.name)
                val fragment = EquipmentFragment()
                fragment.arguments=bundle
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.frameLayout1, EquipmentFragment())
                transaction?.addToBackStack(null)
                transaction?.commit()

            }
        })
    }



    //}




    @SuppressLint("SuspiciousIndentation")
    private fun filterSearch(filterList :List<Customers>, query :String?){
    var tempList = arrayListOf<Customers>()

        //Log.d("tag3",tempList.toString())
        if(query.isNullOrEmpty()){
            tempList.clear()
            tempList.addAll(filterList)
            adapter.updateCustomerList(tempList)

            // dont touch the data, import them as they are
            //adapter.updateCustomerList(filterList)

        }else{
            // tempList :MutableList<Customers>
            try {
                Log.d("filter3", filterList.toString())
                for(i in filterList){
                    Log.d("filter2", i.toString())

                    if(i.name?.contains(query)==true){
                        Log.d("filter4", i.name.contains(query).toString())
                        Log.d("filter8", i.toString())
                        tempList.add(i)
                         Log.d("filter9", tempList.toString())

                    }else{
                        //Toast.makeText(this.context,"failed",Toast.LENGTH_SHORT).show()

                    }
                }

            }catch (e:java.lang.Exception){
                Log.d("e_debug",e.toString())
            }

            //filter the data and import them
            adapter.updateCustomerList(tempList)
        }

    }





}