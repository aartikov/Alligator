package exp.terenfear.singleactivitynavigation.flowcontainer.digit

import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import java.io.Serializable

/**
 * Date: 22.07.2019
 * Time: 19:34
 *
 * @author Terenfear
 */
class DigitFlowScreen : Screen {
    data class Result(val result: String) : ScreenResult, Serializable
}
