package danilo.desenv.catchresponseexception.response

import com.google.gson.annotations.SerializedName

data class ConstraintApi(
    @SerializedName("name")
    val name: String,
    @SerializedName("params")
    val params: List<ParamApi>
)