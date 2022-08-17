package com.quasi.template.demo.diypool.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * viewModel的基类，封装的主要任务是为了处理网络请求过程中出现的异常导致的闪退问题
 */
open class VSBaseViewModel : ViewModel() {

    companion object {
        val LOG_TAG: String = this.javaClass.simpleName
    }

    //<editor-fold desc="LiveData CallBack">
    // 请求报错回调
    val requestErrorLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val loadingLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    //</editor-fold>

    fun launch(
        block: suspend () -> Unit,
        error: suspend (Throwable) -> Unit = {},
        complete: suspend () -> Unit = {},
    ) {
        loadingLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
                loadingLiveData.postValue(false)
            } catch (e: Exception) {
                error(e)
                Log.d("launch", "$e")
                loadingLiveData.postValue(false)
            } finally {
                complete()
            }
        }
    }

}