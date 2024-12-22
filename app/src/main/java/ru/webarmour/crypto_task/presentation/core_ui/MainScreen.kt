package ru.webarmour.crypto_task.presentation.core_ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.webarmour.crypto_task.presentation.MainViewModel
import ru.webarmour.crypto_task.presentation.Screen

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val viewModel: MainViewModel = hiltViewModel()
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Currencies) }

    val showBottomNavigation = remember { mutableStateOf(true) }

    Scaffold(

        bottomBar = {

            if (showBottomNavigation.value) {
                BottomNavigationBar(
                    currentScreen = currentScreen,
                    onScreenSelected = { screen ->
                        currentScreen = screen
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                        Log.d("MainScreen", "Navigated to ${screen.route}")
                    }
                )
            }
        }
    ) { padding ->
        AppNavHost(
            navController = navController,
            startDestination = Screen.Currencies.route,
            modifier = Modifier.padding(padding),
            showBottomNavigation
        )
    }
}