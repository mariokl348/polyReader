package com.company.epubreader.ui.register.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.company.epubreader.R
import kotlinx.android.synthetic.main.fragment_register_main.*


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterMainFragment : Fragment(R.layout.fragment_register_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerBtn.setOnClickListener {

            findNavController().navigate(R.id.toRegister)
        }


        loginBtn.setOnClickListener {
            findNavController().navigate(R.id.toLogin)
        }
    }



}