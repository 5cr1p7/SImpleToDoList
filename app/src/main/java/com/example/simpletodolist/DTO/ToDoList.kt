package com.example.simpletodolist.DTO

class ToDoList {
    var id: Long = -1
    var name = ""
    var createdAt = ""
    var items: MutableList<ToDoItem> = ArrayList()
}