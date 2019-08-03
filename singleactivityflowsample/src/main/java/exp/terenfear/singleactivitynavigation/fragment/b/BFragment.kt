package exp.terenfear.singleactivitynavigation.fragment.b

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import exp.terenfear.singleactivitynavigation.App
import exp.terenfear.singleactivitynavigation.R
import exp.terenfear.singleactivitynavigation.fragment.c.CScreen
import exp.terenfear.singleactivitynavigation.fragment.f.FScreen
import kotlinx.android.synthetic.main.fragment_b.*
import me.aartikov.alligator.annotations.RegisterScreen


@RegisterScreen(BScreen::class)
class BFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToC.setOnClickListener { App.navigator.goForward(CScreen()) }
        btnToF.setOnClickListener { App.navigator.goForward(FScreen()) }
    }

}
