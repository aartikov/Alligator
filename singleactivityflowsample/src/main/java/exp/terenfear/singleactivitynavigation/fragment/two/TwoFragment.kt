package exp.terenfear.singleactivitynavigation.fragment.two

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import exp.terenfear.singleactivitynavigation.App
import exp.terenfear.singleactivitynavigation.R
import exp.terenfear.singleactivitynavigation.flowcontainer.digit.DigitFlowScreen
import exp.terenfear.singleactivitynavigation.flowcontainer.letter.LetterFlowScreen
import kotlinx.android.synthetic.main.fragment_two.*
import me.aartikov.alligator.annotations.RegisterScreen
import java.util.*


@RegisterScreen(TwoScreen::class)
class TwoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToPrevFlow.setOnClickListener { App.navigator.goBackToFlowWithResult(LetterFlowScreen::class.java, DigitFlowScreen.Result(Random().nextInt().toString())) }
        btnToPrevFlow.setOnClickListener { App.navigator.finishFlowWithResult(DigitFlowScreen.Result(Random().nextInt().toString())) }
    }

}