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

## Modularization
                 +-----+
                 | app |
                 +--+--+
                    |
                 +--v---+
                 | data |
                 +--+---+
                    |
         +----------+-----------+
         |                      |
    +----v----+           +-----v----+
    | network |           | database |
    +----+----+           +-----+----+
         |                      |
         +----------+-----------+
                    |
                +---v---+
                | model |
                +-------+
