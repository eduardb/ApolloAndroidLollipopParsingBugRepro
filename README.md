1. Run the application in release mode on an Android 5 device, ARMv7 architecture.
2. Click on the "Click to repro" button.
3. Notice in the logcat the following error:

``` 
com.apollographql.apollo3.exception.JsonDataException: Expected an int but was 4294967297 at path [errors, 0, locations, 0, line]
	at com.apollographql.apollo3.api.json.BufferedSourceJsonReader.nextInt(BufferedSourceJsonReader.kt:615)
	at com.apollographql.apollo3.api.internal.ResponseParser.readErrorLocation(ResponseParser.kt:137)
	at com.apollographql.apollo3.api.internal.ResponseParser.readErrorLocations(ResponseParser.kt:124)
	at com.apollographql.apollo3.api.internal.ResponseParser.readError(ResponseParser.kt:80)
	at com.apollographql.apollo3.api.internal.ResponseParser.readErrors(ResponseParser.kt:62)
	at com.apollographql.apollo3.api.internal.ResponseParser.parse(ResponseParser.kt:34)
	at com.apollographql.apollo3.api.Operations.parseJsonResponse(Operations.kt:63)
	at com.apollographql.apollo3.api.Operations.parseJsonResponse(Operations.kt:80)
	at com.apollographql.apollo3.api.Operations.parseJsonResponse$default(Operations.kt:76)
	at com.apollo.androidlollipopparsingbugrepro.MainActivityKt.repro(MainActivity.kt:44)
```

Expected behaviour: parsing succeeds and the following is printed in the logcat:

``` 
resp: Data(animal=null)
```