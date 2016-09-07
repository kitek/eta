package pl.kitek.eta.eta

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.input_place_fragment.*
import pl.kitek.eta.R
import pl.kitek.eta.common.data.model.Eta
import pl.kitek.eta.common.data.model.Location
import pl.kitek.eta.common.inflate
import timber.log.Timber

class InputPlaceFragment : Fragment(), View.OnClickListener {

    companion object {
        const val KEY_ETA = "deal"
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

        confirmBtn.setOnClickListener(this)
        cancelBtn.setOnClickListener(this)
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
                    showError("Something went wrong?")
                }
            }
        } else {
            showError("Something went wrong?")

            Timber.d("ka boom!")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, if (isStartPlace) "Your start location" else "Your destination")
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    private fun showError(message: String) {
        confirmTextView.text = message
    }

    private fun showConfirmation(spokenText: String) {
        confirmTextView.text = "Is this correct?\n$spokenText"
    }

    override fun onClick(view: View?) {
        val id = view?.id
        if (id == R.id.cancelBtn) {
            displaySpeechRecognizer()
        } else if (id == R.id.confirmBtn) {
            if (isStartPlace) {
                eta = Eta(Location(speechText!!, 0.0f, 0.0f), null)
            } else {
                eta!!.updateDestination(Location(speechText!!, 0f, 0f))
            }
            (activity as EtaActivity).goNext(eta!!)

        }
    }
}
