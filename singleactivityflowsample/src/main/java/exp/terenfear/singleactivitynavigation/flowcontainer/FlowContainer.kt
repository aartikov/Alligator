package exp.terenfear.singleactivitynavigation.flowcontainer

import me.aartikov.alligator.listeners.ScreenResultListener


interface FlowContainer {
    val flowContainerId: Int
    val flowResultListener: ScreenResultListener
}