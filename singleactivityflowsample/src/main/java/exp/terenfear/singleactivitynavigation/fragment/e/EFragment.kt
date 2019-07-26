package exp.terenfear.singleactivitynavigation.fragment.e

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_a.btnToB
import kotlinx.android.synthetic.main.fragment_e.*
import me.aartikov.alligator.annotations.RegisterScreen
import exp.terenfear.singleactivitynavigation.App
import androidx.fragment.app.Fragment
import exp.terenfear.singleactivitynavigation.R
import exp.terenfear.singleactivitynavigation.fragment.b.BScreen
import exp.terenfear.singleactivitynavigation.fragment.c.CScreen
import exp.terenfear.singleactivitynavigation.fragment.d.DScreen

/**
 * Date: 22.07.2019
 * Time: 19:35
 *
 * @author Terenfear
 */
@RegisterScreen(EScreen::class)
class EFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_e, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToB.setOnClickListener { App.navigator.goBackTo(BScreen::class.java) }
        btnToD.setOnClickListener {
            App.navigator.goBackTo(CScreen::class.java)
            App.navigator.replace(DScreen())
        }
    }

}
