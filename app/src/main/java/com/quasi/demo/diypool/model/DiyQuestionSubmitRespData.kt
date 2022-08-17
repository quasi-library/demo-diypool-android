package com.quasi.template.demo.diypool.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 提交自选池问卷之后返回默认勾选的商品id列表
 */
data class DiyQuestionSubmitRespData(
    val diyCombinationList: ArrayList<DiyCombinationModel>? = null
)

/**
 * 自选池中勾选的单条商品数据
 */
@Parcelize
data class DiyCombinationModel(
    val num: Int? = null, // 1
    val productVariantId: String? = null, // 58820960379609541
    val type: String? = null // tent
) : Parcelable