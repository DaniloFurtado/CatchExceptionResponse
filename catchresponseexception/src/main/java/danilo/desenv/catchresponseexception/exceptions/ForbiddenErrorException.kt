package danilo.desenv.catchresponseexception.exceptions

import danilo.desenv.catchresponseexception.response.ErrorApi

class ForbiddenErrorException(
    message: String,
    errors: List<ErrorApi> = listOf()
) : BaseException(message, errors)