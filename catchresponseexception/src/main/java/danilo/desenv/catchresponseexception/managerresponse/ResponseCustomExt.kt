package danilo.desenv.catchresponseexception.managerresponse

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

private val managerResponseCustom: ManagerResponseCustom =
    ManagerResponseCustomImp()

fun <T> Single<Response<T>>.byCustomException(): Single<T> {
    return this.map {
        managerResponseCustom.getErrorResponseRetrofit(it).body()
    }
}

fun <T> Maybe<Response<T>>.byCustomException(): Maybe<T> {
    return this.map {
        managerResponseCustom.getErrorResponseRetrofit(it).body()
    }
}

fun <T> Flowable<Response<T>>.byCustomException(): Flowable<T> {
    return this.map {
        managerResponseCustom.getErrorResponseRetrofit(it).body()
    }
}

fun <T> Observable<Response<T>>.byCustomException(): Observable<T> {
    return this.map {
        managerResponseCustom.getErrorResponseRetrofit(it).body()
    }
}