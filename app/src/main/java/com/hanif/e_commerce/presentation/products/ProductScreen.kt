package com.hanif.e_commerce.presentation.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.hanif.e_commerce.domain.model.ProductEntity
import com.hanif.e_commerce.presentation.AddToCardScreen
import com.hanif.e_commerce.presentation.CardUnitHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel(),
    navController: NavHostController
) {

    var isSearchClick by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = viewModel.searchText, key2 = isSearchClick) {
        viewModel.searchItems()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        if (viewModel.productsList.isEmpty() && isSearchClick.not() && viewModel.isError.isEmpty()) {
            Column {
                TopAppBar(
                    title = { Text(text = "Hanif's E-commerce") },
                    actions = {
                        Row {
                            IconButton(onClick = { /*TODO*/ }) {

                                Icon(Icons.Outlined.Search, contentDescription = "Search")
                            }
                            IconButton(onClick = { /*TODO*/ }) {

                                Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart")
                            }
                        }
                    },
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    CircularProgressIndicator()
                }
            }
        }else {
            Column {
                TopAppBar(title = { Text(text = "Hanif's E-commerce") }, actions = {
                    Row {
                        IconButton(onClick = {
                            if (isSearchClick) {
                                viewModel.searchText = ""
                                isSearchClick = false
                            } else
                                isSearchClick = true
                        }) {
                            Icon(Icons.Outlined.Search, contentDescription = "Search")
                        }


                        BadgedBox(badge = {
                            if (viewModel.addToCardList.isNotEmpty()) Badge { Text(viewModel.addToCardList.size.toString()) }
                        }) {
                            IconButton(onClick = { navController.navigate(AddToCardScreen) }) {
                                Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart")
                            }
                        }
                    }
                })

                if (isSearchClick) {
                    OutlinedTextField(
                        value = viewModel.searchText,
                        onValueChange = {
                            viewModel.searchText = it
                        },
                        shape = RoundedCornerShape(20),
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "Search by id/name",
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Outlined.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            IconButton(onClick = { viewModel.searchText = "" }, Modifier.rotate(45f)) {
                                Icon(Icons.Outlined.AddCircle, contentDescription = "")
                            }
                        })
                    Spacer(modifier = Modifier.height(10.dp))
                }

                if (viewModel.isError.isNotEmpty()){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(
                            text = viewModel.isError,
                            maxLines = 3,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (viewModel.searchText.isNotEmpty() && viewModel.productsList.isEmpty())
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = (-20).dp), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Product not found. \n Make sure you enter right id/name.",
                            maxLines = 3,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                    if (viewModel.categoryList.isEmpty().not() && isSearchClick.not())
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            itemsIndexed(viewModel.categoryList) { index, it ->
                                AssistChip(
                                    border = AssistChipDefaults.assistChipBorder(enabled = false),
                                    onClick = { viewModel.changeCategory(it) },
                                    label = {
                                        Text(
                                            it.name,
                                            color = if (it.isSelection) Color.White else Color.Gray
                                        )
                                    },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = if (it.isSelection) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                                            alpha = .2f
                                        )
                                    )
                                )
                            }
                        }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        Modifier
                            .fillMaxSize()
                            .weight(1f),
                        //.padding(top = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        viewModel.apply {
                            items(productsList) {
                                ProductCard(Modifier, model = it, viewModel) {
                                    updateModel(
                                        it.copy(
                                            isInsideTheCart = !it.isInsideTheCart,
                                            unit = 1
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    model: ProductEntity,
    viewModel: ProductViewModel,
    onClickAction: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .padding(top = 5.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                AsyncImage(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .clip(RoundedCornerShape(bottomEndPercent = 8, bottomStartPercent = 8)),
                    model = model.image,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                )
                Column(modifier = modifier.padding(horizontal = 4.dp)) {
                    Text(
                        text = "Name: ${model.title}",
                        maxLines = 2,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "RS. ${model.price}", maxLines = 1, fontSize = 14.sp)
                }
            }
            if (model.isInsideTheCart) CardUnitHelper(count = model.unit, onClickAction = {
                if (it == 0) {
                    viewModel.updateModel(model.copy(isInsideTheCart = false, unit = 0))
                } else {
                    viewModel.updateModel(model.copy(unit = it))
                }
            }) {
                viewModel.updateModel(model.copy(isInsideTheCart = false, unit = 0))
            } else
                Button(onClick = { onClickAction.invoke() }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)) {
                    Text(text = "Add To Card")
                }
        }
    }
}

