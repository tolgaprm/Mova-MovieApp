# Mova-Movies App

[<img src="https://upload.wikimedia.org/wikipedia/commons/7/78/Google_Play_Store_badge_EN.svg"
alt="Get it on Google Play Store"
height="80">](https://play.google.com/store/apps/details?id=com.prmto.mova_movieapp)

"A movie and TV show information app for teaching MVVM, Paging3, Flow-Coroutines with Clean
Architecture
With this app, you can easily find information about your favorite films and TV series.
You can view the cast, plot summary, release date, and more. "

<div style="text-align: center">

[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

</div>

## Screenshots

### Light Version

***
<div>
<img src="Screenshoots/light/home_light_turkish.png" width="180" height="360" float:left>
<img src="Screenshoots/light/home_detail_light_turkish.png" width="180" height="360" float:left>

<img src="Screenshoots/light/detail_light_turkish.png" width="180" height="360" float:left>
<img src="Screenshoots/light/detail_2_light_turkish.png" width="180" height="360" float:left>
<img src="Screenshoots/light/detail_3_light_turkish.png" width="180" height="360" float:left>
<img src="Screenshoots/light/detail_4_light_turkish.png" width="180" height="360" float:left>

<img src="Screenshoots/light/explore_light_turkish.png" width="180" height="360" float:left>
<img src="Screenshoots/light/upcoming_light.png" width="180" height="360" float:left >

**[Other Screenshots](readme/LIGHTSCREENS.md)**

</div>

### Dark Version

***
<div>
<img src="Screenshoots/dark/home_dark_english.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/home_detail_dark_english.png" width="180" height="360" float:left>  

<img src="Screenshoots/dark/detail_dark_english.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/detail_2_dark_english.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/detail_3_dark.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/detail_4_dark.png" width="180" height="360" float:left>

<img src="Screenshoots/dark/explore_dark.png" width="180" height="360" float:left>

<img src="Screenshoots/dark/person_detail_dark.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/upcoming_dark.png" width="180" height="360" float:left>


**[Other Screenshots](readme/DARKSCREENS.md)**

</div>

## Tech stack & Open-source libraries

- Minimum SDK level 24+
- 100% [Kotlin](https://kotlinlang.org/)
  based [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) [Flow](https://developer.android.com/kotlin/flow)
  for asynchronous.

- [Android Architecture Components](https://developer.android.com/topic/architecture) - Collection
  of libraries that help you design robust, testable, and maintainable apps.
    - A single-activity architecture, using
      the [Navigation](https://developer.android.com/guide/navigation) to manage composable
      transactions.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an
      action when lifecycle state change
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
      UI-related data that isn't destroyed on UI changes.
    - [UseCases](https://developer.android.com/topic/architecture/domain-layer) - Located domain
      layer
      that sits between the UI layer and the data layer.
    - [Repository](https://developer.android.com/topic/architecture/data-layer) - Located in data
      layer that contains application data and business logic

- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) -Dependency
  Injection Library
- [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The
  Paging library makes it easier for you to load data incrementally and gracefully within your app's
  UI
- [Navigation](https://developer.android.com/guide/navigation) - Manage transaction among the
  fragments
- [Room](https://developer.android.com/training/data-storage/room) - Create, store, and manage
  persistent data backed by a SQLite database.
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) -
  WorkManager is the recommended solution for persistent work. Work is persistent when it remains
  scheduled through app restarts and system reboots.
- [AlarmManager](https://developer.android.com/training/scheduling/alarms) - AlarmManager is a class
  provides access to the system alarm services. These
  allow you to schedule your application to be run at some point in the future. My usage is to set a
  upcoming movie reminder.
- [Notification](https://developer.android.com/develop/ui/views/notifications) - A notification is a
  message that Android displays outside your app's UI to
  provide the user with reminders, communication from other people, or other timely information from
  your app.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging
  network data.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Android, Java and Kotlin
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin
  Coroutines.
- Testing
    - [ Flow Turbine](https://github.com/cashapp/turbine) - Turbine is a small testing library for
      kotlinx.coroutines Flow.
    - [Truth](https://truth.dev/) - A library for performing assertions in tests

## Modularization Structure

- I used convention plugin for manage dependencies.
- This app modularization guide is soon.
- When I implement navigation for multiModule, I got help this
  article. [Android Multimodule Navigation with the Navigation Component](https://medium.com/itnext/android-multimodule-navigation-with-the-navigation-component-99f265de24)
  <img src="Screenshoots/module_structure.png" height="700" float:left >

## API Key 🔑

- You will need to provide developer key to fetch the data from TMDB API.
- Generate a new key (v3 auth) from [here](https://www.themoviedb.org/settings/api). Copy the key
  and go back to Android project.
- Open the page is gradle/local.properties
- Define a constant API_KEY, it looks like

``` API_KEY = PASTE_YOUR_API_KEY ```

## Upcoming Features
- CI Support
- UI and Unit Tests

## Find this repository useful?

Don't forget give a star. ⭐

## Licence

```
    Designed and developed by 2022 tolgaprm (Tolga Pirim)

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