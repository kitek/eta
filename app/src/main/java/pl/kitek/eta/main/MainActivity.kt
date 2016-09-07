package pl.kitek.eta.main

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.support.wearable.view.WearableListView
import kotlinx.android.synthetic.main.activity_main.*
import pl.kitek.eta.R
import pl.kitek.eta.eta.EtaActivity
import timber.log.Timber

class MainActivity : WearableActivity(), WearableListView.ClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etaList.adapter = MainAdapter()
        etaList.setClickListener(this)

        /**
         * One start Activity
         * Two start Activity with fragments
         *
         */
    }

    override fun onTopEmptyRegionClick() {
    }

    override fun onClick(v: WearableListView.ViewHolder?) {
        Timber.d("CLICK")

        // if new then run creator
        // if recent then run eta

        val intent = Intent(this, EtaActivity::class.java)
        intent.putExtra(EtaActivity.KEY_ETA, (etaList.adapter as MainAdapter).getItemAtPosition(1))

        startActivity(intent)
    }

}
