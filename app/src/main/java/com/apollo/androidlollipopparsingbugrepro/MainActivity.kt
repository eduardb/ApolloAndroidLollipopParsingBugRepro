package com.apollo.androidlollipopparsingbugrepro

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apollo.androidlollipopparsingbugrepro.ui.theme.ApolloAndroidLollipopParsingBugReproTheme
import com.apollo.generated.GetAnimalQuery
import com.apollo.generated.TestQuery
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.json.BufferedSourceJsonReader
import com.apollographql.apollo3.api.parseResponse
import okio.Buffer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApolloAndroidLollipopParsingBugReproTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        onButtonClick = ::repro2
                    )
                }
            }
        }
    }
}

@OptIn(ApolloExperimental::class)
fun repro1() {
    val response = GetAnimalQuery(id = "1")
        .parseResponse(
            BufferedSourceJsonReader(
                Buffer().write(
                    """{"errors":[{"message":"Enter your secret code.","locations":[{"line":1,"column":134}],"path":["signup"],"code":"needs-pin"}],"data":{"animal":null}}""".toByteArray()
                )
            )
        )
    if (response.data != null) {
        Log.d("MainActivity", "response: ${response.data}")
    } else {
        if (response.exception != null) {
            Log.e("MainActivity", "error: ${response.exception}")
        } else {
            Log.e("MainActivity", "errors: ${response.errors}")
        }
    }
}

@OptIn(ApolloExperimental::class)
fun repro2() {
    val response = TestQuery()
        .parseResponse(
            BufferedSourceJsonReader(
                Buffer().write(
                    """{"data":{"test":2}}""".toByteArray()
                )
            )
        )
    if (response.data != null) {
        Log.d("MainActivity", "response: ${response.data}")
    } else {
        if (response.exception != null) {
            Log.e("MainActivity", "error: ${response.exception}")
        } else {
            Log.e("MainActivity", "errors: ${response.errors}")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onButtonClick: () -> Unit) {
    Column {
        Text(
            text = "Hello $name!", modifier = modifier
        )
        Button(onClick = onButtonClick) {
            Text("Click to repro")

        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApolloAndroidLollipopParsingBugReproTheme {
        Greeting("Android", onButtonClick = {})
    }
}