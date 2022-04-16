package com.jefriap.submission1made.ui.movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jefriap.submission1made.BuildConfig
import com.jefriap.submission1made.R
import com.jefriap.submission1made.core.domain.model.MovieModel
import com.jefriap.submission1made.databinding.ItemRowBinding
import com.jefriap.submission1made.ui.detail.DetailActivity
import com.jefriap.submission1made.utils.imageLoad
import com.jefriap.submission1made.utils.loadImage
import com.submission.filmcatalogue.data.local.entity.MovieEntity

class MovieRvAdapter(private val dataMovies: List<MovieModel>, private val context: Context) : RecyclerView.Adapter<MovieRvAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = dataMovies[position]
        with(holder.binding) {
            with(data) {
                //image
                if (poster.isNullOrEmpty()) {
                    imgMovie.imageLoad(R.drawable.img_not_found)
                } else imgMovie.loadImage(BuildConfig.IMAGE_URL + poster)
                //title
                tvJudulFilm.text = title
                //genre
                tvGenreFilm.text = overview
                //release date
                if (releaseDate == "") {
                    tvDate.textSize = 9f
                    tvDate.text = context.getString(R.string.blm_tersedia)
                } else tvDate.text = releaseDate
                //rating
                if (rating == 0.0) {
                    tvRating.textSize = 9f
                    tvRating.text = context.getString(R.string.blm_tersedia)
                } else tvRating.text = rating.toString()
                //when item clicked
                holder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ID, movieId)
                        putExtra(DetailActivity.TYPE, "movie")
                    }
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = dataMovies.size

    class ListViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

}