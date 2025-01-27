package edu.ifsp.com.br.EcoRec.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="recycle_material")

data class RecycleMaterial(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)