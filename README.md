AlMarvel 
==================

Based on Android architecture starter template, this appllication is compatible with the latest **stable** version of Android Studio.
Tested with JRE 11 (Android Studio - Preferences - Build - Build Tools Gradle)

## Usage

Create a config file in the root project with name `apikey.properties`, it should conains your marvel api keys (https://developer.marvel.com/account) : 

```
API_KEY="your_public_key"
API_SECRET="your_private_key"
API_TS="1"
```

## Features

The app is quite simple. At launch, user can see list of all Marvel characters (provided
by Marvel API). User can also see his current squad at the top (if any).
Tapping on a character shows details and gives you the option to recruit or fire the
character.

### Screenshots

| | | |
| -- | -- | -- |
| <image src="https://user-images.githubusercontent.com/5929228/192249724-080ac304-6651-45ed-add2-08965e8c2603.png" width="300px" /> | <image src="https://user-images.githubusercontent.com/5929228/192249717-b59fb593-4772-4546-a861-4dbb468764ce.png" width="300px" /> | <image src="https://user-images.githubusercontent.com/5929228/192249715-28231dfc-115b-412e-a596-5f3a20352fcd.png" width="300px" /> |


## Architecture

based on architecture-template (base) : https://github.com/android/architecture-templates

<image src="https://user-images.githubusercontent.com/5929228/192226104-18b0536a-361f-42a9-9817-9867c3f01170.png" width="300px"/>

* Room Database
* Hilt
* ViewModel
* UI in Compose, list + detail (Material3)
* Navigation
* Repository and data source
* Kotlin Coroutines and Flow
* Unit tests
* UI tests using fake data with Hilt

### Data

Use repository pattern :

![image](https://user-images.githubusercontent.com/5929228/192277442-ceaf358e-e7f7-470c-8257-ab1fb11c3e4c.png)

### Domain

The features of this application are basics. The Domain layer only contains one use case to sort the heroes.

### UI

<image src="https://user-images.githubusercontent.com/5929228/192227474-445b12ae-4ef5-459c-a996-df0a493ac64a.png" width="300px"/>

The UI is only available in light mode and do not use dynamic colors.
Based on compose material3, some features are still opt-in (Scaffold & StickyHeader) 

The errors are not displayed yet. The confirmation to fire a hero from a squad use currently a snackBar, a dialog or specific screen should be better.

## Missing

- Tests (Espresso, Unit, ...)
- Themes (Dynamic)
- Dimens (use a speecific accesser for all dimens in App)
- Paging


## Références (not exhaustive) 

https://developer.android.com/topic/architecture
https://github.com/android/nowinandroid
https://github.com/android/sunflower/
https://betterprogramming.pub/managing-jetpack-compose-ui-state-with-sealed-classes-d864c1609279
https://guides.codepath.com/android/storing-secret-keys-in-android
https://stackoverflow.com/questions/64171624/how-to-generate-an-md5-hash-in-kotlin
https://medium.com/codex/creating-the-network-module-with-hilt-3eefc54b72

# License

Now in Android is distributed under the terms of the Apache License (Version 2.0). See the
[license](LICENSE) for more information.
