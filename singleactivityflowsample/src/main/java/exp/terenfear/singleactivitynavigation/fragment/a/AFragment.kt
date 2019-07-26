package exp.terenfear.singleactivitynavigation.fragment.a

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_a.*
import me.aartikov.alligator.annotations.RegisterScreen
import exp.terenfear.singleactivitynavigation.App
import androidx.fragment.app.Fragment
import exp.terenfear.singleactivitynavigation.R
import exp.terenfear.singleactivitynavigation.flowcontainer.digit.DigitFlowScreen
import exp.terenfear.singleactivitynavigation.fragment.b.BScreen

/**
 * Date: 22.07.2019
 * Time: 19:34
 *
 * @author Terenfear
 */
@RegisterScreen(AScreen::class)
class AFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToB.setOnClickListener { App.navigator.goForward(BScreen()) }
        btnToFlow.setOnClickListener { App.navigator.addFlow(DigitFlowScreen()) }
    }

}
