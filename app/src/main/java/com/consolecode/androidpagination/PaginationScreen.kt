package com.consolecode.androidpagination


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch


@Composable
fun PaginationScreen(context: MainActivity, viewModel: PostViewModel = viewModel()) {
    val posts = viewModel.posts.collectAsState()
    val loading by viewModel.loading
    val lazyListState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pagination View",
                    fontSize = 18.sp, modifier = Modifier.weight(.6f)
                )
                Button(modifier = Modifier
                    .padding(1.dp), onClick = { context.finish() }) {
                    Icon(
                        modifier = Modifier
                            .padding(1.dp)
                            .size(20.dp),
                        tint = Color.White,
                        painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                        contentDescription = ""
                    )
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp),
                state = lazyListState,
            ) {
                Alignment.Center
                itemsIndexed(posts.value) { index, post ->
                    PostItem(context, index, post)
                    if (index == posts.value.size - 1 && !loading) {
                        viewModel.loadNextPage()
                    }
                }

                // Add a loading indicator at the end of the list
                if (loading) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }

        }
        // LoadMoreButton(onClick = viewModel::loadNextPage)
        LoadDutton(onClick = {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(lazyListState.firstVisibleItemScrollOffset)
            }
        }
        )
    }
}

@Preview
@Composable
fun PostItem(context: MainActivity, index: Int, post: MediaCoverage?) {
    val painter =
        rememberAsyncImagePainter(post?.thumbnail?.domain + "/" + post?.thumbnail?.basePath + "/0/" + post?.thumbnail?.key)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                // Open web page in WebView
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post?.coverageURL))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box(Modifier.height(200.dp)) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                Modifier
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    )
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                        .align(Alignment.Center),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(5.dp)
                                .drawBehind {
                                    drawCircle(
                                        color = Color.White,
                                        radius = this.size.minDimension
                                    )
                                },
                            text = "${index + 1}"
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {

                            Text(
                                text = "${post?.title}",
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(0.dp),
                                fontSize = 12.sp,
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun LoadDutton(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(30.dp)
                .size(50.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 16.dp, y = 16.dp),
            onClick = onClick,
            shape = CircleShape,

            ) {
            Icon(Icons.Filled.KeyboardArrowDown, "")
            Spacer(modifier = Modifier.height(16.dp))
            //Text(text = "More")
        }
    }
}