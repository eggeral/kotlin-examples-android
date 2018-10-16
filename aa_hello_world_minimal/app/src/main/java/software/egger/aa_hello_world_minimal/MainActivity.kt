package software.egger.aa_hello_world_minimal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

// Create new Android Project
// No activity
// New -> Activity -> Empty Activity -> Select Kotlin and Launcher Activity and Layout File
// Add a text field to activity


// Apart from the slightly different syntax there is hardly any difference between
// the Kotlin version of an Activity and the Java version.
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
