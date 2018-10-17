package software.egger.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import software.egger.kotlinlib.MessageService
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.collections4.bidimap.DualHashBidiMap


// add dependency
// implementation project(':kotlinlib')

class MainActivity : AppCompatActivity() {

    private val logTag = "software.egger.kotlin"
    private val messageService = MessageService(Locale.GERMAN)
    private val bidiMap = DualHashBidiMap<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textBox.text = messageService.helloWorldMessage()

        bidiMap.put("1", 1)
        bidiMap.put("2", 2)
        bidiMap.put("3", 3)

        Log.i(logTag, "Value of key: ${bidiMap["1"]}")
        Log.i(logTag, "Key of value: ${bidiMap.getKey(2)}")
    }
}
