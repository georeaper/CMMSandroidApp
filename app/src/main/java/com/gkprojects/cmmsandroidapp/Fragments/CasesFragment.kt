package com.gkprojects.cmmsandroidapp.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CasesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cases, container, false)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnFloat=view.findViewById<FloatingActionButton>(R.id.openCasesFragment)
        btnFloat.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, CaseInsertFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }


    }


}