package com.ohanyan.goro.sneakersshop.ui.LoginPage

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ohanyan.goro.sneakersshop.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class LoginFragment : Fragment() {

    lateinit var datastore: DataStore<Preferences>
    lateinit var auth: FirebaseAuth



    val PASSWORD: Pattern = Pattern.compile(
        "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
              //  "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$"
    );


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val btLogin: Button
        val pbLogin:ProgressBar
        val tvReg: TextView
        val tvSkip: TextView
        val tilEmail: TextInputLayout
        val tilPassword: TextInputLayout
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        datastore = context?.createDataStore("login")!!

        tilEmail = view.findViewById(R.id.textinputemail)
        tilPassword = view.findViewById(R.id.textinputpassword)
        btLogin = view.findViewById(R.id.button)
        tvSkip = view.findViewById(R.id.skiplogin)
        tvReg = view.findViewById(R.id.registrationid)
        pbLogin=view.findViewById(R.id.progressBarid)


        tvSkip.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                findNavController().navigate(R.id.action_loginFragment_to_bottomNavFragment)
            }
        }

        btLogin.setOnClickListener {

            val etEmail = tilEmail.editText?.text?.toString()
            val etPassword = tilPassword.editText?.text?.toString()

            validateEmail(tilEmail)
            validatePassword(tilPassword)

            if (validateEmail(tilEmail) && validatePassword(tilPassword)) {
              pbLogin.visibility=View.VISIBLE
                CoroutineScope(Dispatchers.Main).launch {
                    auth.signInWithEmailAndPassword(etEmail!!,etPassword!!).addOnSuccessListener {
                        findNavController().navigate(R.id.action_loginFragment_to_bottomNavFragment)
                    }.addOnFailureListener {
                        pbLogin.visibility=View.INVISIBLE
                        Toast.makeText(context, "Սխալ Էլ Հասցե Կամ Գաղտնաբառ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        tvReg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        return view
    }

    fun validateEmail(Edit: TextInputLayout): Boolean {
        val etEmail = Edit.editText?.text?.trim()
        if (etEmail?.isNotEmpty()!! && Patterns.EMAIL_ADDRESS.matcher(etEmail).matches()) {
            return true
        } else {
            Edit.error = "Սխալ Էլ Հասցե"
            return false
        }
    }

    fun validatePassword(Pass: TextInputLayout): Boolean {
        val etPassword = Pass.editText?.text?.trim()
        if (etPassword?.isNotEmpty()!! && PASSWORD.matcher(etPassword).matches()) {
            return true
        } else {
            Pass.error = "Սխալ Գաղտնաբառ"
            return false
        }
    }

}






