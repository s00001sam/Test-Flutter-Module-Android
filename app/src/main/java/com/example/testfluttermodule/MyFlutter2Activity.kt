package com.example.testfluttermodule

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MyFlutter2Activity : FlutterActivity() {
    /**
     *  TODO 確認是否要用 Cache 的 FlutterEngine
     *
     *  好處：開比較快
     *
     *  壞處：會留有上次的頁面紀錄
     */
//    override fun provideFlutterEngine(context: Context): FlutterEngine? {
//        return FlutterEngineCache
//            .getInstance()
//            .get(FLUTTER_ENGINE_ID)
//    }

    override fun getInitialRoute(): String {
        return "/listing_first_step"
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            if (call.method == CLOSE_PAGE) {
                finish()
            } else {
                result.notImplemented()
            }
        }
    }

    companion object {
        private const val CHANNEL = "com.swimple.channel"
        private const val CLOSE_PAGE = "closePage"
    }
}