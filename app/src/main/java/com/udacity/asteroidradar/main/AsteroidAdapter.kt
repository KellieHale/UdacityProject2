package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class AsteroidAdapter(val onItemClicked: (Asteroid) -> Unit): RecyclerView.Adapter<AsteroidAdapter.Viewholder>() {

    private var asteroids: List<Asteroid> = ArrayList()

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val asteroidName: TextView

        init {
            asteroidName = itemView.findViewById(R.id.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.asteroid_item, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val asteroid = asteroids[position]
        holder.asteroidName.text = asteroid.codename
        holder.asteroidName.setOnClickListener {
            val asteroidDetails = asteroids[position]
            onItemClicked(asteroidDetails)
        }
    }

    override fun getItemCount(): Int {
        return asteroids.size
    }

    fun setAsteroids(asteroids: List<Asteroid>) {
        this.asteroids = asteroids
        notifyDataSetChanged()
    }
}