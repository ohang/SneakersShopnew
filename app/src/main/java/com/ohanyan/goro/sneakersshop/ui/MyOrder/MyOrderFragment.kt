package com.ohanyan.goro.sneakersshop.ui.MyOrder

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
import com.ohanyan.goro.sneakersshop.db.Order

class MyOrderFragment:Fragment() {

    lateinit var myOrdersRec: RecyclerView
    lateinit var firebaseAuth: FirebaseAuth
    private val userCollection = Firebase.firestore.collection("User")
    private lateinit var myOrdersAdapter: MyOrdersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.myorder_layout, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        myOrdersRec = view.findViewById(R.id.myorder_list_id)

        userCollection.whereEqualTo("email", firebaseAuth.currentUser?.email).get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (doc in it) {
                    val query: Query =userCollection.document(doc.id).collection("MyOrder")
                    val options = FirestoreRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order::class.java)
                        .build()

                    myOrdersAdapter = MyOrdersAdapter(requireContext(), options)
                    myOrdersRec.setHasFixedSize(true)
                    myOrdersRec.layoutManager = LinearLayoutManager(requireContext())
                    myOrdersRec.adapter = myOrdersAdapter
                    myOrdersAdapter.startListening()
                }
            }
        }
        return view

    }

}