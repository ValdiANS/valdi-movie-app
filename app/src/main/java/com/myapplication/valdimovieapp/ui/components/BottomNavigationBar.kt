package com.myapplication.valdimovieapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.myapplication.valdimovieapp.ui.route.NavigationRoute

@Composable
fun BottomNavigationBar(
    navigationRoutes: List<NavigationRoute>,
    onNavigationSelected: (String) -> Boolean,
    onNavigateScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
    ) {
        navigationRoutes.map { item ->
            NavigationBarItem(
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                selected = onNavigationSelected(item.screen.route),
                onClick = { onNavigateScreen(item.screen.route) },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                }
            )
        }
    }
}