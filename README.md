<h1 align="center">Marvel Heroes</h1>

<p align="center">
Marvel Heroes is a small demo application based on modern Android tech-stacks and MVVM architecture.
</p>

<p align="center">
<img src="/previews/screenshot1.png" width="250">
<img src="/previews/screenshot2.png" width="250">
</p>

## Download
Go to the [Releases](https://github.com/FranAlterados/android-marvel-heroes/releases) to download the latest APK.

## Tech stack & Open-source libraries
- Minimum SDK level 28
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- Jetpack
  - Lifecycle
  - ViewModel
  - Room Persistence
- Architecture
  - MVVM Architecture with new Android [App architecture](https://developer.android.com/topic/architecture)
  - View layer with ViewBinding and [UDF](https://developer.android.com/jetpack/guide/ui-layer#udf) (Unidirectional Data Flow)
  - Repository pattern in data layer
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs.
- [Sandwich](https://github.com/skydoves/Sandwich) - construct the lightweight Http API response and handling error responses.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette) - loading images.
- [TransformationLayout](https://github.com/skydoves/transformationlayout) - implementing transformation motion animations.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for building ripple animation, and CardView.
