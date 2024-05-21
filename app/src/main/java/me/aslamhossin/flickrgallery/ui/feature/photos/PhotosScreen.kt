package me.aslamhossin.flickrgallery.ui.feature.photos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import me.aslamhossin.core.error.displayableMessage
import me.aslamhossin.flickrgallery.R
import me.aslamhossin.flickrgallery.ui.feature.deatils.photos.component.SearchAppBar
import me.aslamhossin.flickrgallery.ui.feature.photos.component.PhotoItem
import me.aslamhossin.flickrgallery.ui.feature.uimodel.Photo
import me.aslamhossin.flickrgallery.ui.navigation.ARG_PHOTO
import me.aslamhossin.flickrgallery.ui.navigation.SCREEN_PHOTO_DETAIL

@Composable
fun PhotosScreen(
    navHostController: NavHostController,
    viewModel: PhotosViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val searchText = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer).fillMaxSize()
    ) {
        Spacer(Modifier.height(24.dp))
        Scaffold(
            topBar = {
                SearchAppBar(
                    searchText = searchText.value,
                    onTextChange = { newText ->
                        searchText.value = newText
                        viewModel.handleIntent(PhotoViewIntent.Search(searchText.value))
                    }

                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    state.isLoading -> CircularProgressIndicator(
                        modifier = Modifier.padding(top = 24.dp)
                    )

                    state.error != null -> Text(
                        text = state.error.displayableMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp)
                    )

                    state.photos != null -> PhotosGrid(photos = state.photos) {
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            ARG_PHOTO,
                            it
                        )
                        navHostController.navigate(SCREEN_PHOTO_DETAIL)
                    }

                    else -> Text(
                        stringResource(R.string.no_photos_available),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp)
                    )
                }

            }
        }
    }


}


@Composable
fun PhotosGrid(photos: List<Photo>, onClickListener: (Photo) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        items(photos) {
            PhotoItem(Modifier.padding(4.dp), photo = it) {
                onClickListener.invoke(it)
            }
        }
    }
}