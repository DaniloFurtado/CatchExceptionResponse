package danilo.desenv.catchresponseexception.exceptions

import danilo.desenv.catchresponseexception.response.ErrorApi

class GenericErrorException(
    message: String,
    errors: List<ErrorApi> = listOf()
) : BaseException(message, errors)