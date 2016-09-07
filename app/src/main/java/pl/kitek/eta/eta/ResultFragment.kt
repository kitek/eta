package pl.kitek.eta.eta

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.result_fragment.*
import pl.kitek.eta.R
import pl.kitek.eta.common.addTo
import pl.kitek.eta.common.data.model.Eta
import pl.kitek.eta.common.data.model.EtaResponse
import pl.kitek.eta.common.data.source.DataRepository
import pl.kitek.eta.common.inflate
import rx.subscriptions.CompositeSubscription
import timber.log.Timber

/**
 * Show ETA as simple text label
 */
class ResultFragment : Fragment() {

    companion object {
        const val KEY_ETA = "eta"

        fun newInstance(eta: Eta): ResultFragment {
            val fragment = ResultFragment()
            fragment.arguments = Bundle()
            fragment.arguments.putParcelable(KEY_ETA, eta)
            return fragment
        }
    }

    private var eta: Eta? = null
    private val repo = DataRepository()
    private val subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eta = arguments.getParcelable<Eta>(KEY_ETA)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup, savedInstanceState: Bundle?): View {
        return container.inflate(R.layout.result_fragment)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (null == eta) {
            showError("Ka-boom! :(")
            return
        }

        repo.getEta(eta!!).subscribe({
            showResult(it)
        }, {
            showError("Error :(")
            Timber.d("onError: ${it.message}")
        }).addTo(subscriptions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscriptions.clear()
    }

    fun showError(message: String) {
        etaProgressBar.visibility = View.GONE
        etaTextView.text = message
        distanceTextView.visibility = View.GONE
        etaLayout.visibility = View.VISIBLE
    }

    fun showResult(result: EtaResponse) {
        etaProgressBar.visibility = View.GONE
        etaTextView.text = result.getTime()
        distanceTextView.text = result.getDistance()
        distanceTextView.visibility = View.VISIBLE
        etaLayout.visibility = View.VISIBLE
    }

}
