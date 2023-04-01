package com.sketchart.sketchart.feature.sketch.presentation.screens.sketchgenerate

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sketchart.sketchart.feature.sketch.presentation.screens.components.ErrorView
import com.sketchart.sketchart.feature.sketch.presentation.screens.destinations.FullSketchScreenDestination

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination
fun SketchGenerateScreen(
    navigator: DestinationsNavigator,
    viewModel: SketchGenerateViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Generate",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = "Sketch.",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        if (state.generatedSketchList.isNullOrEmpty().not())
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(state.generatedSketchList!!) { index, sketch ->
                    val scaleFactor = if (index % 2 == 0) 1f else 1.5f
                    Card(
                        elevation = 3.dp,
                    ) {
                        AsyncImage(
                            model = sketch,
                            contentDescription = "",
                            modifier = Modifier
                                .height(300.dp * scaleFactor)
                                .fillMaxWidth()
                                .border(2.dp, Color.Black)
                                .padding(2.dp)
                                .clickable { navigator.navigate(FullSketchScreenDestination(imgUrl = sketch)) },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            Text(
                text = "Generating your sketch...",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
            )
        }
        if (state.error.isNotEmpty()) ErrorView(error = state.error)
    }
}