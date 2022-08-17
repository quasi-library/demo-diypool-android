package com.quasi.template.demo.diypool.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat

class VSBaseBarItemView @kotlin.jvm.JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    val onClickListener: () -> Unit = {},
    val customizeView: View = TextView(context),
) : LinearLayoutCompat(context, attrs) {

    private var customView: View = View(context)

    companion object {
        private var MARGIN_END_INTERVAL = vsDp2px(8f).toInt()
    }

    init {
        initCustomViewByType(VSBarItemType.CUSTOMIZE)
        addView(customView)
        layoutCustomView()
    }

    private fun initCustomViewByType(viewType: VSBarItemType) {
        customView = when (viewType) {
            VSBarItemType.TEXTVIEW -> {
                TextView(context)
            }
            VSBarItemType.IMAGEVIEW -> {
                ImageView(context)
            }
            VSBarItemType.CUSTOMIZE -> {
                customizeView
            }
        }
    }

    private fun layoutCustomView() {
        val layoutParams = customView.layoutParams as LayoutParams
        layoutParams.marginEnd = MARGIN_END_INTERVAL

        // 限制Item尺寸
//        if (customView is ImageView) {
//            layoutParams.height = BAR_ITEM_IMAGE_HEIGHT
//            layoutParams.width = BAR_ITEM_IMAGE_WIDTH
//        }
        customView.layoutParams = layoutParams

        customView.setOnClickListener {
            onClickListener()
        }
    }

    /**
     * 更新Item展示内容
     * @param itemType 传入的Item类型，文字？图片？自定义视图
     * @param resourceId 传入的资源，文字:string,图片:drawable,自定义视图:layoutXml
     */
    fun updateItemContent(itemType: VSBarItemType, resourceId: Int) {
        initCustomViewByType(itemType)

        when (itemType) {
            VSBarItemType.TEXTVIEW -> {
                (customView as TextView).text = vsGetString(resourceId)
                customView.setOnClickListener {
                    onClickListener()
                }
            }

            VSBarItemType.IMAGEVIEW -> {
                (customView as ImageView).setImageDrawable(vsGetDrawable(resourceId))
            }
            VSBarItemType.CUSTOMIZE -> {
                //TODO
            }
        }
    }
}

enum class VSBarItemType {
    TEXTVIEW,
    IMAGEVIEW,
    CUSTOMIZE
}

