package com.example.gamebacklog.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gamebacklog.data.GameRepository
import com.example.gamebacklog.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val games = gameRepository.getGames()

    fun deleteGame(game: Game) {
        mainScope.launch {
            gameRepository.deleteGame(game)
        }
    }

    fun insertGame(game: Game) {
        mainScope.launch {
            gameRepository.insertGame(game)
        }
    }
}