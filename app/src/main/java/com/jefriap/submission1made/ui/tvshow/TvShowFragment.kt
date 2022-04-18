package com.jefriap.submission1made.ui.tvshow

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
import com.jefriap.submission1made.core.domain.model.TvShowModel
import com.jefriap.submission1made.core.utils.SortUtils
import com.jefriap.submission1made.databinding.FragmentTvShowBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding

    private val viewModel: TvShowViewModel by viewModel()

    private var sort = SortUtils.RANDOM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentOrientation = resources.configuration.orientation

        binding?.rvTvShow?.apply {
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
        viewModel.getTvShows(sort).observe(viewLifecycleOwner) { tvShow ->
            if (tvShow != null) {
                when (tvShow) {
                    is Resource.Loading -> {
                        binding?.loading?.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding?.loading?.visibility = View.GONE
                        binding?.rvTvShow?.visibility = View.VISIBLE
                        tvShow.data?.let {data ->
                            adapter(data)
                        }
                        Log.i("Resource_Success_TvShow", "Data: ${tvShow.data}")
                    }
                    is Resource.Error -> {
                        binding?.loading?.visibility = View.GONE
                        Toast.makeText(context, getString(R.string.terjadi_kesalahan), Toast.LENGTH_SHORT).show()
                        Log.e("Resource_Error", "Error: ${tvShow.message}")
                    }
                }
            }
        }
    }

    private fun adapter(list: List<TvShowModel>) {
        val adapter = TvShowRvAdapter(list, requireContext())
        _binding?.rvTvShow?.adapter = adapter
    }

}