package danilo.desenv.catchexceptionresponse.api

import danilo.desenv.catchresponseexception.managerresponse.ManagerResponseCustomImp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object APIClient {
    private var retrofit: Retrofit? = null

    enum class LogLevel {
        LOG_NOT_NEEDED,
        LOG_REQ_RES,
        LOG_REQ_RES_BODY_HEADERS,
        LOG_REQ_RES_HEADERS_ONLY
    }

    /**
     * Returns Retrofit builder to create
     * @param logLevel - to print the log of Request-Response
     * @return retrofit
     */
    fun getClient(logLevel: LogLevel): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        val interceptorResponse = Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            ManagerResponseCustomImp().getErrorResponseOkHttp(originalResponse)
        }

        when (logLevel) {
            APIClient.LogLevel.LOG_NOT_NEEDED ->
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            APIClient.LogLevel.LOG_REQ_RES ->
                interceptor.level = HttpLoggingInterceptor.Level.BASIC
            APIClient.LogLevel.LOG_REQ_RES_BODY_HEADERS ->
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            APIClient.LogLevel.LOG_REQ_RES_HEADERS_ONLY ->
                interceptor.level = HttpLoggingInterceptor.Level.HEADERS

        }

        val client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(7, TimeUnit.SECONDS)
            .readTimeout(7, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            //.addInterceptor(interceptorResponse)

        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {

                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }


            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            client.sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        } catch (e: Exception) {
        }


        if (null == retrofit) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .build()
        }

        return retrofit!!
    }

    fun getAPIService(logLevel: LogLevel = APIClient.LogLevel.LOG_REQ_RES_BODY_HEADERS) =
        getClient(logLevel)
}