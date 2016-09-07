package pl.kitek.eta.common

import android.widget.Toast
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import timber.log.Timber

class ListenerService : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        showToast(messageEvent.path)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}

