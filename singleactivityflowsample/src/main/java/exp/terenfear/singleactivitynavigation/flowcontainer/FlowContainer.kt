package exp.terenfear.singleactivitynavigation.flowcontainer

import me.aartikov.alligator.listeners.ScreenResultListener

/**
 * Date: 22.07.2019
 * Time: 19:34
 *
 * @author Terenfear
 */
interface FlowContainer {
    val flowContainerId: Int
    val flowResultListener: ScreenResultListener
}