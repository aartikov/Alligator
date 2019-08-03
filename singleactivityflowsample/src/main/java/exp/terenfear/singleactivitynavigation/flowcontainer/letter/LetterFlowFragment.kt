package exp.terenfear.singleactivitynavigation.flowcontainer.letter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import exp.terenfear.singleactivitynavigation.App
import exp.terenfear.singleactivitynavigation.R
import exp.terenfear.singleactivitynavigation.flowcontainer.FlowContainer
import exp.terenfear.singleactivitynavigation.fragment.a.AScreen
import kotlinx.android.synthetic.main.fragment_flow.*
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.alligator.listeners.ScreenResultListener


@RegisterScreen(LetterFlowScreen::class)
class LetterFlowFragment : Fragment(), FlowContainer {

    override val flowContainerId: Int = R.id.flowContainer
    override val flowResultListener = ScreenResultListener { screenClass, result ->
        Log.d(this@LetterFlowFragment.javaClass.simpleName, "Screen result $result from ${screenClass.simpleName}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vFlowTitle.text = this.javaClass.simpleName

        if (childFragmentManager.findFragmentById(flowContainerId) == null) {
            App.navigator.reset(AScreen())
        }
    }

}
