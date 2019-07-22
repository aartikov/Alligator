package exp.terenfear.singleactivitynavigation.flowcontainer.digit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_flow.*
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.alligator.listeners.ScreenResultListener
import exp.terenfear.singleactivitynavigation.App
import androidx.fragment.app.Fragment
import exp.terenfear.singleactivitynavigation.R
import exp.terenfear.singleactivitynavigation.flowcontainer.FlowContainer
import exp.terenfear.singleactivitynavigation.fragment.one.OneScreen

/**
 * Date: 22.07.2019
 * Time: 19:34
 *
 * @author Terenfear
 */
@RegisterScreen(DigitFlowScreen::class, screenResult = DigitFlowScreen.Result::class)
class DigitFlowFragment : Fragment(), FlowContainer {

    override val flowContainerId: Int = R.id.flowContainer
    override val flowResultListener = ScreenResultListener { screenClass, result ->
        Log.d(this@DigitFlowFragment.javaClass.simpleName, "Screen result $result from ${screenClass.simpleName}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vFlowTitle.text = this.javaClass.simpleName

        if (childFragmentManager.findFragmentById(flowContainerId) == null) {
            App.navigator.reset(OneScreen())
        }
    }

}
