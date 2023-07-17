package com.gkprojects.cmmsandroidapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Cases
import com.gkprojects.cmmsandroidapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CaseInsertFragment : Fragment() {
    private lateinit var AppDb : CMMSDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_case_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title=view.findViewById<EditText>(R.id.et_title_case).text
        val startDate=view.findViewById<EditText>(R.id.et_case_startedDate).text
        val user=view.findViewById<AutoCompleteTextView>(R.id.et_user_case).text
        val customer=view.findViewById<AutoCompleteTextView>(R.id.et_customer_case).text
        val endDate=view.findViewById<EditText>(R.id.et_case_endedDate).text
        val statusCase=view.findViewById<Spinner>(R.id.sp_urgent_status)
        val activeCase=view.findViewById<CheckBox>(R.id.checkbox_active_case)
        val commentCase=view.findViewById<EditText>(R.id.et_cases_comments).text
        val arrAdap = this.context?.let { ArrayAdapter.createFromResource(it,R.array.status_cases,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item).also{ adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            statusCase.adapter = adapter
        } }

        val context= getActivity()?.getApplicationContext()
        //AppDb= context?.let { CMMSDatabase.getCMMSDatabase(it) }!!
        val btnsubmit : Button = view.findViewById(R.id.btn_submit_cases)
        val btnclear :Button =view.findViewById(R.id.btn_clear_cases)


        btnsubmit.setOnClickListener {
            val case = Cases(null,title.toString(),startDate.toString(),endDate.toString(),commentCase.toString(),1,1,activeCase.toString(),statusCase.toString())
            GlobalScope.launch ( Dispatchers.IO){

                AppDb.casesDAO().addCases(case)
            }
            title.clear()
            startDate.clear()
            endDate.clear()
            commentCase.clear()
            statusCase.setSelection(0)
            activeCase.isChecked = false
            user.clear()
            customer.clear()
        }

        btnclear.setOnClickListener {  title.clear()
            startDate.clear()
            endDate.clear()
            commentCase.clear()
            statusCase.setSelection(0)
            activeCase.isChecked = false
            user.clear()
            customer.clear() }


    }



}