package com.ohanyan.goro.sneakersshop.ui.UserList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.ohanyan.goro.sneakersshop.R
import com.ohanyan.goro.sneakersshop.db.User

class UsersListAdapter(
    var context: Context, options: FirestoreRecyclerOptions<User>
) : FirestoreRecyclerAdapter<User, UsersListAdapter.UserViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_item,
                parent,
                false
            )
        )
    }


    inner class UserViewHolder(
        itemview: View
    ) : RecyclerView.ViewHolder(itemview) {

        fun bind(item: User) {
            val name: TextView
            val surname:TextView
            val adress:TextView
            val email: TextView
            val phone:TextView
            val postcode:TextView


            name=itemView.findViewById(R.id.username_id)
            surname=itemView.findViewById(R.id.surname_id)
            adress=itemView.findViewById(R.id.useradress_id)
            email=itemView.findViewById(R.id.useremail_id)
            phone=itemView.findViewById(R.id.userphone_id)
            postcode=itemView.findViewById(R.id.userpost_id)

            name.text=item.name
            surname.text=item.surname
            adress.text=item.adress
            email.text=item.email
            phone.text=item.phonenumber
            postcode.text=item.postcode




        }

    }


    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int,
        model: User
    ) {
        holder.bind(model)

    }


}





