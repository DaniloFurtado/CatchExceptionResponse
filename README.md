# CatchExceptionResponse

Foram criados algumas exceptions customizadas para tratar os erros retornados pelo back end que sempre temos problemas... as execptions criadas foram para atender aos seguintes erros
```
401 (Unauthorized)
403 (Forbidden)
422 (Unprocessable Entity)
424 (Failed Dependency)
```
e mais duas, uma erro genericoo e outra para NetworkException

Essa nova funcionalidade elimina a necessidade de tratar os tipos erros usando codigos https muitas vezes usando no ViewModel, agora será necessário somente tratrar a exception já que essa nova funçao intercepta o Response<T> verifica se foi sucesso ou não e trata os codigos de HTTP e cria a exception correta para cada um tipo.

Para usar essa nova função tem dois caminhos, um usando intercpetor adicionando direto no retrofit.
```
val interceptorResponse = Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            ManagerResponseCustomImp().getErrorResponseOkHttp(originalResponse)
        }

client.addInterceptor(interceptor)
```
mas essa abordagem é complicada pq toda e qualquer requisição vai ser tratada dessa forma, òutra forma seria tratar isso nos respositories usando as chamadas de rx com a extension function.
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
pronto assim a extension interfere na requisisção trata o retorno e se for preciso lança a exception e tudo que vc tem que fazer é tratar a exception no viewmodel ou na activity etomar a decissão correta.
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
