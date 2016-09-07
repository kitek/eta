package pl.kitek.eta.common.data.model

import android.os.Parcel
import android.os.Parcelable

class Eta(val origin: Location, val destination: Location) : ListItem, Parcelable {

    companion object {
        @JvmField @Suppress("unused") val CREATOR: Parcelable.Creator<Eta> = object : Parcelable.Creator<Eta> {
            override fun createFromParcel(source: Parcel) = Eta(source)
            override fun newArray(size: Int): Array<Eta?> = arrayOfNulls(size)
        }
    }

    constructor(p: Parcel) : this(
            p.readParcelable<Location>(Location::class.java.classLoader),
            p.readParcelable<Location>(Location::class.java.classLoader)
    )

    override fun getName() = destination.name

    override fun writeToParcel(out: Parcel, flag: Int) {
        out.writeParcelable(origin, flag)
        out.writeParcelable(destination, flag)
    }

    override fun describeContents() = 0

    override fun toString() = "ETA[ origin: $origin, destination: $destination ]"
}
