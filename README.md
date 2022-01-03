<h1 align="center">Marvel Heroes</h1>

<p align="center">
Marvel Heroes is a small demo application based on modern Android tech-stacks and MVVM architecture.
</p>

<p align="center">
<img src="/previews/screenshot1.png" width="250">
<img src="/previews/screenshot2.png" width="250">
</p>

## Build
To build the project you need to add a file named `secrets.properties` in the root folder with this format.

```xml
PUBLIC_API_KEY={your public api key}
PRIVATE_API_KEY={your private api key}
```

You can get your own keys [here](https://developer.marvel.com/)

## Download
Go to the [Releases](https://github.com/FranAlterados/android-marvel-heroes/releases) to download the latest APK.

<img src="/previews/preview.gif" align="right" width="32%"/>

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

## Architecture
Marvel Heores is based on the MVVM architecture and the Repository pattern.

![architecture](/previews/architecture.png)

## TODO
- Test
- Show network errors
- Add `pull to refresh` to force refresh in repository
- Add cache time to stored values

# License
```xml
Designed and developed by 2022 fduranortega (Francisco Durán Ortega)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```