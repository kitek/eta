package pl.kitek.eta.common.data.model

import android.os.Parcelable

interface ListItem : Parcelable {
    fun getName(): String
}
