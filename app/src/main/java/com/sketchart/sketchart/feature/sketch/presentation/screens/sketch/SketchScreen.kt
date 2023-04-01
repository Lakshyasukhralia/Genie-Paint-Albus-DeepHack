package com.sketchart.sketchart.feature.sketch.presentation.screens.sketch

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sketchart.sketchart.R
import com.sketchart.sketchart.feature.sketch.presentation.screens.components.ErrorView
import com.sketchart.sketchart.feature.sketch.presentation.screens.components.InputBox
import com.sketchart.sketchart.feature.sketch.presentation.screens.destinations.SketchGenerateScreenDestination

@Composable
@RootNavGraph(start = true)
@Destination
fun SketchScreen(
    navigator: DestinationsNavigator,
    viewModel: SketchViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var subject by remember {
        mutableStateOf("")
    }

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Upload",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = "Picture.",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(50.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(5.dp)
                    .border(1.dp, Color.Gray)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        multiplePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri == null)
                    Column() {
                        Icon(
                            painter = painterResource(R.drawable.photo_icon),
                            tint = Color.Unspecified,
                            contentDescription = "more",
                            modifier = Modifier
                                .size(250.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Add Photo",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "(Select photo upto 5 MB)",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }


                selectedImageUri?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            InputBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp)),
                placeHolder = "Describe your picture",
                onTyped = { subject = it },
            )
            Spacer(modifier = Modifier.height(80.dp))
            if (!state.isLoading)
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Upload",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color.Black)
                            .padding(15.dp)
                            .clickable {
                                viewModel.createSketch(
                                    selectedImageUri.toString(),
                                    subject
                                )
                            }
                    )
                }
        }

        if (state.isSuccess) {
            navigator.navigate(SketchGenerateScreenDestination)
            viewModel.state = viewModel.state.copy(isSuccess = false)
        }

    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        if (state.error.isNotEmpty()) ErrorView(error = state.error)
    }
}