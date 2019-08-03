package exp.terenfear.singleactivitynavigation.fragment.f

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import exp.terenfear.singleactivitynavigation.App
import exp.terenfear.singleactivitynavigation.R
import exp.terenfear.singleactivitynavigation.flowcontainer.digit.DigitFlowScreen
import kotlinx.android.synthetic.main.fragment_f.*
import me.aartikov.alligator.annotations.RegisterScreen


@RegisterScreen(FScreen::class)
class FFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_f, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToF.setOnClickListener { App.navigator.goForward(FScreen()) }
        btnToFlow.setOnClickListener { App.navigator.addFlow(DigitFlowScreen()) }
    }

}
