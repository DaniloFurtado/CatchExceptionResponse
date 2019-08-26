package danilo.desenv.catchresponseexception.exceptions

import java.io.IOException

class NetworkException(
    message: String
) : IOException(message)