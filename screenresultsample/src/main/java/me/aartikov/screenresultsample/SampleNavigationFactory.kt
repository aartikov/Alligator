package me.aartikov.screenresultsample

import me.aartikov.alligator.navigationfactories.GeneratedNavigationFactory
import me.aartikov.screenresultsample.screens.ImagePickerScreen

// extends GeneratedNavigationFactory to register custom converters
class SampleNavigationFactory : GeneratedNavigationFactory() {

    init {
        registerActivityForResult(
            ImagePickerScreen::class.java,
            ImagePickerScreen.Result::class.java, ImagePickerScreen.Converter(), ImagePickerScreen.ResultConverter()
        )
    }
}
