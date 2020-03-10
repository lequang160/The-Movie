package com.vtvhyundai.media2359demo.ui.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.vtvhyundai.media2359demo.BuildConfig
import com.vtvhyundai.media2359demo.R
import com.vtvhyundai.media2359demo.di.modules.GlideApp
import com.vtvhyundai.media2359demo.extensions.inflate
import com.vtvhyundai.media2359demo.models.MovieModel

class NowPlayingAdapter :
    RecyclerView.Adapter<MovieViewHolder>() {
    val data: MutableList<MovieModel> = mutableListOf()
    lateinit var mContex: Context
    var isLoading: Boolean = false
    var onItemClickListener: ((adapter: RecyclerView.Adapter<*>, position: Int) -> Unit)? = null
    var onLoadMoreListener: ((adapter: RecyclerView.Adapter<*>, isLoading: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return when (viewType) {
            VIEW_TYPE_IS_ITEM -> {
                val inflatedView = parent.inflate(R.layout.item_movie, false)
                MovieViewHolder.ItemMovieViewHolder(inflatedView)
            }

            else -> {
                val inflatedView = parent.inflate(R.layout.item_empty, false)
                MovieViewHolder.EmptyView(inflatedView)
            }
        }


    }

    override fun getItemCount(): Int {
        return when (data.isEmpty()) {
            true -> 1
            else -> data.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data.isNullOrEmpty()) {
            true -> VIEW_TYPE_IS_EMPTY_VIEW
            false -> VIEW_TYPE_IS_ITEM
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if(position == (data.size - 1)){
            if(!isLoading){
                isLoading = true
                onLoadMoreListener?.invoke(this, isLoading)
            }
        }
        when(holder){
            is MovieViewHolder.ItemMovieViewHolder -> {
                GlideApp.with(mContex).load(BuildConfig.BASE_IMAGE_URL + data[position].posterPath).into(holder.posterImg)
                holder.voteBtn.setText(data[position].voteAverage)
                holder.onItemClick {
                    onItemClickListener?.invoke(this, position)
                }
            }
            is MovieViewHolder.EmptyView -> {

            }
        }
    }


    fun addNewData(newData: List<MovieModel>) {
        this.data.addAll(newData as MutableList<MovieModel>)
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mContex = recyclerView.context
    }

    companion object{
        const val VIEW_TYPE_IS_ITEM = 0
        const val VIEW_TYPE_IS_EMPTY_VIEW = 1
    }
}

sealed class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class EmptyView(view: View) : MovieViewHolder(view) {

    }

    class ItemMovieViewHolder(val view: View) : MovieViewHolder(view) {
        val posterImg = view.findViewById<AppCompatImageView>(R.id.item_movie_poster_img)
        val voteBtn = view.findViewById<AppCompatTextView>(R.id.item_movie_vote_btn)
        fun onItemClick(listener: () -> Unit){
            view.setOnClickListener {
                listener.invoke()
            }
        }
    }
}