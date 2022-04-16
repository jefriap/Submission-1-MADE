package com.jefriap.submission1made.ui.movie

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jefriap.submission1made.BuildConfig
import com.jefriap.submission1made.core.domain.model.MovieModel
import com.jefriap.submission1made.core.utils.DiffUtils
import com.jefriap.submission1made.databinding.ItemRowBinding
import com.jefriap.submission1made.ui.detail.DetailActivity
import com.jefriap.submission1made.utils.loadImage
import com.submission.filmcatalogue.data.local.entity.MovieEntity

class MovieRvAdapter() : RecyclerView.Adapter<MovieRvAdapter.ListViewHolder>() {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var dataMovies = ArrayList<MovieModel>()


    fun setData(newListData: List<MovieModel>?) {
        if (newListData == null) return
        val diffUtilCallback = DiffUtils(dataMovies, newListData)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        dataMovies.clear()
        dataMovies.addAll(newListData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = dataMovies[position]
        with(holder.binding) {
            imgMovie.loadImage(BuildConfig.IMAGE_URL + data.poster)
            tvJudulFilm.text = data.title
            tvGenreFilm.text = data.overview
            tvDate.text = data.releaseDate
            tvRating.text = data.rating.toString()
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, data.movieId)
                    putExtra(DetailActivity.TYPE, "movie")
                }
                holder.itemView.context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int = dataMovies.size

    class ListViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

}