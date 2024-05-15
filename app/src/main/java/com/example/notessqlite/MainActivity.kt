package com.example.notessqlite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notessqlite.databinding.ActivityMainBinding
import com.example.notessqlite.NoteDatabaseHelper

// Import necessary packages and classes

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding // Declare a variable for view binding
    private lateinit var db : NoteDatabaseHelper // Declare a variable for database helper
    private lateinit var notesAdapter: NotesAdapter // Declare a variable for the RecyclerView adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inflate the layout using view binding
        setContentView(binding.root) // Set the content view to the root of the inflated layout

        db = NoteDatabaseHelper(this) // Initialize the database helper
        notesAdapter= NotesAdapter(db.getAllNotes(), this) // Initialize the RecyclerView adapter with all notes from the database

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this) // Set the layout manager for the RecyclerView
        binding.notesRecyclerView.adapter = notesAdapter // Set the adapter for the RecyclerView

        binding.addButton.setOnClickListener { // Set click listener for the add button
            val intent = Intent(this,AddNoteActivity::class.java) // Create an intent to open the AddNoteActivity
            startActivity(intent) // Start the AddNoteActivity
        }

    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes()) // Refresh the data in the RecyclerView adapter with all notes from the database
    }
}
