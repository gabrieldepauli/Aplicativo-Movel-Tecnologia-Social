package edu.ifsp.com.br.EcoRec.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "recycle_center_material",
    primaryKeys = ["recycleCenterId", "recycleMaterialId"],
    foreignKeys = [
        ForeignKey(
            entity = RecycleCenter::class,
            parentColumns = ["id"],
            childColumns = ["recycleCenterId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RecycleMaterial::class,
            parentColumns = ["id"],
            childColumns = ["recycleMaterialId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["recycleCenterId"]), Index(value = ["recycleMaterialId"])]
)
data class RecycleCenterMaterial (
    val recycleCenterId:Int,
    val recycleMaterialId:Int
)