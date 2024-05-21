# Photo Gallery App

An open-source Android application designed to help beginners and intermediate developers learn the fundamentals of Android development while building a fully functional image gallery using the Flickr API.
This project covers a wide range of Android development topics, from basic to advanced, providing a comprehensive learning experience.

## Requirements

- Kotlin 1.9.23+
- Android Studio
- Gradle 8.1+

## Features

- Fetch and display a list of photos.
- Search photos by tags.
- View photo details.
- Handle loading and error states.

## Technologies Used

- **Kotlin**
- **Jetpack Compose** for UI
- **Hilt** for dependency injection
- **Kotlin Coroutines** for asynchronous programming
- **Turbine** for Flow testing
- **Mockito** for mocking in unit tests

## Architecture

The app follows the MVI (Model-View-Intent) architecture. Here's a brief overview of the architecture components:

- **Model**: Represents the state of the application.
- **View**: Displays the state to the user and sends user interactions to the ViewModel.
- **Intent**: Represents user actions or events that change the state.


## Getting Started

### Prerequisites

- Android Studio
- Kotlin 1.9.23+
- Gradle 8.1+

### Installation

1. **Clone the repository:**

   ```bash
   git clone git@github.com:aslamhossin/AllmTest.git

2. **Open the project in Android Studio::**
3. **Build the project:** Click on Build > Rebuild Project.
4. **Run the app:** Select an emulator or a physical device and click on Run > Run 'app'.

### Dependencies
Here's a list of the main dependencies used in the project as specified in the libs.versions.toml file:
```toml
[versions]
accompanistPermissions = "0.34.0"
agp = "8.4.0"
androidJoda = "2.10.9.1"
annotation = "1.8.0"
coilCompose = "2.6.0"
coreTesting = "2.2.0"
hiltNavigationCompose = "1.2.0"
kotlin = "1.9.23"
coreKtx = "1.13.1"
junit = "4.13.2"
junitVersion = "1.1.5"
espressoCore = "3.5.1"
kotlinxCoroutines = "1.8.0"
junitKtx = "1.1.5"
kotlinxSerializationJson = "1.6.3"
lifecycleRuntimeKtx = "2.8.0"
activity = "1.9.0"
composeBom = "2024.05.00"
materialIconsExtendedAndroid = "1.6.7"
mockitoCore = "5.3.1"
mockitoInline = "5.2.0"
okhttp = "4.12.0"
retrofit = "2.11.0"
retrofitKotlinxSerializationConverter = "1.0.0"
timber = "5.0.1"
hilt = "2.51.1"
ksp = "1.9.23-1.0.19"
navigationCompose = "2.8.0-beta01"
material = "1.12.0"
truth = "1.1.3"
turbine = "1.2.0"
runner = "1.5.2"

[libraries]
accompanist-permissions = { module = "com.google.accompanist:accompanist-permissions", version.ref = "accompanistPermissions" }
androidx-activity-ktx = { module = "androidx.activity:activity-ktx", version.ref = "activity" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activity" }
androidx-annotation = { module = "androidx.annotation:annotation", version.ref = "annotation" }
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-core-testing = { module = "androidx.arch.core:core-testing", version.ref = "coreTesting" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-navigation-compose = { module = "androidx.navigation:navigation-compose", version.ref = "navigationCompose" }
androidx-material-icons-extended-android = { module = "androidx.compose.material:material-icons-extended-android", version.ref = "materialIconsExtendedAndroid" }
kotlinx-coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "kotlinxCoroutines" }
kotlinx-coroutines-test = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-test", version.ref = "kotlinxCoroutines" }
kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinxSerializationJson" }
mockito-android = { module = "org.mockito:mockito-android", version.ref = "mockitoAndroid" }
mockito-core = { module = "org.mockito:mockito-core", version.ref = "mockitoCore" }
mockito-inline = { module = "org.mockito:mockito-inline", version.ref = "mockitoInline" }
retrofit2-kotlinx-serialization-converter = { module = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter", version.ref = "retrofitKotlinxSerializationConverter" }
logging-interceptor = { module = "com.squareup.okhttp3:logging-interceptor", version.ref = "okhttp" }
okhttp = { module = "com.squareup.okhttp3:okhttp", version.ref = "okhttp" }
retrofit = { module = "com.squareup.retrofit2:retrofit", version.ref = "retrofit" }
robolectric = { module = "org.robolectric:robolectric", version.ref = "robolectric" }
timber = { module = "com.jakewharton.timber:timber", version.ref = "timber" }
coil-compose = { module = "io.coil-kt:coil-compose", version.ref = "coilCompose" }
android-joda = { module = "net.danlew:android.joda", version.ref = "androidJoda" }

# Android - Hilt
hilt_android = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
hilt-android-testing = { module = "com.google.dagger:hilt-android-testing", version.ref = "hilt" }
hilt-android-compiler = { module = "com.google.dagger:hilt-android-compiler", version.ref = "hilt" }
androidx-hilt-navigation-fragment = { module = "androidx.hilt:hilt-navigation-fragment", version.ref = "hiltNavigationCompose" }
hilt-navigation-compose = { module = "androidx.hilt:hilt-navigation-compose", version.ref = "hiltNavigationCompose" }

material = { group = "com.google.android.material", name = "material", version.ref = "material" }
androidx-junit-ktx = { group = "androidx.test.ext", name = "junit-ktx", version.ref = "junitKtx" }
truth = { module = "com.google.truth:truth", version.ref = "truth" }
turbine = { module = "app.cash.turbine:turbine", version.ref = "turbine" }
androidx-runner = { group = "androidx.test", name = "runner", version.ref = "runner" }
androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
jetbrains-kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin_serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
android-library = { id = "com.android.library", version.ref = "agp" }

```
### Running Tests
To run the unit tests, use the following command:
```bash 
./gradlew test

```
### Unit Tests
Unit tests are written using JUnit, Turbine, and Mockito. You can find the tests in the src/test directory.

```kotlin
@OptIn(ExperimentalCoroutinesApi::class)
class PhotosViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var getPhotoUseCase: GetPhotoUseCase

    private lateinit var viewModel: PhotosViewModel

    private val testPhotos = listOf(
        Photo(
            title = "Photo1",
            author = "author1",
            dateTaken = "dateTaken1",
            published = "published1",
            tags = "tag1",
            media = Media("url1"),
            description = "description1",
            link = "link1",
            authorId = "authorId1"
        ),
        Photo(
            title = "Photo2",
            author = "author2",
            dateTaken = "dateTaken2",
            published = "published2",
            tags = "tag2",
            media = Media("url2"),
            description = "description2",
            link = "link2",
            authorId = "authorId2"
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = PhotosViewModel(getPhotoUseCase)
    }

    @Test
    fun `initial state is correct`() = runTest {
        // Arrange
        val expectedState = PhotosViewState(
            isLoading = false,
            photos = null,
            error = null
        )

        // Act & Assert
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(expectedState)
        }
    }

    // Other tests...
}

```

### Future Plans
- UI Testing: Implement UI tests using androidx.compose.ui:ui-test-junit4 and Hilt for testing.
- CI/CD: Integrate Continuous Integration and Continuous Deployment pipelines.
- Analytics: Add analytics to track user interactions and app performance.
- Benchmark: Implement benchmarking to measure app performance.
- Room Integration: Integrate Room as a persistence database.
- Add Fancy Animations: Utilize newly released Shared Element Transitions in Compose to enhance user experience.

