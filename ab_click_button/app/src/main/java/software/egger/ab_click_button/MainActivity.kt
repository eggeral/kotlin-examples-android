package software.egger.ab_click_button

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

// needed for view binding

// Add a input text field to the layout
// add a button the the layout
// add a label to the layout

// after pressing the button the label shows the content of the input field

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 01 Java way of handling views and listeners but using Kotlin syntax
//        val submitButton = findViewById<Button>(R.id.submitButton)
//        val messageEditText = findViewById<EditText>(R.id.messageEditText)
//        val resultTextView = findViewById<TextView>(R.id.resultTextView)
//
//        submitButton.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                resultTextView.text = messageEditText.text
//            }
//        })

        // 02 Using lambdas
//        val submitButton = findViewById<Button>(R.id.submitButton)
//        val messageEditText = findViewById<EditText>(R.id.messageEditText)
//        val resultTextView = findViewById<TextView>(R.id.resultTextView)
//
//        submitButton.setOnClickListener { resultTextView.text = messageEditText.text }

        // 03 Using android extensions
        submitButton.setOnClickListener { resultTextView.text = messageEditText.text }


    }
}
