package com.app.mmm.view.productitem.subview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun ProductTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = label)
        },
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrect = true,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        onValueChange = { onValueChange(it) }
    )
}
