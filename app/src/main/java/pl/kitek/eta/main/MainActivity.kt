package pl.kitek.eta.main

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.support.wearable.view.WearableListView
import kotlinx.android.synthetic.main.activity_main.*
import pl.kitek.eta.R
import pl.kitek.eta.eta.EtaActivity

class MainActivity : WearableActivity(), WearableListView.ClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAmbientEnabled()

        etaList.adapter = MainAdapter()
        etaList.setClickListener(this)
    }

    override fun onTopEmptyRegionClick() {
    }

    override fun onClick(v: WearableListView.ViewHolder) {
        val intent = Intent(this, EtaActivity::class.java)
        intent.putExtra(EtaActivity.KEY_ETA, (etaList.adapter as MainAdapter).getItemAtPosition(v.adapterPosition))
        startActivity(intent)
    }

    override fun onEnterAmbient(ambientDetails: Bundle?) {
        super.onEnterAmbient(ambientDetails)

    }

    override fun onExitAmbient() {
        super.onExitAmbient()
        
    }

}
