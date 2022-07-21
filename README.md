[![kotlin](https://img.shields.io/github/languages/top/bikcodeh/ToDoApp.svg?style=for-the-badge&color=blueviolet)](https://kotlinlang.org/) [![Android API](https://img.shields.io/badge/api-23%2B-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/api?level=23) [![Build status](https://build.appcenter.ms/v0.1/apps/86493375-a9cd-491f-a2b3-43176506e6d2/branches/main/badge)](https://appcenter.ms)

# News App  

## :star: Features

- [x] Display news by us country
- [x] Display news by categories
- [x] Display news by sources
- [x] Display news detail

:runner: For run the app just clone the repository and execute the app on Android Studio.

### Requirements to install the app
- Having an internet connection
- Using phones with Android Api 23+

##### This application was developed using Kotlin and uses the following components:
- Jetpack compose
- Coroutines
- Clean architecture (Domain, Data, UI)
- MVVM
- Repository pattern
- StateFlow
- Navigation component by compose
- Dagger Hilt (Dependency injection)
- Unit testing (Truth by google)
- UI testing by compose
- Mock web server
- Moshi
- Retrofit
- Dagger hilt ui testing
- App center

## Screenshots Light theme
 | Home |     Categories    |  Sources  |   Detail    |
 | :----: | :---------: | :-------: | :-----------: |
 |![Home](assets/home.png?raw=true)|![Categories](assets/categories.png?raw=true)|![Sources](assets/sources.png?raw=true)|![Detail](assets/detail.png?raw=true)|

## Screenshots Dark Mode
 | Home |     Categories    |  Sources  |   Detail    |
 | :----: | :---------: | :-------: | :-----------: |
 |![Home dark](assets/home_dark.png?raw=true)|![Categories dark](assets/categories_dark.png?raw=true)|![Sources dark dark](assets/sources_dark.png?raw=true)|![Detail dark](assets/detail_dark.png?raw=true)|

## :dart: Architecture

The application is built using Clean Architeture pattern based on [Architecture Components](https://developer.android.com/jetpack/guide#recommended-app-arch) on Android. The application is divided into three layers:

![Clean Arquitecture](https://devexperto.com/wp-content/uploads/2018/10/clean-architecture-own-layers.png)

- Domain: This layer contains the business logic of the application, here we define the data models and the use cases.
- Data: This layer contains the data layer of the application. It contains the database, network and the repository implementation.
- UI: This layer contains the presentation layer of the application like fragment, activity, viewmodel etc.

## License

MIT

**Bikcodeh**
