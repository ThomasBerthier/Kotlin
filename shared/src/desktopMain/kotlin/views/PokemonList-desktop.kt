package views

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getWindowSize(): IntSize {
    return LocalWindowInfo.current.containerSize
}

actual val currentPlatform: KotlinPlatform = KotlinPlatform.DESKTOP

@Composable
actual fun getWidth(): Int {
    throw(Throwable("NOT IMPLEMENTED"))
}

@Composable
actual fun getHeight(): Int {
    throw(Throwable("NOT IMPLEMENTED"))
}