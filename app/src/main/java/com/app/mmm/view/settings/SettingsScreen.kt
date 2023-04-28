package com.app.mmm.view.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.mmm.R
import com.app.mmm.view.settings.state.PeriodDuration
import com.app.mmm.view.settings.state.SettingsViewEvent

@Composable
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
fun SettingsScreen(
    viewModel: SettingsViewModel,
) {
    val viewState = viewModel.viewStates.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pushEnabled = viewState.value.pushEnabled
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.pushEnabled),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                )
            )
            Switch(
                checked = pushEnabled,
                onCheckedChange = {
                    viewModel.obtainEvent(SettingsViewEvent.PushChanging(it))
                }
            )
        }

        var expanded by remember { mutableStateOf(false) }
        val expandList = PeriodDuration.values().asList()
        val expandTitle = viewState.value.pushPeriod.title

        AnimatedVisibility(
            visible = pushEnabled,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                expanded = expanded,
                onExpandedChange = {
                    expanded = it
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = pushEnabled,
                    readOnly = true,
                    label = {
                        Text(
                            text = stringResource(id = R.string.pushPeriod),
                            style = TextStyle(
                                fontSize = 18.sp,
                            ),
                        )
                    },
                    value = stringResource(id = expandTitle),
                    onValueChange = { },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    shape = RoundedCornerShape(10.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    expandList.forEach { selectionPeriod ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.obtainEvent(SettingsViewEvent.PeriodChanging(period = selectionPeriod))
                                expanded = false
                            }
                        ) {
                            Text(text = stringResource(id = selectionPeriod.title))
                        }
                    }
                }
            }
        }
    }
}
