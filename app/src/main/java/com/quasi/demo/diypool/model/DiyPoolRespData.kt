package com.quasi.demo.diypool.model

import com.quasi.template.demo.diypool.model.ProductPicModel
import com.quasi.template.demo.diypool.model.ProductTagModel

/**
 * 请求自选池所有商品可选列表
 */
data class DiyPoolRespData(
    val accessoriesList: DiyPoolModel? = null,
    val controllerList: DiyPoolModel? = null,
    val couponId: String? = "",
    val groups: ArrayList<GroupModel>? = arrayListOf(),
    val lightList: DiyPoolModel? = null,
    val recommend: RecommendModel? = RecommendModel(),
    val tentList: DiyPoolModel? = null,
    val ventilationList: DiyPoolModel? = null
)

/**
 * 存储受tent选中后，约定group的集合(即搭配此帐篷的其他配件Ids)
 */
data class GroupModel(
    val accessories: List<String?>? = null,
    val controller: List<String?>? = null,
    val light: List<String?>? = null,
    val tent: List<String?>? = null,
    val ventilation: List<String?>? = null
)

/**
 * 每个子项目的数据类型，相当于二级列表
 */
data class DiyPoolModel(
    val desc: String? = null, // We provide some accessory kit products for you to choose.
    val descCode: String? = null, // Lang_DIY_Accessory_Desc
    val filters: List<FilterModel>? = listOf(),
    val poolVariantList: List<PoolVariantModel?>? = null,
    val tags: List<ProductTagModel?>? = null,
    val title: String? = null, // Choose Accessory Bag
    val titleCode: String? = null // Lang_DIY_Accessory_Title
)

data class RecommendModel(
    val accessories: List<AccessoryModel?>? = null,
    val controller: List<ControllerModel?>? = null
)

data class FilterModel(
    val filters: List<FilterItemModel>? = listOf(),
    val name: String? = "", // Power_Lang
    val type: String? = "" // text
)

data class FilterItemModel(
    val desc: String? = null,
    val name: String? = null, // 160W/100-277V_lang
    val picUrl: String? = null,
    val variants: List<String?>? = null
)

data class PoolVariantModel(
    val afterPerDiscountPriceLocalSymbol: String? = null, // $107.99
    val afterPerDiscountPriceScale: String? = null, // 107.99
    val afterPerDiscountPriceSymbol: String? = null, // US$107.99
    val attributeWithValue: List<AttributeWithValueModel?>? = null,
    val catId: String? = null, // 58313278058168406
    val categoryTrackCode: String? = null, // CAT_Thermometer
    val desc: String? = null,
    val discountPriceNumDisplay: String? = null, // 10%
    val id: String? = null, // 68320123310966121
    val images: ArrayList<ProductPicModel>? = null,
    val isOnSale: Int? = null, // 1
    val isStock: Int? = null, // 1
    val link: String? = null, // Digital_Hygrometer_Indoor_Outdoor_Thermometer-p68320123310966121-v58820960379607746
    val listTrackCode: String? = null,
    val marketPrice: Double? = null, // 122.9900
    val marketPriceLocalSymbol: String? = null, // $122.99
    val marketPriceScale: String? = null, // 122.99
    val marketPriceSymbol: String? = null, // US$122.99
    val miniVariantInfo: MiniVariantInfoModel? = null,
    val name: String? = null, // Digital Hygrometer Indoor Outdoor Thermometer
    val productId: String? = null, // 68320123310966121
    val productTags: List<ProductTagModel>? = null,
    val productTrackCode: String? = null, // Prod_THM003
    val redTips: String? = null,
    val reviewNum: Int? = null, // 421
    val reviewScore: String? = null, // 4.8
    val shopPrice: Double? = null, // 119.9900
    val shopPriceLocalSymbol: String? = null, // $119.99
    val shopPriceScale: String? = null, // 119.99
    val shopPriceSymbol: String? = null, // US$119.99
    val trackId: String? = null, // 68320123310966121:58820960379607746
    val unit: String? = null,
    val variantId: String? = null, // 58820960379607746
    val variantTrackCode: String? = null // ProdVar_WEB-THM003J
)

data class AttributeWithValueModel(
    val attributeId: String? = null, // 59882848098713687
    val attributeName: String? = null, // Max Coverage Area
    val attributeOptionId: String? = null, // 59882848098713687
    val attributeOptionName: String? = null, // Max Coverage Area
    val attributeValues: List<AttributeValueModel?>? = null
)

data class AttributeValueModel(
    val attributeValueId: String? = null, // 60071852429934786
    val name: String? = null // 5x5ft (Core:4x4ft)
)

data class AccessoryModel(
    val name: String? = null, // diy_pool_recommend_accessories_basic
    val sort: Int? = null, // 1
    val sumPrice: Double? = null, // 119.9900
    val sumPriceDisplay: String? = null, // $119.99
    val variants: List<String?>? = null
)

data class ControllerModel(
    val name: String? = null, // diy_pool_recommend_controller_basic
    val sort: Int? = null, // 1
    val sumPrice: Double? = null, // 141.7800
    val sumPriceDisplay: String? = null, // $141.78
    val variants: List<String?>? = null
)

data class MiniVariantInfoModel(
    val afterPerDiscountPriceLocalSymbol: String? = null,
    val afterPerDiscountPriceScale: String? = null,
    val afterPerDiscountPriceSymbol: String? = null,
    val desc: String? = null, // Digital Hygrometer Indoor Outdoor Thermometer
    val descLangCode: String? = null, // Lang_Name_ProdVar_WEB-THM003J
    val discountPriceNumDisplay: String? = null, // 10%
    val images: List<ProductPicModel?>? = null,
    val isOnSale: Int? = null, // 1
    val isStock: Int? = null, // 1
    val link: String? = null, // /Lang_Name_ProdVar_WEB-THM003J-p68320123310966121-v58820960379607746
    val marketPrice: Double? = null, // 122.99
    val marketPriceLocalSymbol: String? = null, // $122.99
    val marketPriceScale: String? = null, // 122.99
    val marketPriceSymbol: String? = null, // US$122.99
    val name: String? = null, // Digital Hygrometer Indoor Outdoor Thermometer
    val nameLangCode: String? = null, // Lang_Name_ProdVar_WEB-THM003J
    val productId: String? = null, // 68320123310966121
    val productTags: List<ProductTagModel?>? = null,
    val shopPrice: Double? = null, // 119.99
    val shopPriceLocalSymbol: String? = null, // $119.99
    val shopPriceScale: String? = null, // 119.99
    val shopPriceSymbol: String? = null, // US$119.99
    val variantId: String? = null // 58820960379607746
)
