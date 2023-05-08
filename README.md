# CaZaIt-Android-Private

카자잇 안드로이드 배포용 레포지토리

![](README_images/CaZaIt2.gif)

## Features

* DataStore
* Hilt
* ViewModel
* DataBinding and LiveData
* Repository and data source
* Coroutine Flow API
* OKHTTP
* Retrofit

### Material View Pager Dots Indicator

[Material View Pager Dots Indicator](https://github.com/tommybuonomo/dotsindicator)

### Naver Maps

[네이버 지도 SDK](https://navermaps.github.io/android-map-sdk/guide-ko/)

## Package Structure

```shell
☕️cazait
    ├── 🗂️data
    │   ├── 🗂️api
    │   ├── 🗂️dto
    │   │   ├── 🗂️request
    │   │   └── 🗂️response
    │   ├── 🗂️error
    │   │   └── 🗂️mapper
    │   ├── 🗂️mapper
    │   ├── 🗂️model
    │   ├── 🗂️remote
    │   │   ├── 🗂️auth
    │   │   ├── 🗂️cafe
    │   │   └── 🗂️user
    │   └── 🗂️repository
    │       ├── 🗂️auth
    │       ├── 🗂️cafe
    │       └── 🗂️users
    ├── 🗂️di
    ├── 🗂️network
    ├── 🗂️ui
    │   ├── 🗂️adapter
    │   ├── 🗂️base
    │   ├── 🗂️component
    │   │   ├── 🗂️cafeinfo
    │   │   │   ├── 🗂️menu
    │   │   │   └── 🗂️review
    │   │   ├── 🗂️cafelist
    │   │   ├── 🗂️map
    │   │   ├── 🗂️signin
    │   │   └── 🗂️signup
    │   └── 🗂️viewholder
    └── 🗂️utils
```
