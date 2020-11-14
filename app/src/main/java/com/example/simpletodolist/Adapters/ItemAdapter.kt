package com.example.simpletodolist.Adapters

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodolist.DTO.ToDoItem
import com.example.simpletodolist.ListItemActivity
import com.example.simpletodolist.R

class ItemAdapter(private val activity: ListItemActivity, private val list: MutableList<ToDoItem>):
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_child_item, p0, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.itemName.text = list[p1].itemName
        holder.itemName.isChecked = list[p1].isCompleted
        holder.itemName.setOnClickListener {
            list[p1].isCompleted = !list[p1].isCompleted
            activity.dbHandler.updateToDoItem(list[p1])
        }
        holder.delete.setOnClickListener {
            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Are you sure")
            dialog.setMessage("Do you want to delete this item ?")
            dialog.setPositiveButton("Delete") { _: DialogInterface, _: Int ->
                activity.dbHandler.deleteToDoItem(list[p1].id)
                activity.refreshList()
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()
        }
        holder.edit.setOnClickListener {
            activity.updateItem(list[p1])
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val itemName: CheckBox = v.findViewById(R.id.cb_item)
        val edit : ImageView = v.findViewById(R.id.iv_edit)
        val delete : ImageView = v.findViewById(R.id.iv_delete)
    }
}