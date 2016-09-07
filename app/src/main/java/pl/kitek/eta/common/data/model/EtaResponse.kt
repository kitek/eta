package pl.kitek.eta.common.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

class EtaResponse(
        @SerializedName("destination_addresses") val destinationAddresses: List<String>,
        @SerializedName("origin_addresses") val originAddresses: List<String>,
        val rows: ArrayList<EtaResponse.Row>,
        val status: String) {

    class Row(val elements: ArrayList<EtaResponse.Element>)
    class Element(val distance: EtaResponse.Value, val duration: EtaResponse.Value, val status: String)
    class Value(val text: String, val value: Int)

    fun getTime(): String = rows[0].elements[0].duration.text
    fun getDistance() = rows[0].elements[0].distance.text

}
