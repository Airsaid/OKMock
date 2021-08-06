package com.airsaid.okmock

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airsaid.okmock.data.ArrayMockDataTest
import com.airsaid.okmock.data.BeanMockDataTest
import com.airsaid.okmock.data.CollectionMockDataTest
import com.airsaid.okmock.data.PrimitiveDataProvider
import com.airsaid.okmock.util.toDetailString
import kotlin.concurrent.thread

/**
 * @author airsaid
 */
class MainActivity : AppCompatActivity() {

  private val mMockTestClasses = listOf<Any>(
    PrimitiveDataProvider::class.java.newInstance(),
    BeanMockDataTest::class.java.newInstance(),
    ArrayMockDataTest::class.java.newInstance(),
    CollectionMockDataTest::class.java.newInstance()
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    thread {
      val fields = mutableListOf<Pair<String, String>>()
      mMockTestClasses.map { (it to it.javaClass.declaredFields) }
        .forEach {
          for (field in it.second) {
            fields.add(field.name to field.get(it.first).toDetailString())
          }
        }

      val strings = StringBuilder()
      for (field in fields) {
        val fieldName = field.first
        val fieldValue = field.second
        strings.append("$fieldName: $fieldValue")
        strings.appendLine()
        strings.appendLine()
      }
      runOnUiThread {
        findViewById<TextView>(R.id.textView).text = strings.toString()
      }
    }
  }
}