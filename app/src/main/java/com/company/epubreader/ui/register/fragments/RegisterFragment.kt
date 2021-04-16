package com.company.epubreader.ui.register.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.company.epubreader.R
import com.company.epubreader.ui.Constants
import com.vishnusivadas.advanced_httpurlconnection.PutData
import kotlinx.android.synthetic.main.fragment_register.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment(R.layout.fragment_register) {
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

        signUpBtn.setOnClickListener {
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
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun setUpEnvironment() {


        val fullName = displayNameInput.editText?.text.toString()
        val userName = userNameInput.editText?.text.toString()
        val password = passwordNameInput.editText?.text.toString()
        val email = emailNameInput.editText?.text.toString()


        if (fullName.isNotEmpty() && userName.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {

            progressBar.isVisible = true
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {


                val field = arrayOfNulls<String>(4)
                field[0] = "fullname"
                field[1] = "username"
                field[2] = "password"
                field[3] = "email"
                val data = arrayOfNulls<String>(4)
                data[0] = fullName
                data[1] = userName
                data[2] = password
                data[3] = email


                val putData = PutData(
                    Constants.BASE_URL + "signup.php",
                    "POST",
                    field,
                    data
                )

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBar.isVisible = false
                        val result = putData.result
                        if (result.equals("Sign Up Success")) {
                            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()

                            // go to login
                            findNavController().navigate(R.id.toLoginFromRegister)

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