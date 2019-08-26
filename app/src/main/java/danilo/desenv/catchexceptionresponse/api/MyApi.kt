package danilo.desenv.catchexceptionresponse.api

import danilo.desenv.catchresponseexception.response.ResponseApi
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {

    // 200 5d5e8bc42f00000d0092faa9
    // 401 5d5e8be12f00004a0092faab
    // 403 5d5e8bf22f0000610092faad
    // 422 5d5e8c072f00000d0092fab1
    // 424 5d5e8c1d2f00004a0092fab4

    // Has no content 5d5ea1992f00004e0092fb7c
    // has invalid content 5d5ec5152f0000610092fc62

    @GET("5d5e8c072f00000d0092fab1")
    fun requestBack(): Single<Response<ResponseApi>>

}