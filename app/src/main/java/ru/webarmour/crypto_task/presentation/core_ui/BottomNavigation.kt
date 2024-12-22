package ru.webarmour.crypto_task.presentation.core_ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.webarmour.crypto_task.R
import ru.webarmour.crypto_task.presentation.Screen
import ru.webarmour.crypto_task.presentation.theme.lightPrimary
import ru.webarmour.crypto_task.presentation.theme.primary
import ru.webarmour.crypto_task.presentation.theme.secondary
import ru.webarmour.crypto_task.presentation.theme.selectedTextColor

@Composable
fun BottomNavigationBar(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.drawBehind(onDraw = {
            drawLine(
                color = Color.Gray.copy(alpha = 1f),
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 0.5.dp.toPx()
            )
        }),
        containerColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.currencies), contentDescription = null) },
            label = { Text("Currencies") },
            selected = currentScreen is Screen.Currencies,
            onClick = { onScreenSelected(Screen.Currencies)},
            colors = NavigationBarItemColors(
                selectedIconColor = primary,
                selectedTextColor = selectedTextColor,
                selectedIndicatorColor = lightPrimary,
                unselectedIconColor = secondary,
                unselectedTextColor = secondary,
                disabledIconColor = secondary,
                disabledTextColor = secondary
            )

        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.favorites_on), contentDescription = null) },
            label = { Text("Favourites") },
            selected = currentScreen is Screen.Favourites,
            onClick = { onScreenSelected(Screen.Favourites) },
            colors = NavigationBarItemColors(
                selectedIconColor = primary,
                selectedTextColor = selectedTextColor,
                selectedIndicatorColor = lightPrimary,
                unselectedIconColor = secondary,
                unselectedTextColor = secondary,
                disabledIconColor = secondary,
                disabledTextColor = secondary
            )
        )
    }
}