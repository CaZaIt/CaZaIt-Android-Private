<h1 align="center">
  CaZaIt-Android-Private
</h1>
<p align="center">
  카자잇 안드로이드 배포용 레포지토리
</p>

<p align="center">
<img src="/README_images/CaZaIt2.gif" width="260" height="520"/>
</p>

# Tech stack & Open-source libraries

- **Jetpack**
  - Lifecycle
  - DataStore
  - ViewModel
  - Navigation
  - Room
  - DataBinding, LiveData, StateFlow
 

- **Dependency Injection**
  - Hilt
- Repository Pattern


- Glide
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit)
- Coroutine Flow
- [Material View Pager Dots Indicator](https://github.com/tommybuonomo/dotsindicator)
- [Naver Maps](https://navermaps.github.io/android-map-sdk/guide-ko/)


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
