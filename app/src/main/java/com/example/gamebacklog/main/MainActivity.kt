package com.example.gamebacklog.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamebacklog.R
import com.example.gamebacklog.add.AddActivity
import com.example.gamebacklog.model.Game
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_add.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val gamesList = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(gamesList)
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var tempGame: Game

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
        if (gamesList.isNotEmpty()) {
            tempGame = gamesList.first()

            var snackBar = Snackbar.make(
                mainLayout,
                getString(R.string.delete_message, tempGame.title),
                Snackbar.LENGTH_LONG
            )
            snackBar.setAction(getString(R.string.undo)) { onUndoClick() }
            snackBar.addCallback(object : Snackbar.Callback() {

                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if (event == DISMISS_EVENT_TIMEOUT) {
                        mainActivityViewModel.deleteGame(tempGame)
                    }
                }

            })
            snackBar.show()

            gamesList.remove(tempGame)
            gameAdapter.notifyDataSetChanged()
        }
    }

    private fun onUndoClick() {
        gamesList.add(0, tempGame)
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
