# Test-Flutter-Module-Android

## 基礎配置，可以參考 [官方資訊](https://docs.flutter.dev/add-to-app/android/project-setup#option-a---depend-on-the-android-archive-aar)
或是直接參考此專案內部的設定

- 首先在 Flutter 專案，透過 `flutter build aar` 指令即可以產生 aar 檔案，會看到類似以下的圖，代表順利建立成功

![image](https://github.com/s00001sam/Test-Flutter-Module-Android/assets/61711644/6a6bcccb-b872-4c6b-a1bd-2e465f1d653a)
- 到 Android 專案，打開 `settings.gradle.kts`，在 `dependencyResolutionManagement` 內部的 `repositories` 補上以下資訊
    - 其中 `flutter_aar` 即為上一步產生的 `repo` 檔案夾（我改名成 flutter_aar）
    - 目前此專案無此資料夾，要 Build 之前，請先自己的「`repo` 檔案夾」更名為 `flutter_aar` 放進專案根目錄即可


```
maven(url = "file://${rootProject.projectDir}/flutter_aar")
maven(url = "https://storage.googleapis.com/download.flutter.io")
```

- 在 `app` 的 `build.gradle.kts` 的 `buildTypes` 補上以下
```
buildTypes {
   create("profile") {
        initWith(getByName("debug"))
   }
}
```

- 在 `app` 的 `build.gradle.kts` 的 `dependencies` 補上以下，Sync 之後，設定就差不多完成了！
```
debugImplementation("com.swim.flutter.app.swimple_flutter_module:flutter_debug:1.0")
add("profileImplementation", "com.swim.flutter.app.swimple_flutter_module:flutter_profile:1.0")
releaseImplementation("com.swim.flutter.app.swimple_flutter_module:flutter_release:1.0")
```
## 進入「開啟 MyFlutterActivity」，可以參考 [官方資料](https://docs.flutter.dev/add-to-app/android/add-flutter-screen)

- 可以依照官方方法去直接開啟 FlutterActivity，就不需要自行創建 Activity，但我希望可以串接 `MethodChannel`，所以實作如下...
- 寫一個 `MyFlutterActivity` 繼承至 `FlutterActivity`
- 在 `AndroidManifest` 定義此 Activity
```
<activity
    android:name=".MyFlutterActivity"
    android:configChanges="orientation|keyboardHidden|keyboard|screenSize|locale|layoutDirection|fontScale|screenLayout|density|uiMode"
    android:hardwareAccelerated="true"
    android:windowSoftInputMode="adjustResize" />
```
- 透過 Intent 開啟 Activity 即可
```
startActivity(Intent(context, MyFlutterActivity::class.java))
```

## [Optional] 透過 `FlutterEngine` 來 Cache FlutterActivity，可以加快開啟 Flutter 頁面
- 建一個 `MyApplication`，如下，記得要到 `AndroidManifest` 宣告
```
class MyApplication : Application() {

    lateinit var flutterEngine: FlutterEngine

    override fun onCreate() {
        super.onCreate()

        // Instantiate a FlutterEngine.
        flutterEngine = FlutterEngine(this)

        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put(FLUTTER_ENGINE_ID, flutterEngine)
    }

    companion object {
        const val FLUTTER_ENGINE_ID = "my_flutter_engine"
    }
}
```

- 在 `MyFlutterActivity` 補上以下即可
```
override fun provideFlutterEngine(context: Context): FlutterEngine? {
    return FlutterEngineCache
       .getInstance()
       .get(FLUTTER_ENGINE_ID)
}
```

## [Optional] 透過 Router 開啟特定頁面
- 首先需要知道 要前往的「Flutter 專案」頁面的 Router 名稱，這邊舉例名稱是「/detail」，有兩種方式
    1. 在 `MyFlutterActivity` 直接複寫 getInitialRoute()，return 該頁 Router 名稱
    ```
    override fun getInitialRoute(): String {
        return "/detail"
    }
    ```
    2. 如果有用上面提到的 Application 的 `FlutterEngine`，可以透過 `flutterEngine.navigationChannel.setInitialRoute`
    ```
    flutterEngine.navigationChannel.setInitialRoute("/detail")
    ```

