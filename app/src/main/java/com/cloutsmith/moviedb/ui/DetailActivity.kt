package com.cloutsmith.moviedb.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.cloutsmith.moviedb.IMAGE_BASE_URL
import com.cloutsmith.moviedb.R
import com.cloutsmith.moviedb.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailActivity : AppCompatActivity() {
    var movie: Movie? = null

    companion object {
        val MOVIE_DETAIL_EXTRA = "MOVIE_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        movie = intent.getSerializableExtra(MOVIE_DETAIL_EXTRA) as Movie

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        loadPage()
    }

    fun loadPage() {
        supportActionBar?.title = movie?.title
        movieDate.text = movie?.date
        movieTitle.text = movie?.title
        movieDescription.text = movie?.description
        Picasso.with(this)
                .load(IMAGE_BASE_URL + movie?.imageUrl)
                .placeholder(R.drawable.ic_movies)
                .into(movieIcon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
