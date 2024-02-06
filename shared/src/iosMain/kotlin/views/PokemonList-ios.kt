package views

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getWindowSize(): IntSize {
    throw(Throwable("NOT IMPLEMENTED"))
}

actual val currentPlatform: KotlinPlatform = KotlinPlatform.IOS

@Composable
actual fun getWidth(): Int {
    throw(Throwable("NOT IMPLEMENTED"))
}

@Composable
actual fun getHeight(): Int {
    throw(Throwable("NOT IMPLEMENTED"))
}