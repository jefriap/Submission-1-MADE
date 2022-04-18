package com.jefriap.submission1made.core.ui.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jefriap.submission1made.core.BuildConfig
import com.jefriap.submission1made.core.R
import com.jefriap.submission1made.core.databinding.ItemRowBinding
import com.jefriap.submission1made.core.domain.model.MovieModel
import com.jefriap.submission1made.core.utils.DiffUtils
import com.jefriap.submission1made.core.utils.imageLoad
import com.jefriap.submission1made.core.utils.loadImage
import java.util.ArrayList

class MovieRvAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieRvAdapter.ListViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null

    private var listData = ArrayList<MovieModel>()

    fun setData(newListData: List<MovieModel>?) {
        if (newListData == null) return
        val diffUtilCallback = DiffUtils(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
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
            }
        }
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root) {
        //when item clicked
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition].movieId)
            }
        }

    }

}