package com.ohanyan.goro.sneakersshop.ui.UserList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.User

class UsersListFragment: Fragment() {

    lateinit var UsersRecycle: RecyclerView
    lateinit var auth: FirebaseAuth
    private val UserColection = Firebase.firestore.collection("User")
    private lateinit var fradapter: UsersListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.users_layout, container, false)


        auth = FirebaseAuth.getInstance()



        UsersRecycle = view.findViewById(R.id.users_list_id)



                    val query: Query =UserColection
                    val options = FirestoreRecyclerOptions.Builder<User>()
                        .setQuery(query, User::class.java)
                        .build()


                    fradapter = UsersListAdapter(requireContext(), options)

                    UsersRecycle.setHasFixedSize(true)
                    UsersRecycle.layoutManager = LinearLayoutManager(requireContext())

                    UsersRecycle.adapter = fradapter
                    fradapter.startListening()


        return view

    }

}
