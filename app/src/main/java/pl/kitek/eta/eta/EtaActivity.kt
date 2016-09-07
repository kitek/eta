package pl.kitek.eta.eta

import android.content.Context
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.Wearable
import pl.kitek.eta.R
import pl.kitek.eta.common.data.model.Eta
import pl.kitek.eta.common.data.model.ListItem
import timber.log.Timber
import java.util.concurrent.TimeUnit

class EtaActivity : WearableActivity() {

    companion object {
        const val KEY_ETA = "eta"
        const val MESSAGE = "some-message"
        const val CONNECTION_TIME_OUT_MS = 1000L
    }

    private lateinit var client: GoogleApiClient
    private var nodeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eta)
        setAmbientEnabled()

        val item = intent.getParcelableExtra<ListItem>(KEY_ETA)
//        Timber.d("item: $item")
//        initApi()
//        containerView.setOnClickListener {
//            sendToast()
//        }
//        retrieveDeviceNode()

        if (item is Eta) {
            // Recent
            fragmentManager.beginTransaction()
                    .replace(R.id.containerView, ResultFragment.newInstance(item))
                    .commit()
        } else {
            // New
            fragmentManager.beginTransaction()
                    .replace(R.id.containerView, InputPlaceFragment())
                    .commit()

        }
    }

    fun goNext(eta: Eta) {
        if (eta.isComplete()) {
            fragmentManager.beginTransaction()
                    .replace(R.id.containerView, ResultFragment.newInstance(eta))
                    .commit()
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.containerView, InputPlaceFragment.newInstance(eta))
                    .commit()
        }
    }

    private fun initApi() {
        client = getGoogleApiClient(this)
        retrieveDeviceNode()
    }

    private fun getGoogleApiClient(context: Context): GoogleApiClient {
        return GoogleApiClient.Builder(context).addApi(Wearable.API).build()
    }

    private fun retrieveDeviceNode() {
        Thread(Runnable {
            client.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS)
            val result = Wearable.NodeApi.getConnectedNodes(client).await()
            val nodes = result.nodes
            if (nodes.size > 0) {
                nodeId = nodes[0].id
                Timber.d("connected node: $nodeId")
            }
            client.disconnect()
        }).start()
    }

    private fun sendToast() {
        if (nodeId != null) {
            Thread(Runnable {
                client.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS)
                Wearable.MessageApi.sendMessage(client, nodeId, MESSAGE, null)

                Timber.d("message sent")

                client.disconnect()
            }).start()
        }
    }
}
