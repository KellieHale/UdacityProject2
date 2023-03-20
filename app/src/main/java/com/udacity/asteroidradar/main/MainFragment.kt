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
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.work.RefreshDataWorker
import kotlinx.coroutines.*

class MainFragment : Fragment() {

    private lateinit var adapter: AsteroidAdapter

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

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

    override fun onResume() {
        super.onResume()

        viewModel.asteroids.observe(viewLifecycleOwner) { asteroids ->
            adapter.setAsteroids(asteroids)
        }
        viewModel.photo.observe(viewLifecycleOwner) { photoOfDay ->
            Picasso
                .with(requireContext())
                .load(photoOfDay.imgSrcUrl)
                .into(binding.activityMainImageOfTheDay)
        }
        viewModel.updateUi(requireContext())

        WorkManager.getInstance(requireContext().applicationContext)
            .getWorkInfosForUniqueWorkLiveData(RefreshDataWorker.WORK_NAME).observe(viewLifecycleOwner) { infos ->
                if (infos.size > 0) {
                    val state = infos[0].state
                    if (state == WorkInfo.State.ENQUEUED) {
                        viewModel.getFilteredAsteroids(requireContext())
                    }
                }
            }
    }

    private fun addDivider(recyclerView: RecyclerView) {
        val verticalDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL)
        val verticalDivider =
            ContextCompat.getDrawable(requireActivity(), R.drawable.vertical_divider)
        verticalDecoration.setDrawable(verticalDivider!!)
        recyclerView.addItemDecoration(verticalDecoration)
    }

    private fun viewAsteroid(asteroid: Asteroid) {
        val bundle = bundleOf("selectedAsteroid" to asteroid )
        findNavController().navigate(R.id.action_showDetail, bundle)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("true"))
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId){
                R.id.show_all_menu -> AsteroidApiFilter.SHOW_SAVED
                R.id.show_week_menu -> AsteroidApiFilter.SHOW_WEEK
                else -> AsteroidApiFilter.SHOW_TODAY
            })
        viewModel.getFilteredAsteroids(requireContext())
        return true
    }
}
