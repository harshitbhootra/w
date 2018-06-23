package com.visiontutor.app.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.visiontutor.app.R


class SearchTutorResultAdapter(val data: ArrayList<String>) : RecyclerView.Adapter<SearchTutorResultAdapter.SearchTutorResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTutorResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.search_tutor_view, parent, false)
        return SearchTutorResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SearchTutorResultViewHolder, position: Int) {
        val d = data[position]
        holder.name.text = d
        holder.exp.text = d
        holder.gender.text = d
    }

    class SearchTutorResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tutor_name)
        val exp = itemView.findViewById<TextView>(R.id.tutor_exp)
        val gender = itemView.findViewById<TextView>(R.id.tutor_gender)
    }
}