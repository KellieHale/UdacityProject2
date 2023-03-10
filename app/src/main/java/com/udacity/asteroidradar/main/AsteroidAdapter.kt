package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class AsteroidAdapter(val onItemClicked: (Asteroid) -> Unit): RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>() {

    private var asteroids: List<Asteroid> = ArrayList()

    class AsteroidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val asteroidName: TextView

        init {
            asteroidName = itemView.findViewById(R.id.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.asteroid_item, parent, false)
        return AsteroidViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroids[position]
        holder.asteroidName.text = asteroid.codename
        holder.asteroidName.setOnClickListener {
            onItemClicked(asteroids[position])
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