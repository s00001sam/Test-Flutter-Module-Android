package com.example.testfluttermodule

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.testfluttermodule.ui.theme.TestFlutterModuleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current

            TestFlutterModuleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting(
                            modifier = Modifier
                                .clickable {
                                    startActivity(
                                        /**
                                         *  註解掉的部分是開啟非客製化的 FlutterActivity
                                         *  這邊改用客製化的 MyFlutterActivity
                                         */
//                                        FlutterActivity.createDefaultIntent(context)
//                                        FlutterActivity
//                                            .withNewEngine()
//                                            .initialRoute("/listing_first_step")
//                                            .build(context)

//                                        FlutterActivity
//                                            .withCachedEngine(MyApplication.FLUTTER_ENGINE_ID)
//                                            .build(context)
                                        Intent(context, MyFlutterActivity::class.java)
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Go Flutter Page",
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestFlutterModuleTheme {
        Greeting()
    }
}