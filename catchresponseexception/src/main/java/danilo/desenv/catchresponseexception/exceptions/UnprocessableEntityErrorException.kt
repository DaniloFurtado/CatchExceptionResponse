package danilo.desenv.catchresponseexception.exceptions

import danilo.desenv.catchresponseexception.response.ErrorApi

class UnprocessableEntityErrorException(
    message: String,
    errors: List<ErrorApi> = listOf()
) : BaseException(message, errors)