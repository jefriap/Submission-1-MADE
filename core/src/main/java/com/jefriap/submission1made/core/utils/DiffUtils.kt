package com.jefriap.submission1made.core.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.jefriap.submission1made.core.domain.model.MovieModel

class DiffUtils(private val oldList: List<MovieModel>, private val newList: List<MovieModel>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].movieId == newList[newItemPosition].movieId
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (title,
            overview,
            rating,
            releaseDate,
            poster,
            backdrop,
            favorite) = oldList[oldPosition]
        val (title1,
            overview1,
            rating1,
            releaseDate1,
            poster1,
            backdrop1,
            favorite1) = newList[newPosition]

        return title == title1
                && overview == overview1
                && rating == rating1
                && releaseDate == releaseDate1
                && poster == poster1
                && backdrop == backdrop1
                && favorite == favorite1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}