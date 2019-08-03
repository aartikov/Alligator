package exp.terenfear.singleactivitynavigation.fragment.c

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import exp.terenfear.singleactivitynavigation.App
import exp.terenfear.singleactivitynavigation.R
import exp.terenfear.singleactivitynavigation.fragment.d.DScreen
import exp.terenfear.singleactivitynavigation.fragment.e.EScreen
import kotlinx.android.synthetic.main.fragment_c.*
import me.aartikov.alligator.annotations.RegisterScreen


@RegisterScreen(CScreen::class)
class CFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_c, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToE.setOnClickListener { App.navigator.goForward(EScreen()) }
        btnToD.setOnClickListener { App.navigator.replace(DScreen()) }
    }

}
