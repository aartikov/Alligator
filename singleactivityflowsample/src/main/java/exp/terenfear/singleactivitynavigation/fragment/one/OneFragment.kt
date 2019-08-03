package exp.terenfear.singleactivitynavigation.fragment.one

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import exp.terenfear.singleactivitynavigation.App
import exp.terenfear.singleactivitynavigation.R
import exp.terenfear.singleactivitynavigation.fragment.two.TwoScreen
import kotlinx.android.synthetic.main.fragment_one.*
import me.aartikov.alligator.annotations.RegisterScreen


@RegisterScreen(OneScreen::class)
class OneFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToTwo.setOnClickListener { App.navigator.goForward(TwoScreen()) }
    }

}