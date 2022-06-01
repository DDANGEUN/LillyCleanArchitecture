# LillyCleanArchitecture
Android Clean Architecture Sample.
This project is being updated by adding funcitions one by one.

## Info

***Android Clean Architecture Sample with***
- Room
- SharedPreference(skipable start view)
- RxBle

 **Using**
  - [Hilt](https://dagger.dev/hilt/)
  - [Flow](https://kotlinlang.org/docs/flow.html)
  - [RxAndroidBle](https://github.com/dariuszseweryn/RxAndroidBle)
  - [Room](https://developer.android.com/topic/libraries/architecture/room)
  - Theme Ref : https://material.io/design/material-studies/shrine.html#components


## Preview
#### Room
<img src = "https://github.com/DDANGEUN/LillyCleanArchitecture/blob/main/lillyClean_room.gif" width="30%">

#### RxBle
<img src = "https://github.com/DDANGEUN/LillyCleanArchitecture/blob/main/lillyClean_ble.gif" width="30%">



  - If you want to see your ble device like this code preview, ***modify UUID*** in **devices/Constants.kt**

```Kotlin
const val SERVICE_STRING = "6E400001-B5A3-F393-E0A9-E50E24DCCA9E"
const val CHARACTERISTIC_COMMAND_STRING = "6E400002-B5A3-F393-E0A9-E50E24DCCA9E"
const val CHARACTERISTIC_RESPONSE_STRING = "6E400003-B5A3-F393-E0A9-E50E24DCCA9E"
```

## Blog

- [안드로이드 Clean Architecture 구현하기](https://ddangeun.tistory.com/138)
