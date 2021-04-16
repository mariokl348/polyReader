package com.company.epubreader.ui.register.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.company.epubreader.R
import com.company.epubreader.ui.Constants
import com.company.epubreader.ui.main.MainActivity
import com.company.epubreader.utils.App
import com.vishnusivadas.advanced_httpurlconnection.PutData
import kotlinx.android.synthetic.main.fragment_login.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = App.prefs

        if (pref?.rememberMe!!) {
            userNameInput.editText?.setText(pref.username)
            passwordNameInput.editText?.setText(pref.password)
        }



        rememberCheckBox.setOnCheckedChangeListener { compoundButton, b ->

            pref.rememberMe = b

        }

        loginBtn.setOnClickListener {
            if (pref.rememberMe) {
                pref.username = userNameInput.editText?.text.toString()
                pref.password = passwordNameInput.editText?.text.toString()
            } else {
                pref.username = ""
                pref.password = ""
            }

            setUpEnvironment()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setUpEnvironment() {
        val userName = userNameInput.editText?.text.toString()
        val password = passwordNameInput.editText?.text.toString()
        if (userName.isNotEmpty() && password.isNotEmpty()) {

            progressBarLogin.isVisible = true

            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {

                val field = arrayOfNulls<String>(2)
                field[0] = "username"
                field[1] = "password"
                val data = arrayOfNulls<String>(2)
                data[0] = userName
                data[1] = password

                val putData = PutData(
                    Constants.BASE_URL +"login.php",
                    "POST",
                    field,
                    data
                )

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBarLogin.isVisible = false
                        val result = putData.result
                        if (result.equals("Login Success")) {
                            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()

                        } else {
                            Log.i("asjdhvw", "" + result)
                            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
                        }

                    }
                }


            }

        } else {
            Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
        }
    }
}