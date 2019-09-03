# CatchExceptionResponse

Some custom exceptions were created to handle the errors returned by the back end that we always have problems ... the execptions created were to address the following errors.
```
401 (Unauthorized)
403 (Forbidden)
422 (Unprocessable Entity)
424 (Failed Dependency)
```
and two more, one generic error and one for NetworkException

This new functionality eliminates the need to handle error types using https codes often using ViewModel, now you only need to handle the exception since this new function intercepts Response <T> checks for success or not and handles HTTP codes. and creates the correct exception for each type.

To use this new function has two paths, one using interceptor adding straight into the retrofit.
```
val interceptorResponse = Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            ManagerResponseCustomImp().getErrorResponseOkHttp(originalResponse)
        }

client.addInterceptor(interceptor)
```
but this approach is complicated because any and all requests will be handled this way, otherwise it would be to handle it in respositories using Rx calls with the extension function.
```
override fun requestPeriods(customerId: String): Single<List<InvoicePeriod>> {
        return creditInvoicesService
            .getPeriods(customerId)
            .subscribeOn(Schedulers.io())
            .byCustomException() // <- nosso amiguinho magico
            .cache()
            .map {
                it.periods?.map { item -> InvoicePeriodMapper.fromDTO(item) }
            }
    }
```
so the extension interferes with the request treats the return and if necessary throws the exception and all you have to do is handle the exception in viewmodel or activity and make the correct decision.
```
subscribe({
              Log.d("Retorno Api", "Sucesso $it")
          }, {
              when (it) {
                        is NetworkException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        is UnauthorizedErrorException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        is UnprocessableEntityErrorException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        is ForbiddenErrorException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        is FailedDependencyErrorException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        is GenericErrorException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        else -> Log.d("Retorno Api", it.toString())

                    }
                })              

```

import Library

gradle project
```
allprojects {
    repositories {
         ...
         maven { url 'https://jitpack.io' }
    }
}
```
app gradle
```
dependencies {
       implementation 'com.github.DaniloFurtado:CatchExceptionResponse:0.1.1'
}
```
