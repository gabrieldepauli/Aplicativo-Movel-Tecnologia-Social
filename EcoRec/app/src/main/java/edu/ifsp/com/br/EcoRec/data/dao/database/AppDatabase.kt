package edu.ifsp.com.br.EcoRec.data.dao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ifsp.com.br.EcoRec.data.dao.RecycleCenterDao
import edu.ifsp.com.br.EcoRec.data.dao.RecycleCenterMaterialDao
import edu.ifsp.com.br.EcoRec.data.dao.RecycleMaterialDao
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenterMaterial
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial

@Database(
    entities = [RecycleCenter::class, RecycleMaterial::class, RecycleCenterMaterial::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recycleCenterDao():RecycleCenterDao
    abstract fun recycleMaterialDao():RecycleMaterialDao
    abstract fun recycleCenterMaterialDao():RecycleCenterMaterialDao

}