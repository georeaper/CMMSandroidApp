package com.gkprojects.cmmsandroidapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gkprojects.cmmsandroidapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ContractFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v=inflater.inflate(R.layout.fragment_contract, container, false)
        // Inflate the layout for this fragment

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnFloat=view.findViewById<FloatingActionButton>(R.id.openContractFragment)
        btnFloat.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, ContractInsertFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }


}