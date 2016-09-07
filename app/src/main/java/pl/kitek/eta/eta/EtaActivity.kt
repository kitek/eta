package pl.kitek.eta.eta

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import pl.kitek.eta.R
import pl.kitek.eta.common.data.model.Eta
import pl.kitek.eta.common.data.model.ListItem
import pl.kitek.eta.common.data.source.DataRepository
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

        val repo = DataRepository()
        repo.getEta(item as Eta).subscribe({
            Timber.d("onNEXT: ${it.getTime()}")

        }, {
            Timber.d("onError :P")
        })

    }

}
