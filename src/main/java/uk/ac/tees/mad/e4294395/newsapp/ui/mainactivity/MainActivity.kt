package uk.ac.tees.mad.e4294395.newsapp.ui.mainactivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.e4294395.newsapp.ui.base.AppNavigationBar
import uk.ac.tees.mad.e4294395.newsapp.ui.splashscreen.SplashScreen
import uk.ac.tees.mad.e4294395.newsapp.ui.theme.NewsAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                NewsApp()
            }
        }
    }
}

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onTimeout = {
                navController.navigate("main") {
                    // Clear back stack to prevent returning to splash
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("main") {
            AppNavigationBar()
        }
    }
}