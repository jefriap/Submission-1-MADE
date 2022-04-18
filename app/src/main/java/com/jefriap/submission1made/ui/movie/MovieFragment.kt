package com.jefriap.submission1made.ui.movie

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.jefriap.submission1made.R
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentOrientation = resources.configuration.orientation

        binding?.rvMovies?.apply {
            layoutManager = if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 4)
            } else {
                GridLayoutManager(context, 2)
            }
            setHasFixedSize(true)
            this.adapter = adapter

            getList(sort)
        }
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
                        movie.data?.let {data ->
                            adapter(data)
                        }
                        Log.i("Resource_Success_Movie", "Data: ${movie.data}")
                    }
                    is Resource.Error -> {
                        binding?.loading?.visibility = View.GONE
                        Toast.makeText(context, getString(R.string.terjadi_kesalahan), Toast.LENGTH_SHORT).show()
                        Log.e("Resource_Error", "Error: ${movie.message}")
                    }
                }
            }
        }
    }

    private fun adapter(list: List<MovieModel>) {
        val adapter = MovieRvAdapter(list, requireContext())
        _binding?.rvMovies?.adapter = adapter
    }

}