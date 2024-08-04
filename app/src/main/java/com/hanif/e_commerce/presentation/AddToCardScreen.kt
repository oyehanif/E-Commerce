package com.hanif.e_commerce.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.hanif.e_commerce.R
import com.hanif.e_commerce.domain.model.ProductEntity
import com.hanif.e_commerce.presentation.products.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToCardScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel(),
    navController: NavHostController
) {
    Column(Modifier.padding(10.dp)) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Search")
                }
            },
            title = { Text(text = "My Cart") },
        )

        if (viewModel.addToCardList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Empty Cart.",
                    maxLines = 3,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(viewModel.addToCardList) {
                    ProductCard(model = it, viewModel = viewModel)
                }
            }
        }
    }
}


@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    model: ProductEntity,
    viewModel: ProductViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
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
                        maxLines = 3,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "RS. ${model.price}", maxLines = 1, fontSize = 14.sp)
                }
            }
            CardUnitHelper(count = model.unit, onClickAction = {
                viewModel.apply {
                    if (it == 0) {
                        updateModel(model.copy(isInsideTheCart = false, unit = 0))
                    } else {
                        updateModel(model.copy(unit = it))
                    }
                }
            }, onDelete = {
                viewModel.updateModel(model.copy(isInsideTheCart = false, unit = 0))
            })
        }
    }
}


@Composable
fun CardUnitHelper(
    modifier: Modifier = Modifier,
    count: Int,
    onClickAction: (Int) -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = {
                if (count > 0) onClickAction.invoke(count - 1)
            },
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(painter = painterResource(id = R.drawable.outline_remove_24), contentDescription = "", tint = Color.White)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = count.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold,modifier=Modifier.padding(horizontal = 4.dp))
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(
            onClick = {
                onClickAction.invoke(count + 1)
            },
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Outlined.Add, contentDescription = "", tint = Color.White)
        }
        IconButton(
            onClick = { onDelete.invoke() },
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Outlined.Delete, contentDescription = "", tint = Color.Red)
        }

    }
}