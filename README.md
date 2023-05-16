# CaZaIt-Android-Private

카자잇 안드로이드 배포용 레포지토리

<img src="/README_images/CaZaIt2.gif" width="260" height="520"/>

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

```
☕️cazait
    ├── 🗂️data
    │   ├── api
    │   ├── dto
    │   │   ├── request
    │   │   └── response
    │   ├── error
    │   │   └── mapper
    │   ├── model
    │   │   ├── entity
    │   │   └── mapper
    │   ├── repository
    │   │   ├── auth
    │   │   ├── cafe
    │   │   └── users
    │   └── source
    │       ├── local
    │       │   └── dao
    │       └── remote
    │           ├── auth
    │           ├── cafe
    │           └── user
    ├── di
    ├── 🗂️domain
    │   ├── model
    │   │   └── mapper
    │   ├── repository
    │   └── usecase
    ├── network
    ├── 🗂️ui
    │   ├── adapter
    │   ├── base
    │   ├── component
    │   │   ├── cafeinfo
    │   │   │   ├── menu
    │   │   │   └── review
    │   │   ├── cafelist
    │   │   ├── map
    │   │   ├── menu
    │   │   ├── mypage
    │   │   ├── review
    │   │   ├── seemore
    │   │   ├── signin
    │   │   └── signup
    │   └── viewholder
    └── utils
```
