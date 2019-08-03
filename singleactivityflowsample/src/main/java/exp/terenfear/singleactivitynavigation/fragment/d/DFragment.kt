package exp.terenfear.singleactivitynavigation.fragment.d

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import exp.terenfear.singleactivitynavigation.App
import exp.terenfear.singleactivitynavigation.R
import exp.terenfear.singleactivitynavigation.fragment.a.AScreen
import kotlinx.android.synthetic.main.fragment_d.*
import me.aartikov.alligator.annotations.RegisterScreen


@RegisterScreen(DScreen::class)
class DFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToA.setOnClickListener { App.navigator.goBackTo(AScreen::class.java) }
    }

}
