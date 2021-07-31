package com.ohanyan.goro.sneakersshop.ui.AdminPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.Sneaker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

const val MAIN_URL:String="https://raw.githubusercontent.com/ohang/SneakersShop/master/"

class AdminPageFragment: Fragment() {

    lateinit var auth: FirebaseAuth
    val textValid: Pattern = Pattern.compile(
        "^" +

                //    "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[ա-ֆԱ-Ֆa-zA-Z0-9])" +      //any letter
                ".{3,}" +               //at least 4 characters
                "$"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()

        val view = inflater.inflate(R.layout.fragment_admin_page, container, false)
        val btToUsersList:Button = view.findViewById(R.id.users_id)
        val tvAddSneaker:TextView = view.findViewById(R.id.add_id)

        tvAddSneaker.setOnClickListener{
            addSneaker()
        }

        btToUsersList.setOnClickListener{
            findNavController().navigate(R.id.action_adminPage_to_usersListFragment)
        }

        return view
    }


    fun addSneaker(){
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.add_sneaker_layout)

        val addurl1: TextInputEditText =
            dialog.findViewById<TextInputEditText>(R.id.addurlsedit)
        val addurl2: TextInputEditText =
            dialog.findViewById<TextInputEditText>(R.id.img2)
        val addmale: TextInputEditText =
            dialog.findViewById<TextInputEditText>(R.id.addmaleedit)
        val addname: TextInputEditText =
            dialog.findViewById<TextInputEditText>(R.id.addnameedit)
        val addprice: TextInputEditText =
            dialog.findViewById<TextInputEditText>(R.id.addpriceedit)
        val addsizes: TextInputEditText =
            dialog.findViewById<TextInputEditText>(R.id.addsizesedit)

        val addButton = dialog.findViewById<Button>(R.id.addsneaker)


        addButton.setOnClickListener{
            if (validate(addurl1)&&validate(addurl2)&&validate(addmale)
                &&validate(addname)&&validate(addprice)&&validate(addsizes)){


                val n1:String= MAIN_URL +addurl1.text.toString()+".jpg"
                val n2:String= MAIN_URL +addurl2.text.toString()+".jpg"

                val urls:String= n1+","+n2
                CoroutineScope(Dispatchers.Main).launch {
                    val newsneaker= Sneaker(2,
                        addprice.text.toString(),
                        urls,
                        addsizes.text.toString(),
                        addmale.text.toString(),
                        addname.text.toString())

                    if (newsneaker.male=="Man") {
                        Firebase.firestore.collection("Sneakers/manwoman/Man").add(newsneaker)
                        dialog.hide()

                    }else{
                        Firebase.firestore.collection("Sneakers/manwoman/Woman").add(newsneaker)
                        dialog.hide()
                    }
                }
            }
        }
        dialog.show()
    }

    fun validate(text: TextInputEditText): Boolean {
        val textinput = text.text?.trim()

        if (!textinput?.isEmpty()!! && textValid.matcher(textinput).matches()) {
            return true
        } else {
            text.error = "Սխալ"
            return false
        }
    }

}