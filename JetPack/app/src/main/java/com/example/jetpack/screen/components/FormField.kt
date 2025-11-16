package com.example.jetpack.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.ui.theme.JetPackTheme

@Composable
fun FormField(
    label: String,
    textState: String,
    onTextChange: (String) -> Unit,
    isPasswordField: Boolean = false,
    isNumber: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // External label text
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp).width(100.dp)
        )

        if (!isNumber) {
            OutlinedTextField(
                value = textState,
                onValueChange = onTextChange,
                modifier = Modifier.weight(1f),
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                visualTransformation = (
                        if (isPasswordField)
                            PasswordVisualTransformation()
                        else
                            VisualTransformation.None
                        ),
                placeholder = { Text("Enter $label") }
            )
        } else {
            NumericOutlinedTextField(
                label = label,
                value = textState,
                onValueChange = onTextChange,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun NumericOutlinedTextField(
    label: String,
    value: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        modifier = modifier,
        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
        placeholder = { Text("Enter $label") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None
    )
}

@Preview(showBackground = true)
@Composable
fun FormFieldPreview() {
    JetPackTheme {
        Column {
            FormField("Email", "", {})
            FormField("Password", "", {}, isPasswordField = true)
            FormField("Age", "", {}, isNumber = true)
        }
    }
}
