package com.app.mmm.view.productitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.mmm.R
import com.app.mmm.view.productitem.state.ProductItemViewAction
import com.app.mmm.view.productitem.state.ProductItemViewEvent
import com.app.mmm.view.productitem.subview.ProductTextField

@Composable
@ExperimentalComposeUiApi
fun ProductItem(
    itemTag: String,
    viewModel: ProductItemViewModel,
    onBackClick: () -> Unit,
) {
    val viewState by viewModel.viewStates.collectAsState()
    val viewAction by viewModel.viewActions.collectAsState(initial = null)

    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    val keyboardActions = KeyboardActions(
        onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
        onDone = {
            localFocusManager.clearFocus()
            keyboardController?.hide()
        }
    )

    when (viewAction) {
        ProductItemViewAction.SaveAction -> onBackClick()
        null -> {}
    }

    val buttonIsEnabled by remember {
        derivedStateOf {
            viewState.productName.isNotEmpty() && viewState.productCaloric.isNotEmpty()
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = if (itemTag == "new") stringResource(id = R.string.new_product)
                    else stringResource(id = R.string.editing)
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        onBackClick()
                    }
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                if (itemTag != "new") {
                    IconButton(
                        onClick = { viewModel.obtainEvent(ProductItemViewEvent.DeletingProductItem) }
                    ) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White)
                    }
                }
            }
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProductTextField(
                value = viewState.productName,
                label = stringResource(id = R.string.product_name_title),
                onValueChange = {
                    viewModel.obtainEvent(ProductItemViewEvent.ChangingProductName(it))
                },
                keyboardActions = keyboardActions,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
            ProductTextField(
                value = viewState.productCaloric,
                label = stringResource(id = R.string.product_caloric_title),
                onValueChange = {
                    viewModel.obtainEvent(ProductItemViewEvent.ChangingProductCaloric(it))
                },
                keyboardActions = keyboardActions,
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    viewModel.obtainEvent(ProductItemViewEvent.SaveProduct)
                },
                enabled = buttonIsEnabled
            ) {
                Text(text = stringResource(id = R.string.save_product))
            }
        }
    }
}
