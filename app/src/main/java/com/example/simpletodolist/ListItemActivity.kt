package com.example.simpletodolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodolist.Adapters.ItemAdapter
import com.example.simpletodolist.DTO.ToDoItem
import com.example.simpletodolist.Utils.DBHandler
import com.example.simpletodolist.Utils.INTENT_TODO_ID
import com.example.simpletodolist.Utils.INTENT_TODO_NAME
import kotlinx.android.synthetic.main.activity_list_item.*

class ListItemActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler
    var todoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = intent.getStringExtra(INTENT_TODO_NAME)
        todoId = intent.getLongExtra(INTENT_TODO_ID, -1)
        dbHandler = DBHandler(this)

        rv_item.layoutManager = LinearLayoutManager(this)
        rv_item.adapter = ItemAdapter(this, dbHandler.getToDoItems(todoId))

        fab_item.setOnClickListener {
            onFabClick()
        }
    }

    fun onFabClick() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("ToDo Items")
        val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
        val toDoName = view.findViewById<EditText>(R.id.ev_todo_list)
        dialog.setView(view)
        dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
            if (toDoName.text.isNotEmpty()) {
                val item = ToDoItem()
                item.itemName = toDoName.text.toString()
                item.toDoId = todoId
                item.isCompleted = false
                dbHandler.addToDoItem(item)
                refreshList()
            }
        }
        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

        }
        dialog.show()
    }

    fun updateItem(item : ToDoItem){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Update ToDo Item")
        val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
        val toDoName = view.findViewById<EditText>(R.id.ev_todo_list)
        toDoName.setText(item.itemName)
        dialog.setView(view)
        dialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->
            if (toDoName.text.isNotEmpty()) {
                item.itemName = toDoName.text.toString()
                item.toDoId = todoId
                item.isCompleted = false
                dbHandler.updateToDoItem(item)
                refreshList()
            }
        }
        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

        }
        dialog.show()
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    fun refreshList() {
        rv_item.adapter = ItemAdapter(this,dbHandler.getToDoItems(todoId))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else
            super.onOptionsItemSelected(item)
    }

}