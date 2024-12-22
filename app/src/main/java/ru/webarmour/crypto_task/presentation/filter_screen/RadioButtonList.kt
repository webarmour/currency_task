package ru.webarmour.crypto_task.presentation.filter_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.webarmour.crypto_task.domain.util.CurrencyOrder
import ru.webarmour.crypto_task.presentation.theme.header
import ru.webarmour.crypto_task.presentation.theme.primary
import ru.webarmour.crypto_task.presentation.theme.secondary
import ru.webarmour.crypto_task.presentation.theme.textDefaults

@Composable
fun RadioButtonList(
    sortOptions: List<SortOption>,
    selectedOption: CurrencyOrder,
    textColor: Color = textDefaults,
    backgroundColor: Color = header,
    onOptionSelected: (CurrencyOrder) -> Unit,
) {
    LazyColumn {
        items(sortOptions) { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(backgroundColor)
                    .clickable {
                        Log.d("MyTag", "Clicked option: ${option.currencyOrder}")
                        onOptionSelected(option.currencyOrder)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = option.label,
                    color = textColor, // Пример цвета текста
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                    fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing
                )
                RadioButton(
                    colors = RadioButtonColors(
                        selectedColor = primary,
                        unselectedColor = secondary,
                        disabledSelectedColor = primary,
                        disabledUnselectedColor = secondary
                    ),
                    selected = selectedOption == option.currencyOrder,
                    onClick = {
                        onOptionSelected(option.currencyOrder)
                        Log.d("MyTag", "${option.currencyOrder}")
                    }
                )
            }
        }
    }
}