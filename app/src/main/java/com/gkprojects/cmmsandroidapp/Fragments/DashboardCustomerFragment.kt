package com.gkprojects.cmmsandroidapp.Fragments

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReports
import com.gkprojects.cmmsandroidapp.R


class DashboardCustomerFragment : Fragment() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_customer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args =this.arguments
        val CustomerId= args!!.getInt("id")
        val CustomerName = args.getString("name")
        val Address = args.getString("Address")
        val Phone = args.getString("Phone")
        val Email = args.getString("email")

        val tvCustomer :TextView = view.findViewById(R.id.textviewCustomerNameDashboardCustomer)
        val tvEmail :TextView = view.findViewById(R.id.textviewCustomerNameDashboardEmail)
        val tvAddress :TextView = view.findViewById(R.id.textviewCustomerNameDashboardAddress)
        val tvPhone :TextView = view.findViewById(R.id.textviewCustomerNameDashboardPhone)

        tvCustomer.text=CustomerName
        tvAddress.text=Address
        tvEmail.text=Email
        tvPhone.text=Phone

        val customerInformation : ImageButton= view.findViewById(R.id.ibDashboardGeneralInformationImageButton)
        val assetBtn : ImageButton =view.findViewById(R.id.imageDashboardButonAssets)
        val workOrderBtn : ImageButton =view.findViewById(R.id.imageDashboardButonWorkOrders)
        val contractBtn : ImageButton =view.findViewById(R.id.imageDashboardButonContracts)
        val technicalCaseBtn : ImageButton =view.findViewById(R.id.imageDashboardButonTechnicalCases)
        val notesBtn : ImageButton =view.findViewById(R.id.imageDashboardButonNotes)

        customerInformation.setOnClickListener {
            sentIDtoFragments(CustomerId,EditCustomerFragment())
        }




    }

    private fun sentIDtoFragments(id: Int ,fragment :Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putInt("id",id)
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout1, fragment)
        fragmentTransaction.commit()

    }


}