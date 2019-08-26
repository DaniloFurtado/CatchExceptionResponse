package danilo.desenv.catchresponseexception.exceptions

import danilo.desenv.catchresponseexception.response.ErrorApi

abstract class BaseException(
    message: String,
    val errors: List<ErrorApi> = listOf()
) : Exception(message)