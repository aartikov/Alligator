package me.aartikov.sharedelementanimation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.sharedelementanimation.R
import me.aartikov.sharedelementanimation.SampleApplication
import me.aartikov.sharedelementanimation.screens.SecondScreen

@RegisterScreen(SecondScreen::class)
class SecondFragment : Fragment(), SharedElementProvider {

    private lateinit var mKittenImageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mKittenImageView = view.findViewById(R.id.kitten_image_view)

        val screen = SampleApplication.screenResolver.getScreen<SecondScreen>(this)
        mKittenImageView.setImageResource(if (screen.kittenIndex == 0) R.drawable.kitten_0 else R.drawable.kitten_1)
        mKittenImageView.setOnClickListener { SampleApplication.navigator.goBack() }
    }

    override fun getSharedElement(animationData: AnimationData?): View {
        return mKittenImageView
    }

    override fun getSharedElementName(animationData: AnimationData?): String {
        val screen = SampleApplication.screenResolver.getScreen<SecondScreen>(this)
        return "kitten_" + screen.kittenIndex
    }
}
