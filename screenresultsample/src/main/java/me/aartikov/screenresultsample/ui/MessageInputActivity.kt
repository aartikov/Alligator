package me.aartikov.screenresultsample.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.screenresultsample.R
import me.aartikov.screenresultsample.SampleApplication
import me.aartikov.screenresultsample.screens.MessageInputScreen

@RegisterScreen(value = MessageInputScreen::class, screenResult = MessageInputScreen.Result::class)
class MessageInputActivity : AppCompatActivity() {
    private val mNavigator: Navigator = SampleApplication.navigator
    private val mNavigationContextBinder: NavigationContextBinder = SampleApplication.navigationContextBinder

    private lateinit var mMessageEditText: EditText
    private lateinit var mOkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_input)

        mMessageEditText = findViewById(R.id.message_edit_text)
        mOkButton = findViewById(R.id.ok_button)

        mOkButton.setOnClickListener {
            val message = mMessageEditText.getText().toString()
            mNavigator.goBackWithResult(MessageInputScreen.Result(message)) // Easy-peasy!
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val navigationContext = NavigationContext.Builder(this, SampleApplication.navigationFactory)
            .build()
        mNavigationContextBinder.bind(navigationContext)
    }

    override fun onPause() {
        mNavigationContextBinder.unbind(this)
        super.onPause()
    }

    override fun onBackPressed() {
        mNavigator.goBack()
    }
}
