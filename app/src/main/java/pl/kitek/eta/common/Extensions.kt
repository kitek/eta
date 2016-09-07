@file:JvmName("ExtensionsUtils")

package pl.kitek.eta.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rx.Subscription
import rx.subscriptions.CompositeSubscription


fun Subscription.addTo(subscription: CompositeSubscription) {
    subscription.add(this)
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}
