package pl.kitek.eta.common.data.model

import android.os.Parcel
import android.os.Parcelable

class ListItemNew() : ListItem {

    companion object {
        @JvmField @Suppress("unused") val CREATOR: Parcelable.Creator<ListItemNew> = object : Parcelable.Creator<ListItemNew> {
            override fun createFromParcel(source: Parcel) = ListItemNew()
            override fun newArray(size: Int): Array<ListItemNew?> = arrayOfNulls(size)
        }
    }

    override fun getName() = "New ETA"

    override fun writeToParcel(p: Parcel?, flag: Int) {
    }

    override fun describeContents() = 0
}
