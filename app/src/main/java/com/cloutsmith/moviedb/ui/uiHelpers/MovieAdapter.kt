package com.cloutsmith.moviedb.ui.uiHelpers

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cloutsmith.moviedb.IMAGE_BASE_URL
import com.cloutsmith.moviedb.R
import com.cloutsmith.moviedb.model.Movie
import com.cloutsmith.moviedb.ui.DetailActivity
import com.cloutsmith.moviedb.ui.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycle_item.*

/**
 * Created by @ayz4sci on 28/01/2018.
 */
class MovieAdapter(val context: MainActivity,
                   val inflater: LayoutInflater,
                   val movies: ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val view = inflater.inflate(R.layout.recycle_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {
        holder?.setup(movies[position])
    }

    inner class CustomViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun setup(movie: Movie) {
            movieDate.text = movie.date
            movieName.text = movie.title
            Picasso.with(this@MovieAdapter.context)
                    .load(IMAGE_BASE_URL + movie.imageUrl)
                    .placeholder(R.drawable.ic_movies)
                    .into(movieIcon)

            containerView?.setOnClickListener({
                val intent = Intent(this@MovieAdapter.context, DetailActivity::class.java)
                intent.putExtra("MOVIE_EXTRA", movie)
                this@MovieAdapter.context.startActivity(intent)
            })
        }
    }
}