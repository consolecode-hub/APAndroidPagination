# AP Android Pagination

![Screenshot_20240424-134632](https://github.com/consolecode-hub/APAndroidPagination/assets/3745464/63e5641f-6101-4b97-933d-2ad06a674abd)

https://github.com/consolecode-hub/APAndroidPagination/assets/3745464/0e5778d7-ee1c-49b9-a0cf-76a4a33db82a


Android Pagination app using Jetpack Compose

Android Material Design 3 [login](https://m3.material.io/) screen with androidx dependency

default Config for gradle

        minSdk = 24
        targetSdk = 34
        jvmTarget = "1.8"
        
**Depend on our library**
Material Components for Android is available through Google's Maven Repository. To use it:

Open the **_build.gradle_** file for your application.

Add the library to the dependencies section:

    dependencies {
    // ...
    implementation ("androidx.compose.material3:material3-*:1.2.0")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    }

Visit Google's Maven Repository or MVN Repository to find the latest version of the library.
