package com.quasi.template.demo.diypool.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 商品展示的小标签
 */
@Parcelize
data class ProductTagModel(
    val productId: String? = null, // 110254906295189952
    val productVariantId: String? = null, // 110254906295189953
    val sort: Int? = null, // 0
    val tag: String? = null, // Non-advertising
    val textType: Int? = null // 2
) : Parcelable