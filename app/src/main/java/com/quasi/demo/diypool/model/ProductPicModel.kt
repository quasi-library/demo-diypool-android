package com.quasi.template.demo.diypool.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 商品信息-图片信息
 */
@Parcelize
data class ProductPicModel(
    val cover: String? = null, // https://image.prod.sino-beta.com/asset/width-600/picture/2022-01-20/j2EaR2GaefqaH338d9A5X2aaE9Feif78n974y93ad1Q1=9=1/a8a0a5e298ea5e6ad55bac3c.jpg
    val isMain: Int? = null, // 0
    val isSpu: Int? = null, // 1
    val picId: String? = null, // 39403327267978121
    val picLink: String? = null, // https://image.prod.sino-beta.com/asset/width-600/picture/2022-01-20/j2EaR2GaefqaH338d9A5X2aaE9Feif78n974y93ad1Q1=9=1/a8a0a5e298ea5e6ad55bac3c.jpg
    val productVariantId: String? = null, // 58820960379606263
    val spuId: String? = null, // 68320123310964830
    val type: Int? = null // 0
) : Parcelable