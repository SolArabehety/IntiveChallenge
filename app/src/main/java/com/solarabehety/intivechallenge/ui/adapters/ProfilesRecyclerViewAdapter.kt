package com.solarabehety.intivechallenge.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.solarabehety.intivechallenge.R
import com.solarabehety.intivechallenge.model.User
import com.solarabehety.intivechallenge.utils.MaterialColorPalette
import com.squareup.picasso.Picasso


/**
 * Created by Sol Arabehety on 6/17/2018.
 */
class UsersRecyclerViewAdapter(private val onItemClickListener: View.OnClickListener) :
        RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>() {
    private var dataSet: List<User> = emptyList()

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val ivProfile: ImageView = v.findViewById(R.id.ivProfile)
        val tvCompleteName: TextView = v.findViewById(R.id.tvCompleteName)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_user, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Picasso.get().load(dataSet[position].picture).into(viewHolder.ivProfile)

        val completeName = dataSet[position].firstName.capitalize() + " " + dataSet[position].lastName.capitalize()
        viewHolder.tvCompleteName.text = completeName

        val randomColor = MaterialColorPalette.getPositionColor(position)
        viewHolder.itemView.setBackgroundColor(randomColor)

        viewHolder.itemView.tag = dataSet[position]
        viewHolder.itemView.setOnClickListener(onItemClickListener)
    }

    override fun getItemCount() = dataSet.size

    fun setData(users: List<User>) {
        dataSet = users
        notifyDataSetChanged()
    }


}