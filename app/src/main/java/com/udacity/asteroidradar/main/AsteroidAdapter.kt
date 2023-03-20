package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

class AsteroidAdapter(val onItemClicked: (Asteroid) -> Unit): RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>() {

    private var asteroids: List<Asteroid> = ArrayList()

    class AsteroidViewHolder(itemView: View, binding: AsteroidItemBinding) : RecyclerView.ViewHolder(itemView) {
        val asteroidName: TextView
        val asteroidDate: TextView
        val binding: AsteroidItemBinding

        private val asteroidStatus: ImageView

        init {
            this.binding = binding
            asteroidName = itemView.findViewById(R.id.name)
            asteroidDate = itemView.findViewById(R.id.date)
            asteroidStatus = itemView.findViewById(R.id.status)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<AsteroidItemBinding>(
            inflater, R.layout.asteroid_item, parent,false)
        return AsteroidViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroids[position]
        holder.asteroidName.text = asteroid.codename
        holder.asteroidDate.text = asteroid.closeApproachDate
        holder.asteroidName.setOnClickListener {
            onItemClicked(asteroids[position])
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return asteroids.size
    }
    fun setAsteroids(asteroids: List<Asteroid>) {
        this.asteroids = asteroids
        notifyDataSetChanged()
    }
}