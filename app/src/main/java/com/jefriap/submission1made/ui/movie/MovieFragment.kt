package com.jefriap.submission1made.ui.movie

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.jefriap.submission1made.core.data.Resource
import com.jefriap.submission1made.core.domain.model.MovieModel
import com.jefriap.submission1made.core.utils.SortUtils
import com.jefriap.submission1made.databinding.FragmentMovieBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding

    private val viewModel: MovieViewModel by viewModel()

    private var sort = SortUtils.RANDOM

    private lateinit var rvAdapter: MovieRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAdapter = MovieRvAdapter()

        val currentOrientation = resources.configuration.orientation

        binding?.rvMovies?.apply {
            layoutManager = if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 4)
            } else {
                GridLayoutManager(context, 2)
            }
            setHasFixedSize(true)
            adapter = rvAdapter

        }

        getList(sort)
    }

    private fun getList(sort: String) {
        viewModel.getMovies(sort).observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> {
                        binding?.loading?.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding?.loading?.visibility = View.GONE
                        rvAdapter.setData(movie.data)
                        Log.i("Success_Resource", "Data: ${movie.data}")
                    }
                    is Resource.Error -> {
                        binding?.loading?.visibility = View.GONE
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        Log.e("Error_Resource", "Error: ${movie.message}")
                    }
                }
            }
        }
    }

}