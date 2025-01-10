package me.aartikov.simplescreenswitchersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.simplescreenswitchersample.R
import me.aartikov.simplescreenswitchersample.screens.TabScreen.Android

@RegisterScreen(Android::class)
class AndroidFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_android, container, false)
    }
}
