package me.aartikov.alligator.converters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import me.aartikov.alligator.Screen
import java.io.Serializable

/**
 * Creates an intent that starts an activity of the given class. It also puts a screen to the intent's extra if `ScreenT` is `Serializable` or `Parcelable`.
 *
 * @param <ScreenT> screen type
</ScreenT> */
class DefaultIntentConverter<ScreenT : Screen?>(
    private val mScreenClass: Class<ScreenT>,
    private val mActivityClass: Class<out Activity?>
) : IntentConverter<ScreenT> {
    override fun createIntent(context: Context, screen: ScreenT): Intent {
        val intent = Intent(context, mActivityClass)
        if (screen is Serializable) {
            intent.putExtra(KEY_SCREEN, screen as Serializable)
        } else if (screen is Parcelable) {
            intent.putExtra(KEY_SCREEN, screen as Parcelable)
        }
        return intent
    }

    override fun getScreen(intent: Intent): ScreenT? {
        return if (Serializable::class.java.isAssignableFrom(mScreenClass)) {
            intent.getSerializableExtra(KEY_SCREEN) as ScreenT?
        } else if (Parcelable::class.java.isAssignableFrom(mScreenClass)) {
            intent.getParcelableExtra<Parcelable>(KEY_SCREEN) as ScreenT?
        } else {
            throw IllegalArgumentException("Screen " + mScreenClass.simpleName + " should be Serializable or Parcelable.")
        }
    }

    companion object {
        private const val KEY_SCREEN = "me.aartikov.alligator.KEY_SCREEN"
    }
}