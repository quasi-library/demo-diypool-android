package com.quasi.demo.diypool.base

import android.app.Application


class VSApplication : Application() {

    //<editor-fold desc="Companion Method">
    companion object {
        private lateinit var instance: VSApplication
        fun getInstance() = instance
    }
    //</editor-fold>

    //<editor-fold desc="LifeCycle">
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    //</editor-fold>

    //<editor-fold desc="Private Method">
    private val LOG_TAG: String by lazy { this.javaClass.simpleName }

    //</editor-fold>
}