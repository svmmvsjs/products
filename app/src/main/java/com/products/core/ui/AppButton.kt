package com.products.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    type: AppButtonType = AppButtonType.Filled,
    onClick: () -> Unit
) {
    when (type) {
        AppButtonType.Filled -> Button(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            shape = MaterialTheme.shapes.small

        ) {
            ButtonContent(text = text)
        }

        AppButtonType.Outlined -> OutlinedButton(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),

            ) {
            ButtonContent(text = text)
        }

        AppButtonType.Text -> {
            TextButton(
                onClick = onClick,
                modifier = modifier.fillMaxWidth(),
                enabled = enabled,
                shape = MaterialTheme.shapes.small
            ) {
                ButtonContent(text = text)
            }
        }
    }
}

@Composable
private fun ButtonContent(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(8.dp),
    )
}

@Preview
@Composable
fun PreviewOutlinedButton() {
    AppTheme {
        AppButton(text = "Outlined", type = AppButtonType.Outlined) {
        }
    }
}

@Preview
@Composable
fun PreviewTextButton() {
    AppTheme {
        AppButton(text = "Text Button", type = AppButtonType.Text) {
        }
    }
}

@Preview(name = "Primary Button")
@Composable
fun PreviewAppButton() {
    AppTheme {
        AppButton(text = "Text") {}
    }
}

@Preview(name = "Disabled Button")
@Composable
fun PreviewDisabledAppButton() {
    AppTheme {
        AppButton(text = "Text", enabled = false) {}
    }
}

enum class AppButtonType {
    Filled, Outlined, Text
}