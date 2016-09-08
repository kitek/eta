# eta
Android Wear App that allows estimate travel time and distance between two places.

![alt tag](https://raw.githubusercontent.com/kitek/eta/master/demo.gif)

## Used technologies:

- Android Wear (minSdkVersion = 23)
- Kotlin
- Google Maps Distance API
- Speech recognition


## How to build

- Clone repo
- Go to [Google Console](https://console.developers.google.com/apis/api/distance_matrix_backend) and enable Distance Matrix API
- Generate new [API KEY](https://console.developers.google.com/apis/credentials) and paste it as `service.key` value in `signing/signing.properties`
- Open project in Android Studio
- Sync gradle dependencies
- Run on emulator or device

## Issues

- If you run app on your physical device you need enable wifi connection
