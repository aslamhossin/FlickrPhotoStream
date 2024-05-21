package me.aslamhossin.flickrgallery.ui.feature.deatils

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.aslamhossin.flickrgallery.R
import me.aslamhossin.flickrgallery.ui.feature.deatils.components.PhotoContent
import me.aslamhossin.flickrgallery.ui.feature.uimodel.Photo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailScreen(
    photo: Photo,
    navController: NavController,
    viewModel: PhotoDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var hasPermission by remember { mutableStateOf(false) }

    val requestPermissions =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            hasPermission = results.all { it.value }
        }

    val permissionList = mutableListOf<String>()
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            // No need to request WRITE_EXTERNAL_STORAGE for Android 10 and above for using MediaStore
        }

        else -> {
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissionList.add(android.Manifest.permission.POST_NOTIFICATIONS)
    }

    LaunchedEffect(key1 = true) {

        hasPermission = permissionList.all { permission ->
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is PhotoDetailEffect.ShowMessage -> {
                    val message = effect.downloadStatus.getLocaleMessage(context)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer).fillMaxSize()
    ) {
        Spacer(Modifier.height(24.dp))
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            photo.title,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    actions = {
                        IconButton(
                            onClick = {
                                if (hasPermission || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    val fileName = photo.title.ifEmpty { photo.author }
                                    viewModel.handleIntent(
                                        PhotoDetailIntent.SavePhoto(
                                            fileName,
                                            photo.media.url
                                        )
                                    )
                                } else {
                                    requestPermissions.launch(permissionList.toTypedArray())
                                }
                            }
                        ) {
                            Icon(Icons.Filled.Download, contentDescription = null)
                        }
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PhotoContent(photo)
            }
        }
    }

}
