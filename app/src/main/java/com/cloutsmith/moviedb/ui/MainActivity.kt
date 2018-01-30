package com.cloutsmith.moviedb.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import com.cloutsmith.moviedb.R
import com.cloutsmith.moviedb.model.Movie
import com.cloutsmith.moviedb.network.NetworkUtil
import com.cloutsmith.moviedb.ui.uiHelpers.EndlessRecyclerOnScrollListener
import com.cloutsmith.moviedb.ui.uiHelpers.MovieAdapter
import com.cloutsmith.moviedb.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_internet_error.*


class MainActivity : AppCompatActivity() {
    private val movies = ArrayList<Movie>()
    private var year = "2018"
    private var page: Int = 1
    private var totalPages: Int = 0
    private var viewModel: MainActivityViewModel? = null
    private var isFreshLoad: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        initModels()
        initListeners()
    }

    private fun initUI() {
        setSupportActionBar(toolbar)

        val gridLayoutManager = GridLayoutManager(this, 2)
        recycleView.layoutManager = gridLayoutManager
        recycleView.adapter = MovieAdapter(this, LayoutInflater.from(this), movies)
    }

    private fun initModels() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel!!.getMovies().observe(this, Observer { movieResult ->
            if (movieResult != null) {
                if (isFreshLoad) {
                    movies.clear()
                    recycleView.visibility = VISIBLE
                }
//                page = movieResult.page
                totalPages = movieResult.totalPages
                movies.addAll(movieResult.movies)
                recycleView.adapter.notifyDataSetChanged()
            } else {
                showError(getString(R.string.server_error))
            }
            progressBar.visibility = GONE
        })

        viewModel!!.getErrors().observe(this, Observer { error -> showError(error!!) })
    }

    private fun initListeners() {
        retryButton.setOnClickListener {
            hideError()
            loadPage()
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    year = spinner.selectedItem as String
                    isFreshLoad = true
                    recycleView.visibility = GONE
                    loadPage()
                }
            }
        }

        recycleView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                if (page < totalPages) {
                    progressBar.visibility = VISIBLE
                    page++
                    loadPage()
                }
            }
        })
    }

    private fun loadPage() {
        if (NetworkUtil.isConnected(this)) {
            progressBar.visibility = VISIBLE
            viewModel!!.loadMovies(page, year)
        } else {
            showError()
        }
    }

    private fun showError(message: String = getString(R.string.internet_connection_error)) {
        errorTextView.text = message
        errorLayout.visibility = VISIBLE
    }

    private fun hideError() {
        errorLayout.visibility = GONE
    }

}
