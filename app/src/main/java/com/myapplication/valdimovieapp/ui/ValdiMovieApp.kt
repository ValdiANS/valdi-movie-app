package com.myapplication.valdimovieapp.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.myapplication.valdimovieapp.R
import com.myapplication.valdimovieapp.ui.components.BottomNavigationBar
import com.myapplication.valdimovieapp.ui.pages.detailmovie.DetailMovie
import com.myapplication.valdimovieapp.ui.pages.favorite.FavoriteScreen
import com.myapplication.valdimovieapp.ui.pages.home.HomeScreen
import com.myapplication.valdimovieapp.ui.route.NavigationRoute
import com.myapplication.valdimovieapp.ui.route.Screen
import com.myapplication.valdimovieapp.ui.theme.ValdiMovieAppTheme
import com.myapplication.valdimovieapp.ui.theme.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValdiMovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationRoutes = listOf(
        NavigationRoute(
            title = stringResource(R.string.home_nav_title),
            icon = Icons.Outlined.Home,
            screen = Screen.Home
        ),
        NavigationRoute(
            title = stringResource(R.string.favorite_nav_title),
            icon = Icons.Outlined.FavoriteBorder,
            screen = Screen.Favorite
        ),
//        NavigationRoute(
//            title = stringResource(R.string.about_nav_title),
//            icon = Icons.Outlined.Person,
//            screen = Screen.About
//        ),
    )

    Scaffold(
        topBar = {
            if (currentRoute != Screen.DetailMovie.route) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                // move to about screen
                                try {
                                    val uri = Uri.parse("valdimovieapp://about")
                                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Module not found", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = stringResource(R.string.about),
                            )
                        }
                    }
                )
            }
        },

        bottomBar = {
            if (currentRoute != Screen.DetailMovie.route) {
                BottomNavigationBar(
                    navigationRoutes = navigationRoutes,
                    onNavigationSelected = { route -> currentRoute == route },
                    onNavigateScreen = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onDetailClick = {
                        navController.navigate(Screen.DetailMovie.createRoute(it))
                    }
                )
            }

            composable(Screen.Search.route) {
                Text(text = "Search")
            }

            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    onDetailClick = {
                        navController.navigate(Screen.DetailMovie.createRoute(it))
                    }
                )
            }

            composable(Screen.About.route) {
                Text(text = "About")
            }

            composable(
                route = Screen.DetailMovie.route,
                arguments = listOf(
                    navArgument("movieId") { type = NavType.StringType }
                )
            ) {
                val movieId = it.arguments?.getString("movieId") ?: ""


                DetailMovie(
                    movieId = movieId,
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ValdiMovieAppPreview() {
    ValdiMovieAppTheme {
        ValdiMovieApp()
    }
}