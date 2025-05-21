package com.example.eraassignment.view

import Events
import LoadingScreen
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.eraassignment.components.Screen
import com.example.eraassignment.viewModel.SearchScreenViewModel
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen() {
    val searchViewModel = koinViewModel<SearchScreenViewModel>()
    val gridState = rememberLazyStaggeredGridState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        searchViewModel.getImages()
    }

    LaunchedEffect(gridState) {
        snapshotFlow {
            gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.filterNotNull()
            .collect { lastVisible ->
                if (lastVisible >= searchViewModel.images.value.size - 10) {
                    searchViewModel.goToNextPage()
                    searchViewModel.getImages()
                }
            }
    }

    LaunchedEffect(searchViewModel.eventFlow) {
        searchViewModel.eventFlow.collect { event ->
            when (event) {
                is Events.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    Screen(title = "Main", body = {
        Box {
            if (searchViewModel.isLoading.value) {
                LoadingScreen()
            }
            Column {
                BasicTextField(
                    value = searchViewModel.searchTerm.value,
                    onValueChange = { searchViewModel.searchTerm.value = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                if (searchViewModel.searchTerm.value.isEmpty()) Text(
                                    text = "Enter to search images...",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 24.sp,
                                    ),
                                )
                                innerTextField()
                            }
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        searchViewModel.setQuery(searchViewModel.searchTerm.value)
                                        searchViewModel.clearImages()
                                        searchViewModel.getImages()
                                    }
                            )
                            if (searchViewModel.searchTerm.value.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear icon",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            searchViewModel.searchTerm.value = ""
                                        }
                                )
                            }
                        }
                    }
                )
                if (searchViewModel.images.value.isEmpty() && !searchViewModel.isLoading.value) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "No images found",
                            modifier = Modifier.padding(16.dp).align(Alignment.Center)
                        )
                    }
                } else {
                    LazyVerticalStaggeredGrid(
                        state = gridState,
                        columns = StaggeredGridCells.Fixed(count = 3),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalItemSpacing = 4.dp,
                    ) {
                        items(searchViewModel.images.value, key = { photo ->
                            photo.id
                        }) { photo ->
                            AsyncImage(
                                model = photo.src.medium,
                                contentDescription = photo.alt,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        searchViewModel.setSelectedImage(photo)
                                    }
                            )
                        }
                    }
                }
            }
        }


        if (searchViewModel.selectedImage.value != null) {
            DetailView(
                image = searchViewModel.selectedImage.value!!,
                onDismiss = {
                    searchViewModel.setSelectedImage(null)
                }
            )
        }
    })
}