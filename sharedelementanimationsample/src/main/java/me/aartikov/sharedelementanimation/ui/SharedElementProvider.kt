package me.aartikov.sharedelementanimation.ui

import android.view.View
import me.aartikov.alligator.animations.AnimationData

interface SharedElementProvider {
    fun getSharedElement(animationData: AnimationData?): View

    fun getSharedElementName(animationData: AnimationData?): String
}
