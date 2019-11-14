package com.example.gamebacklog.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gamebacklog.model.Game
import com.example.gamebacklog.data.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    fun insertGame(game: Game) {
        mainScope.launch {
            gameRepository.insertGame(game)
        }
    }
}