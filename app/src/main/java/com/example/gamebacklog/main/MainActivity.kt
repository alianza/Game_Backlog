package com.example.gamebacklog.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamebacklog.R
import com.example.gamebacklog.add.AddActivity
import com.example.gamebacklog.model.Game

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val gamesList = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(gamesList)
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()

        initViewModel()
    }

    private fun initViews() {
        fab.setOnClickListener { view ->
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        ibtnClear.setOnClickListener { onDeleteButtonClick() }

        // Initialize the recycler view with a linear layout manager, adapter
        rvGames.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvGames.adapter = gameAdapter
    }

    private fun onDeleteButtonClick() {
        mainActivityViewModel.deleteGame(gamesList.first())
        gameAdapter.notifyDataSetChanged()
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.games.observe(this, Observer { games ->
            if (games != null) {
                gamesList.clear()
                gamesList.addAll(games)
                gameAdapter.notifyDataSetChanged()
            }
        })
    }
}
