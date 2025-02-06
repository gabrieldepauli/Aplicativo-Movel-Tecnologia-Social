package edu.ifsp.com.br.EcoRec.ui.changeData

import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial

interface ItemClickListener {

    fun DeleteMaterial(id: Int)
    fun DeleteCenter(id: Int)

    fun UpdateMaterial(material: RecycleMaterial)
    fun UpdateCenter(center: RecycleCenter)
}