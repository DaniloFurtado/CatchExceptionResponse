package danilo.desenv.catchresponseexception.response

import com.google.gson.annotations.SerializedName

data class ResponseApi(
    @SerializedName("errors")
    val errors: List<ErrorApi>
)