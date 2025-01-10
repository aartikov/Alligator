package me.aartikov.sharedelementanimation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.sharedelementanimation.R
import me.aartikov.sharedelementanimation.SampleApplication
import me.aartikov.sharedelementanimation.screens.FirstScreen
import me.aartikov.sharedelementanimation.screens.SecondScreen

@RegisterScreen(FirstScreen::class)
class FirstFragment : Fragment(), SharedElementProvider {

    private lateinit var mKittenImageViews: Array<ImageView>

    private val mNavigator: Navigator = SampleApplication.navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mKittenImageViews = arrayOf(
            view.findViewById(R.id.kitten_image_view_0),
            view.findViewById(R.id.kitten_image_view_1)
        )

        for (i in mKittenImageViews.indices) {
            val kittenIndex = i
            mKittenImageViews[i].setOnClickListener {
                mNavigator.goForward(SecondScreen(kittenIndex), KittenAnimationData(kittenIndex))
            }
        }
    }

    override fun getSharedElement(animationData: AnimationData?): View {
        val kittenAnimationData = animationData as KittenAnimationData
        return mKittenImageViews[kittenAnimationData.kittenIndex]
    }

    override fun getSharedElementName(animationData: AnimationData?): String {
        return "kitten"
    }
}
