package me.aartikov.screenresultsample.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import me.aartikov.alligator.ActivityResultHandler
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.alligator.listeners.ScreenResultListener
import me.aartikov.screenresultsample.R
import me.aartikov.screenresultsample.SampleApplication
import me.aartikov.screenresultsample.screens.ImagePickerScreen
import me.aartikov.screenresultsample.screens.MainScreen
import me.aartikov.screenresultsample.screens.MessageInputScreen

@RegisterScreen(MainScreen::class)
class MainActivity : AppCompatActivity(), ScreenResultListener {
    private val mNavigator: Navigator = SampleApplication.navigator
    private val mNavigationContextBinder: NavigationContextBinder = SampleApplication.navigationContextBinder
    private val mActivityResultHandler: ActivityResultHandler = SampleApplication.activityResultHandler

    private lateinit var mInputMessageButton: Button
    private lateinit var mPickImageButton: Button
    private lateinit var mMessageTextView: TextView
    private lateinit var mImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mInputMessageButton = findViewById(R.id.input_message_button)
        mPickImageButton = findViewById(R.id.pick_image_button)
        mMessageTextView = findViewById(R.id.message_text_view)
        mImageView = findViewById(R.id.image_view)

        // goForward works as startActivityForResult here
        mInputMessageButton.setOnClickListener { mNavigator.goForward(MessageInputScreen()) }
        mPickImageButton.setOnClickListener { mNavigator.goForward(ImagePickerScreen()) }
    }

    private fun onMessageInputted(messageInputResult: MessageInputScreen.Result) {
        mMessageTextView.text = getString(R.string.inputted_message_template, messageInputResult.message)
    }

    private fun onImagePicked(imagePickerResult: ImagePickerScreen.Result) {
        Picasso.with(this).load(imagePickerResult.uri).into(mImageView)
    }

    override fun onScreenResult(screenClass: Class<out Screen?>, result: ScreenResult?) {
        if (result == null) {
            Toast.makeText(this@MainActivity, getString(R.string.cancelled), Toast.LENGTH_SHORT).show()
            return
        }

        if (result is MessageInputScreen.Result) {
            onMessageInputted(result)
        } else if (result is ImagePickerScreen.Result) {
            onImagePicked(result)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val navigationContext = NavigationContext.Builder(this, SampleApplication.navigationFactory)
            .screenResultListener(this) // set ScreenResultListener
            .build()
        mNavigationContextBinder.bind(navigationContext)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mActivityResultHandler.onNewIntent(intent) // handle result
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mActivityResultHandler.onActivityResult(requestCode, resultCode, data) // handle result
    }

    override fun onPause() {
        mNavigationContextBinder.unbind(this)
        super.onPause()
    }

    override fun onBackPressed() {
        mNavigator.goBack()
    }
}
