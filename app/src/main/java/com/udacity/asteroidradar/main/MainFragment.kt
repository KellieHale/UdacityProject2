package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var adapter: AsteroidAdapter

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val mainViewModel = MainViewModel()
        mainViewModel.asteroids.observe(viewLifecycleOwner) { asteroids ->
            adapter.setAsteroids(asteroids)
        }
        mainViewModel.photo.observe(viewLifecycleOwner) { photoOfDay ->
            Picasso
                .with(requireContext())
                .load(photoOfDay.imgSrcUrl)
                .placeholder(R.drawable.placeholder_picture_of_day)
                .into(binding.activityMainImageOfTheDay)
        }
        mainViewModel.getAsteroids()
        mainViewModel.getPictureOfDay()


        setHasOptionsMenu(true)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.asteroid_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AsteroidAdapter(onItemClicked = {asteroid ->
            viewAsteroid(asteroid)
        })
        recyclerView.adapter = adapter
        addDivider(recyclerView)
    }
    private fun addDivider(recyclerView: RecyclerView) {
        val verticalDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        val verticalDivider =
            ContextCompat.getDrawable(requireActivity(), R.drawable.vertical_divider)
        verticalDecoration.setDrawable(verticalDivider!!)
        recyclerView.addItemDecoration(verticalDecoration)
    }

    private fun viewAsteroid(asteroid: Asteroid) {
        val bundle = bundleOf("asteroid" to asteroid )
        findNavController().navigate(R.id.action_showDetail, bundle)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("true"))
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }


}
