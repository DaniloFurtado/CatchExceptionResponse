package danilo.desenv.catchresponseexception.response

import com.google.gson.annotations.SerializedName

data class ParamApi(
    @SerializedName("name")
    val name: String?,
    @SerializedName("value")
    val value: String?
)