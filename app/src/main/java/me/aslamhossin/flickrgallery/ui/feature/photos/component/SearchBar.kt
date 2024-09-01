package me.aslamhossin.flickrgallery.ui.feature.photos.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.aslamhossin.flickrgallery.R
import me.aslamhossin.flickrgallery.ui.theme.GalleryAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    searchText: String,
    onTextChange: (String) -> Unit,
) {
    var isSearching by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            if (isSearching) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        onTextChange(it)
                        isSearching = it.isNotEmpty()
                    },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(R.string.search)) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            } else {
                Text(
                    text = stringResource(R.string.photos_gallery),
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { isSearching = !isSearching }) {
                Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.search))
            }
        },
        actions = {
            if (isSearching) {
                IconButton(onClick = { isSearching = false }) {
                    Icon(Icons.Filled.Done, contentDescription = stringResource(R.string.done))
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun SearchBarPreview() {
    GalleryAppTheme {
        SearchAppBar("", {})
    }
}