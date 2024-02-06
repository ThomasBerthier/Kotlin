import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import views.rootNavHost

//private val quizRepository = QuizRepository()
//private val offlineQuizRepository = OfflineQuizRepository()
@Composable
fun App() {
    MaterialTheme {
        PreComposeApp{ rootNavHost() }
    }
}