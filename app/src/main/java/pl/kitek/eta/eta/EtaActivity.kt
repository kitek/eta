package pl.kitek.eta.eta

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import pl.kitek.eta.R
import pl.kitek.eta.common.data.model.Eta
import pl.kitek.eta.common.data.model.ListItem
import timber.log.Timber

class EtaActivity : WearableActivity() {

    companion object {
        const val KEY_ETA = "eta"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val item = intent.getParcelableExtra<ListItem>(KEY_ETA)
        Timber.d("item: $item")

        val URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s"
        val KEY = "AIzaSyA90EpRcMuGtCWlkRCug8TPHv5bINgM90s"

        if (item is Eta) {

//            val client = OkHttpClient()
//            val request = Request
//                    .Builder()
//                    .url(URL.format(item.origin.getLatLng(), item.destination.getLatLng(), KEY))
//                    .build()
//            client.newCall(request).enqueue(object : Callback {
//                override fun onFailure(call: Call?, e: IOException?) {
//                    Timber.d("SAD, failure")
//                }
//
//                override fun onResponse(call: Call?, response: Response) {
//                    if (!response.isSuccessful) return
//
//                    Timber.d("** SUCCESS **")
//                    Timber.d("response: ${response.body().string()}")
//                }
//
//            })

        }


    }

}
