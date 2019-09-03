package danilo.desenv.catchresponseexception.response

import com.google.gson.annotations.SerializedName

data class ErrorApi(
    @SerializedName("property")
    val property: String?,
    @SerializedName("value")
    val value: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("constraint")
    val constraint: ConstraintApi
)