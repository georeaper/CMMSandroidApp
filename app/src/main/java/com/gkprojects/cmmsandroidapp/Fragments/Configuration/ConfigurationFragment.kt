package com.gkprojects.cmmsandroidapp.Fragments.Configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.databinding.FragmentConfigurationBinding


class ConfigurationFragment : Fragment() {
    private lateinit var binding : FragmentConfigurationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentConfigurationBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnTv= binding.configurationCheckFormsTv
        btnTv.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, CheckFormsFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        binding.configurationTemplatePDFreportTv.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, TemplatesFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        binding.configurationCustomizedFieldsTv.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayout1, CustomizedFieldFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }


}