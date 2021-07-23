<p align="center"><img src="https://live.staticflickr.com/65535/49185149122_37f5c52e43_k.jpg"></p>

# SpaceX launches

Simple application that parses SpaceX launches and displays it in a convenient way.

Application uses MVVM pattern with [Hilt](https://dagger.dev/hilt/) for DI, [Room](https://developer.android.com/jetpack/androidx/releases/room) for database and [Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)  for networking.

To provide easy and flexible experienc with asynchronous tasks application uses Kotlin Coroutines.

<p align="left">
  <img src="https://user-images.githubusercontent.com/3418906/120925620-101a4e00-c6e2-11eb-891d-a5b059f16107.png" width="300" />
  <img src="https://user-images.githubusercontent.com/3418906/120925625-16102f00-c6e2-11eb-8fa2-554df594c5c0.png" width="300" />
</p>

## Test coverage

Contains unit tests that covers business and repository layers.

## Build

No additional steps are required to build the project. Just open it in your favourite IDE and run.

## Links

1. [SpaceX API](https://github.com/r-spacex/SpaceX-API/blob/master/docs/README.md)
1. [Guide to app architecture](https://developer.android.com/jetpack/guide)
1. [Kotlin coroutines guide](https://kotlinlang.org/docs/coroutines-guide.html)
