package com.cops.tutorvirtual.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hitos")

data class HitoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val fechaLimite: String,
    val estado: String
)