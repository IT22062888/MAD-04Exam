package com.example.notessqlite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notessqlite.Note
import com.example.notessqlite.NoteDatabaseHelper

// Adapter class for managing the RecyclerView items

class NotesAdapter (private var notes: List<Note>, context: Context):
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NoteDatabaseHelper = NoteDatabaseHelper(context) // Database helper instance

    // ViewHolder class to hold the views for each item in the RecyclerView
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView) // TextView for note title
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView) // TextView for note content
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton) // ImageView for update action
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton) // ImageView for delete action
    }

    // Method called when a new ViewHolder object needs to be created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // Inflate the layout for a single item in the RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent ,false )
        return NoteViewHolder(view) // Return a new NoteViewHolder object with the inflated view
    }

    // Method to get the number of items in the RecyclerView
    override fun getItemCount(): Int = notes.size

    // Method called to bind data to the views of a ViewHolder
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position] // Get the note object at the current position
        holder.titleTextView.text = note.title // Set the title TextView text to the note title
        holder.contentTextView.text = note.content // Set the content TextView text to the note content

        // Set click listener for the update button
        holder.updateButton.setOnClickListener {
            // Create an intent to open the UpdateNoteActivity and pass the note ID as extra data
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply{
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent) // Start the UpdateNoteActivity
        }

        // Set click listener for the delete button
        holder.deleteButton.setOnClickListener {
            db.deleteNote(note.id) // Delete the note from the database
            refreshData(db.getAllNotes()) // Refresh the data in the RecyclerView
            // Show a toast message indicating that the note is deleted
            Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    // Method to refresh the data in the RecyclerView with new list of notes
    fun refreshData(newNotes : List<Note>){
        notes = newNotes // Update the list of notes
        notifyDataSetChanged() // Notify the adapter that the data set has changed
    }

}
