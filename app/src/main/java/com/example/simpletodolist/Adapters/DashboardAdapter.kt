package com.example.simpletodolist.Adapters

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodolist.DTO.ToDoList
import com.example.simpletodolist.ListItemActivity
import com.example.simpletodolist.MainActivity
import com.example.simpletodolist.R
import com.example.simpletodolist.Utils.INTENT_TODO_ID
import com.example.simpletodolist.Utils.INTENT_TODO_NAME

class DashboardAdapter(private val activity: MainActivity, private val list: MutableList<ToDoList>):
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_child_dashboard, p0, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.toDoName.text = list[p1].name

        holder.toDoName.setOnClickListener {
            val intent = Intent(activity, ListItemActivity::class.java)
            intent.putExtra(INTENT_TODO_ID,list[p1].id)
            intent.putExtra(INTENT_TODO_NAME,list[p1].name)
            activity.startActivity(intent)
        }

        holder.menu.setOnClickListener {
            val popup = PopupMenu(activity,holder.menu)
            popup.inflate(R.menu.dashboard_child)
            popup.setOnMenuItemClickListener {

                when(it.itemId){
                    R.id.menu_edit->{
                        activity.updateToDo(list[p1])
                    }
                    R.id.menu_delete->{
                        val dialog = AlertDialog.Builder(activity)
                        dialog.setTitle("Are you sure")
                        dialog.setMessage("Do you want to delete this task ?")
                        dialog.setPositiveButton("Continue") { _: DialogInterface, _: Int ->
                            activity.dbHandler.deleteToDo(list[p1].id)
                            activity.refreshList()
                        }
                        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

                        }
                        dialog.show()
                    }
                    R.id.menu_mark_as_completed->{
                        activity.dbHandler.updateToDoItemCompletedStatus(list[p1].id,true)
                    }
                    R.id.menu_reset->{
                        activity.dbHandler.updateToDoItemCompletedStatus(list[p1].id,false)
                    }
                }

                true
            }
            popup.show()
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val toDoName: TextView = v.findViewById(R.id.tv_todo_name)
        val menu : ImageView = v.findViewById(R.id.iv_menu)
    }
}