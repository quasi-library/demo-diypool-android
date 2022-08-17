package com.quasi.template.demo.diypool.base

import android.app.Activity

// 记录当前页面栈中的Activity的单例类
object VSActivityCollector {
    private val activities = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    // 随时随地退出程序
    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
        // 杀掉进程
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}