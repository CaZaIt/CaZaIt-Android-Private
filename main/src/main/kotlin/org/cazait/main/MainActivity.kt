package org.cazait.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.cazait.feature.navigation.MainRoutes

class MainActivity : ComponentActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }

    @Composable
    private fun NavGraph(
        navController: NavHostController,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Scaffold(
            bottomBar = {
                if (MainRoutes.isMainRoute(currentRoute)) {
                    MainBottomBar(
                        navController = navController,
                        currentRoute = currentRoute,
                    )
                }
            },
        ) { paddingValues ->
        }
    }
}
