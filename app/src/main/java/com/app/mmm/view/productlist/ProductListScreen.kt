package com.app.mmm.view.productlist

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.mmm.R

@Composable
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun ProductListScreen(
    viewModel: ProductListViewModel,
    onAddClick: (String) -> Unit,
) {
    val productList = viewModel.productList.collectAsState(initial = emptyMap())
    val viewState = viewModel.viewStates.collectAsState()
    val lazyState = rememberLazyListState()

    val fabVisibility by remember {
        derivedStateOf {
            lazyState.firstVisibleItemIndex > 0
        }
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = !fabVisibility,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                FloatingActionButton(
                    onClick = {
                        onAddClick("new")
                    },
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = lazyState
        ) {
            if (viewState.value.isLoading) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.loading))
                        CircularProgressIndicator()
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(2.dp)) }
            productList.value.entries.forEach { (header, list) ->
                stickyHeader(key = header.date) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp)
                            .background(
                                color = colorResource(id = R.color.purple_200),
                                shape = RoundedCornerShape(4.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = header.date,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Всего: ${header.totalCaloric} кКал",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                }
                items(items = list, key = { item -> item.id }) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(start = 8.dp, end = 8.dp)
                            .clickable {
                                onAddClick(product.id)
                            },
                        elevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp),
                                text = product.name,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp),
                                text = "${product.caloric} кКал",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(2.dp)) }
            }
        }
    }
}
