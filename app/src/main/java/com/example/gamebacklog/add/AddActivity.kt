package com.example.gamebacklog.add

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.gamebacklog.R
import com.example.gamebacklog.main.MainActivityViewModel
import com.example.gamebacklog.model.Game

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.util.*

class AddActivity : AppCompatActivity() {

    private var releaseDate: Date? = null
    private lateinit var addActivityViewModel: AddActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        initViews()

        initViewModel()
    }

    private fun initViewModel() {
        addActivityViewModel = ViewModelProviders.of(this).get(AddActivityViewModel::class.java)
    }

    private fun initViews() {
        fab.setOnClickListener { onSaveButtonClicked() }

        etDate.setOnClickListener { onEditDateClicked() }

        etDate.setOnFocusChangeListener { v: View?, hasFocus: Boolean -> if (hasFocus) { onEditDateClicked() } }
    }

    private fun onSaveButtonClicked() {
        var errorMessage = "Please fill in:"
        var valid = true

        if (etTitle.text.isNullOrEmpty()) {
            errorMessage += " Title"
            valid = false
        }

        if (etPlatform.text.isNullOrEmpty()) {
            errorMessage += " Platform"
            valid = false
        }

        if (releaseDate == null) {
            errorMessage += " Release Date"
            valid = false
        }

        if (valid) {
            val game = Game(null, etTitle.text.toString(), etPlatform.text.toString(), releaseDate!!)
            println(game)
            addActivityViewModel.insertGame(game)
            Snackbar.make(addConstraintLayout, "Added game: ${game.title}", Snackbar.LENGTH_LONG).show()
            clearInputs()
        } else {
            Snackbar.make(addConstraintLayout, errorMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearInputs() {
        etTitle.text?.clear()
        etPlatform.text?.clear()
        etDate.text?.clear()
    }

    private fun onEditDateClicked(): Boolean {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            releaseDate = Date(year, monthOfYear, dayOfMonth)

            println(releaseDate)

            // Display Selected date in textbox
            etDate.setText("$dayOfMonth/$monthOfYear/$year")
        }, year, month, day)

        dpd.show()
        return true
    }

}
