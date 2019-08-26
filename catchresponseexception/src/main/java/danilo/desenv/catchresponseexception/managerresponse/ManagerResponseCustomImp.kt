package danilo.desenv.catchresponseexception.managerresponse

import com.google.gson.Gson
import danilo.desenv.catchresponseexception.response.ResponseApi
import danilo.desenv.catchresponseexception.exceptions.*
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

class ManagerResponseCustomImp(
    private val gson: Gson = Gson()
) : ManagerResponseCustom {

    override fun <T> getErrorResponseRetrofit(response: retrofit2.Response<T>): retrofit2.Response<T> {
        if (response.isSuccessful) {
            return response
        } else {
            val responseErrorBody = response.errorBody()
            throw createExceptionToReturn(responseErrorBody, response.code())
        }
    }

    override fun getErrorResponseOkHttp(response: Response): Response {
        if (response.isSuccessful) {
            return response
        } else {
            val responseBody = response.body()
            throw createExceptionToReturn(responseBody, response.code())
        }
    }

    private fun createExceptionToReturn(
        responseBody: ResponseBody?,
        httpCode: Int
    ): BaseException {
        return if (responseBody != null && responseBody.contentLength() > 0) {
            val errorBodyJsonObject = convertBodyToJson(responseBody.string())
            processResponseToException(errorBodyJsonObject, httpCode)
        } else {
            createExceptionInvalidBody()
        }
    }

    private inline fun <reified T> parseJsonToResponseApi(jsonObject: JSONObject): T =
        gson.fromJson(jsonObject.toString(), T::class.java)

    private fun convertBodyToJson(stringBody: String) =
        try {
            JSONObject(stringBody)
        } catch (e: Exception) {
            throw createExceptionInvalidBody()
        }

    private fun createExceptionInvalidBody(): GenericErrorException {
        return GenericErrorException(
            "Has no content value returned from Api"
        )
    }

    private fun processResponseToException(
        jsonObject: JSONObject,
        httpCode: Int
    ): BaseException {
        val responseApi = parseJsonToResponseApi<ResponseApi>(jsonObject)
        return createExceptionApi(httpCode, responseApi)
    }

    private fun createExceptionApi(httpCode: Int, responseApi: ResponseApi): BaseException {
        return when (httpCode) {
            HTTP_UNAUTHORIZED -> {
                UnauthorizedErrorException(
                    UnauthorizedErrorException::class.java.simpleName,
                    responseApi.errors
                )
            }

            HTTP_FORBIDDEN -> {
                ForbiddenErrorException(
                    ForbiddenErrorException::class.java.simpleName,
                    responseApi.errors
                )
            }

            HTTP_UNPROCESSABLE_ENTITY -> {
                UnprocessableEntityErrorException(
                    UnprocessableEntityErrorException::class.java.simpleName,
                    responseApi.errors
                )
            }

            HTTP_FAILED_DEPENDENCY -> {
                FailedDependencyErrorException(
                    FailedDependencyErrorException::class.java.simpleName,
                    responseApi.errors
                )
            }

            else -> {
                GenericErrorException(
                    GenericErrorException::class.java.simpleName
                )
            }
        }
    }

    companion object {
        private const val HTTP_UNAUTHORIZED = 401
        private const val HTTP_FORBIDDEN = 403
        private const val HTTP_UNPROCESSABLE_ENTITY = 422
        private const val HTTP_FAILED_DEPENDENCY = 424
    }
}