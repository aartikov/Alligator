package me.aartikov.simplenavigationsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.simplenavigationsample.R
import me.aartikov.simplenavigationsample.SampleApplication
import me.aartikov.simplenavigationsample.screens.ScreenA
import me.aartikov.simplenavigationsample.screens.ScreenD

@RegisterScreen(ScreenD::class)
class FragmentD : Fragment() {
    private val mNavigator = SampleApplication.navigator
    private val mScreenResolver = SampleApplication.screenResolver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val messageTextView = view.findViewById<TextView>(R.id.message_text_view)
        val goBackToAButton = view.findViewById<Button>(R.id.go_back_to_a_button)
        val screen = mScreenResolver.getScreen<ScreenD>(this) // use ScreenResolver to get a screen with its arguments
        messageTextView.text = screen.message
        goBackToAButton.setOnClickListener { mNavigator.goBackTo(ScreenA::class.java) }
    }
}
