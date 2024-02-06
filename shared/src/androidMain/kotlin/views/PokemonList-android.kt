package views

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntSize

@Composable
actual fun getWidth(): Int {
    return LocalConfiguration.current.screenWidthDp
}

@Composable
actual fun getHeight(): Int {
    return LocalConfiguration.current.screenHeightDp
}

actual val currentPlatform: KotlinPlatform = KotlinPlatform.ANDROID

@Composable
actual fun getWindowSize(): IntSize {
    throw(Throwable("NOT IMPLEMENTED"))
}