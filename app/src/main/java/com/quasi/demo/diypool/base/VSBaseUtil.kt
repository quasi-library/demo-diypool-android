package com.quasi.template.demo.diypool.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.provider.Settings
import android.util.SparseArray
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.quasi.demo.diypool.base.VSApplication
import java.io.*


fun vsGetString(stringResourceId: Int) = VSApplication.getInstance().getString(stringResourceId)

fun vsGetColor(colorResourceId: Int) =
    ContextCompat.getColor(VSApplication.getInstance(), colorResourceId)

fun vsGetColorList(colorListResourceId: Int) =
    ContextCompat.getColorStateList(VSApplication.getInstance(), colorListResourceId)

fun vsGetDrawable(drawableId: Int) =
    ContextCompat.getDrawable(VSApplication.getInstance(), drawableId)

fun vsGetDensity() = VSApplication.getInstance().resources.displayMetrics.density

fun vsGetScaledDensity() = VSApplication.getInstance().resources.displayMetrics.scaledDensity

fun vsPx2dp(px: Float): Float = (px / vsGetDensity() + 0.5f)

fun vsDp2px(dp: Float): Float = dp * vsGetDensity() + 0.5f

fun vsSp2px(sp: Float): Float = vsGetScaledDensity() * sp + 0.5f

fun vsPx2sp(px: Float): Float = px / vsGetScaledDensity() + 0.5f

fun square(x: Float) = x * x

/**
 * View的扩展函数,简化控件出现或者消失的代码
 */
fun View.vsIsShow(status: Boolean) {
    if (status) this.visibility = View.VISIBLE else this.visibility = View.GONE
}

fun vsShowLongToast(stringRes: Int) {
    Toast.makeText(VSApplication.getInstance(), vsGetString(stringRes), Toast.LENGTH_LONG)
        .show()
}

fun vsShowLongToast(showString: String) {
    Toast.makeText(VSApplication.getInstance(), showString, Toast.LENGTH_LONG).show()
}

fun vsShowShortToast(stringRes: Int) {
    Toast.makeText(VSApplication.getInstance(), vsGetString(stringRes), Toast.LENGTH_SHORT)
        .show()
}

fun vsShowShortToast(showString: String) {
    Toast.makeText(VSApplication.getInstance(), showString, Toast.LENGTH_SHORT).show()
}

fun <V> sparseOf(vararg pairs: Pair<Int, V>): SparseArray<V> = if (pairs.isNotEmpty()) {
    pairs.toSparse(SparseArray(pairs.size))
} else {
    SparseArray()
}

fun <V, M : SparseArray<in V>> Array<out Pair<Int, V>>.toSparse(destination: M): M =
    destination.apply { putAll(this@toSparse) }

fun <V> SparseArray<V>.putAll(pairs: Array<out Pair<Int, V>>) {
    for ((key, value) in pairs) {
        put(key, value)
    }
}

fun timeDurationToMinute(duration: Int): String =
    StringBuilder().append(duration / 60).append(" min").toString()

fun cToF(celsius: Float): Float {
    return celsius * 1.8f + 32f
}

fun fToC(fahrenheit: Float): Double {
    return (fahrenheit - 32) / 1.8
}


/**
 * 补全，用于将小于10的数字补全未两位
 * @param number 用于补全的数字
 */
fun numberCompletion(number: Int): String {
    val completionNumber = if (number < 10) {
        StringBuilder().append("0").append(number)
    } else {
        StringBuilder().append(number)
    }
    return completionNumber.toString()
}

fun <T : Serializable> deepCopy(obj: T?): T? {
    if (obj == null) return null
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(obj)
    oos.close()
    val bais = ByteArrayInputStream(baos.toByteArray())
    val ois = ObjectInputStream(bais)
    @Suppress("unchecked_cast")
    return ois.readObject() as T
}

/**
 * 获取屏幕高度(包含状态栏+标题栏+View区域)
 */
fun vsGetScreenHeight(): Int {
    val wm = VSApplication.getInstance()
        .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return wm.defaultDisplay.height
}

/**
 * 获取屏幕宽度
 */
fun vsGetScreenWidth(): Int {
    val wm = VSApplication.getInstance().getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return wm.defaultDisplay.width
}

/**
 * 获取手机状态栏的高度（展示电池电量，系统时间那一行）
 */
fun vsGetStatusHeight(context: Activity): Int {
    var statusBarHeight1 = -1
    //获取status_bar_height资源的ID
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        //根据资源ID获取响应的尺寸值
        statusBarHeight1 = context.resources.getDimensionPixelSize(resourceId)
    }
    return statusBarHeight1
}

/**
 * 获取自定义标题栏的高度（展示标题和navigationItem那一行）
 */
fun vsGetNavigationBarHeight(): Int {
    return 54
}

fun vsGetAssets(): AssetManager {
    return VSApplication.getInstance().assets
}

/**
 * 获取数字常用字体
 */
fun vsGetNumberFont(): Typeface {
    return Typeface.createFromAsset(vsGetAssets(), "fonts/helvetica_neue_condensed_bold.ttf")
}

/**
 * 获取粗体字体
 */
fun vsGetBoldFont(): Typeface {
    return Typeface.createFromAsset(vsGetAssets(), "fonts/abc_diatype_bold.otf")
}

fun vsGetMediumFont(): Typeface {
    return Typeface.createFromAsset(vsGetAssets(), "fonts/abc_diatype_medium.otf")
}

fun vsGetRegularFont(): Typeface {
    return Typeface.createFromAsset(vsGetAssets(), "fonts/abc_diatype_regular.otf")
}

fun vsGetLightFont(): Typeface {
    return Typeface.createFromAsset(vsGetAssets(), "fonts/abc_diatype_light.otf")
}

fun vsGetBoldItalicFont(): Typeface {
    return Typeface.createFromAsset(vsGetAssets(), "fonts/ABCDiatype-BoldItalic.otf")
}

/**
 * 获取应用绘制区域高度(去除状态栏，去除标题栏，仅仅Activity View区域)
 */
fun vsGetViewContentHeight(context: Activity): Int {
    return vsGetScreenHeight() - vsGetStatusHeight(context) - vsGetNavigationBarHeight()
}

/**
 * 获取Android id
 */

@SuppressLint("HardwareIds") // 这个地方无视警告，但是这个android id在root模式下可以被修改
fun vsGetAndroidId() = Settings.Secure.getString(
    VSApplication.getInstance().contentResolver,
    Settings.Secure.ANDROID_ID
)

/**
 * 不足10的字符在前面添加0补全字符
 * @param char 字符本身
 * @return 补全之后的字符
 */
fun completeCharLessThan10(char: Int): String {
    return if (char < 10) {
        StringBuilder().append("0").append(char).toString()
    } else {
        char.toString()
    }
}

/**
 * 获取app的版本
 */
fun vsGetAppVersion(): String {
    val packageManager = VSApplication.getInstance().packageManager
    val packageInfo = packageManager.getPackageInfo(VSApplication.getInstance().packageName, 0)
    return packageInfo.versionName
}



