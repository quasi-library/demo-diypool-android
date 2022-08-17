package com.quasi.demo.diypool.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.quasi.demo.diypool.model.DiyPoolModel
import com.quasi.demo.diypool.model.DiyPoolRespData
import com.quasi.demo.diypool.model.PoolVariantModel
import com.quasi.demo.diypool.model.VSResponseModel
import com.quasi.template.demo.diypool.base.VSBaseViewModel
import com.quasi.template.demo.diypool.base.vsGetAssets
import com.quasi.template.demo.diypool.model.DiyCombinationModel

/**
 * 自选池页面的数据及勾选逻辑
 */
class DiyKitPoolViewModel : VSBaseViewModel() {

    //<editor-fold desc="Property">

    /**
     * 整个自选池页面包含的所有商品
     */
    private var mDataSource: DiyPoolRespData? = null

    /**
     * 根据调查问卷默认选中的商品Ids，用于重置时使用
     */
    var mDefaultIds: ArrayList<DiyCombinationModel>? = null

    /**
     * 当前勾选的商品Ids
     */
    var mSelectedIds: ArrayList<DiyCombinationModel>? = null

    //</editor-fold>

    //<editor-fold desc="Callback LiveData">

    val refreshListLiveData: MutableLiveData<List<DiyPoolModel?>> =
        MutableLiveData<List<DiyPoolModel?>>()

    //</editor-fold>

    //<editor-fold desc="Request Method">

    /**
     * 从本地获取JSON
     */
    private fun mockData(): VSResponseModel<DiyPoolRespData> {
        // 解析JSON数据
        val inputStream = vsGetAssets().open("mock.json")
        val jsonString = String(inputStream.readBytes())

        val data = Gson().fromJson<DiyPoolRespData>(
            jsonString,
            DiyPoolRespData::class.java
        )

        return VSResponseModel(
            code = 0,
            message = "mock",
            data = data
        )
    }

    /**
     * 请求自选池模版
     */
    fun requestDiyPoolProducts() {
        launch({
            val result = mockData()
            if (result.code == VSResponseModel.API_RESPONSE_SUCCESS && result.data != null) {
                // 先刷新数据源
                mDataSource = result.data

                // 判断库存后勾选默认项

                // 将数据按照section分类添加至分组,抛给页面去刷新
                val sectionList = listOf(
                    result.data!!.tentList,
                    result.data!!.controllerList,
                    result.data!!.lightList,
                    result.data!!.ventilationList,
                    result.data!!.accessoriesList
                )
                refreshListLiveData.postValue(sectionList)
            } else {
                requestErrorLiveData.postValue("generate fail")
            }
        })
    }
    //</editor-fold>

    /**
     * 通过skuId反向查询skuModel，方便查询库存等
     */
    fun querySkuModelById(skuId: String, skuType: String): PoolVariantModel? {
        val subList: DiyPoolModel? = when (skuType) {
            "tent" -> mDataSource?.tentList
            "controller" -> mDataSource?.controllerList
            "light" -> mDataSource?.lightList
            "ventilation" -> mDataSource?.ventilationList
            "accessories" -> mDataSource?.accessoriesList
            else -> {
                Log.e(LOG_TAG, "在自选池中反向查询sku时，type异常")
                null
            }
        }

        val skuModel = subList?.poolVariantList?.firstOrNull {
            skuId == it?.id
        }

        return skuModel
    }

}
