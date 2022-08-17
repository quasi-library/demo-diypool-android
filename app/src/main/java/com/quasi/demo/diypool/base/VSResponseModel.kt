package com.quasi.template.demo.diypool.base

/// Api统一的返回结构
class VSResponseModel<T>(var code: Int = 0, var message: String? = null, var data: T? = null) {

    override fun toString(): String {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }

    /** 全局错误码 */
    companion object {
        const val API_RESPONSE_EXCEPTION = -1               // 未知异常
        const val API_RESPONSE_SUCCESS = 0                  // 请求成功
        const val API_RESPONSE_FAIL_NOT_FOUND = 404         //  接口未找到
        const val API_RESPONSE_FAIL_SERVER_RESTART = 502    //  服务器正在部署
        const val API_RESPONSE_FAIL_USER_ID = 100110        //  获取UserID失败，需要重新登录
        const val API_RESPONSE_FAIL_NO_USER = 100009        //  用户不存在
        const val API_RESPONSE_FAIL_WRONG_PASSWORD = 100105 //  登录密码不正确
        const val API_RESPONSE_FAIL_ORDER = 300004          //  订单不存在
    }
}