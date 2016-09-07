package pl.kitek.eta.common.data.model

import android.os.Parcel
import android.os.Parcelable

class Location(val name: String, val lat: Float, val lng: Float) : Parcelable {

    companion object {
        @JvmField @Suppress("unused") val CREATOR: Parcelable.Creator<Location> = object : Parcelable.Creator<Location> {
            override fun createFromParcel(source: Parcel) = Location(source)
            override fun newArray(size: Int): Array<Location?> = arrayOfNulls(size)
        }
    }

    constructor(p: Parcel) : this(p.readString(), p.readFloat(), p.readFloat())

    override fun writeToParcel(out: Parcel, flag: Int) {
        out.writeString(name)
        out.writeFloat(lat)
        out.writeFloat(lng)
    }

    override fun describeContents() = 0

    override fun toString() = "Location[ name=$name, lat:$lat, lng:$lng ]"

    fun getLatLng(): String = "$lat,$lng"

}
