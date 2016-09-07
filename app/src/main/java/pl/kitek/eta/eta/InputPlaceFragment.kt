package pl.kitek.eta.eta

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.wearable.view.DelayedConfirmationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.input_place_fragment.*
import pl.kitek.eta.R
import pl.kitek.eta.common.data.model.Eta
import pl.kitek.eta.common.data.model.Location
import pl.kitek.eta.common.inflate

class InputPlaceFragment : Fragment(), DelayedConfirmationView.DelayedConfirmationListener {

    companion object {
        const val DECISION_TIMEOUT = 5000L // 5 sek
        const val KEY_ETA = "eta"
        const val SPEECH_REQUEST_CODE = 0

        fun newInstance(eta: Eta): InputPlaceFragment {
            val fragment = InputPlaceFragment()
            fragment.arguments = Bundle()
            fragment.arguments.putParcelable(KEY_ETA, eta)
            return fragment
        }
    }

    private var eta: Eta? = null
    private var isStartPlace = true
    private var speechText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eta = it.getParcelable<Eta>(KEY_ETA)
        }
        isStartPlace = (null == eta || null == eta?.origin)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup, savedInstanceState: Bundle?): View {
        return container.inflate(R.layout.input_place_fragment)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displaySpeechRecognizer()

        confirmBtn.setListener(this)
        confirmBtn.setTotalTimeMs(DECISION_TIMEOUT)

        cancelBtn.setListener(this)
        cancelBtn.setTotalTimeMs(DECISION_TIMEOUT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        speechText = ""
        if (requestCode === SPEECH_REQUEST_CODE && resultCode === Activity.RESULT_OK) {
            data?.let {
                val results = it.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (null != results) {
                    speechText = results[0]
                    showConfirmation(results[0])
                } else {
                    showError(getString(R.string.error))
                }
            }
        } else {
            showError(getString(R.string.error))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun displaySpeechRecognizer() {
        if (!isAdded) return

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, if (isStartPlace)
            getString(R.string.start_location) else getString(R.string.end_location)
        )
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    private fun showError(message: String) {
        confirmTextView.text = message
        cancelBtn.start()
    }

    private fun showConfirmation(spokenText: String) {
        confirmTextView.text = getString(R.string.is_correct) + "\n" + spokenText
        confirmBtn.start()
    }

    private fun onButtonClicked(id: Int) {
        if (!isAdded) return
        confirmBtn.reset()
        cancelBtn.reset()

        when (id) {
            R.id.cancelBtn -> displaySpeechRecognizer()
            R.id.confirmBtn -> {
                speechText?.let {
                    if (isStartPlace) {
                        eta = Eta(Location(it, 0.0f, 0.0f), null)
                    } else {
                        eta!!.updateDestination(Location(it, 0f, 0f))
                    }
                    (activity as EtaActivity).goNext(eta!!)
                }
            }
        }
    }

    override fun onTimerSelected(v: View?) {
        v?.let {
            onButtonClicked(it.id)
        }
    }

    override fun onTimerFinished(v: View?) {
        v?.let {
            onButtonClicked(it.id)
        }
    }

}
