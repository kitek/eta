package pl.kitek.eta.common.data.model

import android.os.Parcel

class ListItemNew : ListItem {

    override fun getName() = "New ETA"

    override fun writeToParcel(p: Parcel?, flag: Int) {
    }

    override fun describeContents() = 0
}
