package com.jefriap.submission1made.ui.tvshow

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jefriap.submission1made.BuildConfig
import com.jefriap.submission1made.R
import com.jefriap.submission1made.core.domain.model.TvShowModel
import com.jefriap.submission1made.databinding.ItemRowBinding
import com.jefriap.submission1made.ui.detail.DetailActivity
import com.jefriap.submission1made.utils.imageLoad
import com.jefriap.submission1made.utils.loadImage
import com.submission.filmcatalogue.data.local.entity.TvShowEntity

class TvShowRvAdapter(private val dataTvShows: List<TvShowModel>, private val context: Context) : RecyclerView.Adapter<TvShowRvAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = dataTvShows[position]
        with(holder.binding) {
            with(data) {
                //image
                if (poster.isNullOrEmpty()) {
                    imgMovie.imageLoad(R.drawable.img_not_found)
                } else imgMovie.loadImage(BuildConfig.IMAGE_URL + poster)
                //title
                tvJudulFilm.text = name
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
                        putExtra(DetailActivity.EXTRA_ID, tvShowId)
                        putExtra(DetailActivity.TYPE, "tv")
                    }
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = dataTvShows.size

    class ListViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)
}