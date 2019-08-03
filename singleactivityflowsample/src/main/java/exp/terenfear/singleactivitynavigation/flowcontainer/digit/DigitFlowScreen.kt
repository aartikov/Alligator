package exp.terenfear.singleactivitynavigation.flowcontainer.digit

import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import java.io.Serializable


class DigitFlowScreen : Screen {
    data class Result(val result: String) : ScreenResult, Serializable
}
