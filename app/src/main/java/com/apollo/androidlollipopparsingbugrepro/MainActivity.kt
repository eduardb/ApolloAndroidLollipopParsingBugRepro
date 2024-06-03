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
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.parseJsonResponse

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
                        onButtonClick = ::repro
                    )
                }
            }
        }
    }
}

@OptIn(ApolloExperimental::class)
fun repro() {
    try {
        val resp = GetAnimalQuery(id = "1")
            .parseJsonResponse(
                """{"errors":[{"message":"Enter your secret code.","locations":[{"line":1,"column":134}],"path":["signup"],"code":"needs-pin"}],"data":{"animal":null}}"""
            )
        Log.d("MainActivity", "resp: ${resp.data}")
    } catch (e: Exception) {
        e.printStackTrace()
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