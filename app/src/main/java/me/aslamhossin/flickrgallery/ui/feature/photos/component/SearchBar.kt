package me.aslamhossin.flickrgallery.ui.feature.deatils.photos.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = OutlinedTextFieldTokens.FocusInputColor.value,
                        unfocusedTextColor = OutlinedTextFieldTokens.InputColor.value,
                        disabledTextColor = OutlinedTextFieldTokens.DisabledInputColor.value
                            .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                        errorTextColor = OutlinedTextFieldTokens.ErrorInputColor.value,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        cursorColor = OutlinedTextFieldTokens.CaretColor.value,
                        errorCursorColor = OutlinedTextFieldTokens.ErrorFocusCaretColor.value,
                        selectionColors = LocalTextSelectionColors.current,
                        focusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledBorderColor = OutlinedTextFieldTokens.DisabledOutlineColor.value
                            .copy(alpha = OutlinedTextFieldTokens.DisabledOutlineOpacity),
                        errorBorderColor = OutlinedTextFieldTokens.ErrorOutlineColor.value,
                        focusedLeadingIconColor = OutlinedTextFieldTokens.FocusLeadingIconColor.value,
                        unfocusedLeadingIconColor = OutlinedTextFieldTokens.LeadingIconColor.value,
                        disabledLeadingIconColor = OutlinedTextFieldTokens.DisabledLeadingIconColor.value
                            .copy(alpha = OutlinedTextFieldTokens.DisabledLeadingIconOpacity),
                        errorLeadingIconColor = OutlinedTextFieldTokens.ErrorLeadingIconColor.value,
                        focusedTrailingIconColor = OutlinedTextFieldTokens.FocusTrailingIconColor.value,
                        unfocusedTrailingIconColor = OutlinedTextFieldTokens.TrailingIconColor.value,
                        disabledTrailingIconColor = OutlinedTextFieldTokens.DisabledTrailingIconColor
                            .value.copy(alpha = OutlinedTextFieldTokens.DisabledTrailingIconOpacity),
                        errorTrailingIconColor = OutlinedTextFieldTokens.ErrorTrailingIconColor.value,
                        focusedLabelColor = OutlinedTextFieldTokens.FocusLabelColor.value,
                        unfocusedLabelColor = OutlinedTextFieldTokens.LabelColor.value,
                        disabledLabelColor = OutlinedTextFieldTokens.DisabledLabelColor.value
                            .copy(alpha = OutlinedTextFieldTokens.DisabledLabelOpacity),
                        errorLabelColor = OutlinedTextFieldTokens.ErrorLabelColor.value,
                        focusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                        unfocusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                        disabledPlaceholderColor = OutlinedTextFieldTokens.DisabledInputColor.value
                            .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                        errorPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                        focusedSupportingTextColor = OutlinedTextFieldTokens.FocusSupportingColor.value,
                        unfocusedSupportingTextColor = OutlinedTextFieldTokens.SupportingColor.value,
                        disabledSupportingTextColor = OutlinedTextFieldTokens.DisabledSupportingColor
                            .value.copy(alpha = OutlinedTextFieldTokens.DisabledSupportingOpacity),
                        errorSupportingTextColor = OutlinedTextFieldTokens.ErrorSupportingColor.value,
                        focusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                        unfocusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                        disabledPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value
                            .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                        errorPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                        focusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
                        unfocusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
                        disabledSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value
                            .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                        errorSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
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
        colors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        scrolledContainerColor = MaterialTheme.colorScheme.applyTonalElevation(
    backgroundColor = containerColor,
    elevation = TopAppBarSmallTokens.OnScrollContainerElevation
),
        navigationIconContentColor = TopAppBarSmallTokens.LeadingIconColor.value,
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