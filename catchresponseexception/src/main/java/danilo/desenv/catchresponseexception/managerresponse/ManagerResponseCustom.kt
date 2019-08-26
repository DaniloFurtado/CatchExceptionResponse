package danilo.desenv.catchresponseexception.managerresponse

import okhttp3.Response

interface ManagerResponseCustom {
    fun <T> getErrorResponseRetrofit(response: retrofit2.Response<T>): retrofit2.Response<T>
    fun getErrorResponseOkHttp(response: Response): Response
}