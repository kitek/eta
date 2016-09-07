package pl.kitek.eta.common.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.wearable.view.WearableListView
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import pl.kitek.eta.R

class WearableListItemLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0) : LinearLayout(context, attrs, defStyle), WearableListView.OnCenterProximityListener {

    private var circleView: ImageView? = null
    private var nameView: TextView? = null
    private val fadedTextAlpha: Float
    private val fadedCircleColor: Int
    private val chosenCircleColor: Int

    init {
        fadedTextAlpha = resources.getInteger(R.integer.action_text_faded_alpha) / 100f
        fadedCircleColor = ContextCompat.getColor(context, R.color.grey)
        chosenCircleColor = ContextCompat.getColor(context, R.color.blue)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        nameView = findViewById(R.id.nameView) as TextView
        circleView = findViewById(R.id.circleView) as ImageView
    }

    override fun onNonCenterPosition(animate: Boolean) {
        nameView?.alpha = fadedTextAlpha
        circleView?.let {
            if (animate) {
                it.animate().scaleX(0.6f).scaleY(0.6f).alpha(0.5f).duration = 250
            }
        }
    }

    override fun onCenterPosition(animate: Boolean) {
        nameView?.alpha = 1f
        if (animate) {
            circleView?.let {
                it.animate().scaleX(1.0f).scaleY(1.0f).alpha(1f).duration = 250
            }
        }
    }

}
