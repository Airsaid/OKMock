package com.airsaid.okmock

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airsaid.okmock.data.provider.*
import kotlin.concurrent.thread

/**
 * @author airsaid
 */
class MainActivity : AppCompatActivity() {

  private val mSpinner: Spinner by lazy { findViewById(R.id.spinner) }

  private val mTextView: TextView by lazy { findViewById(R.id.textView) }

  private val mMockTestClasses = mapOf<String, Any>(
    "Bean" to BeanMockDataProvider::class.java.newInstance(),
    "Primitive Type" to PrimitiveDataProvider::class.java.newInstance(),
    "Static Field" to StaticFieldProvider::class.java.newInstance(),
    "Array" to ArrayMockDataProvider::class.java.newInstance(),
    "Collection" to CollectionMockDataProvider::class.java.newInstance()
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val names = mMockTestClasses.map { it.key }
    mSpinner.adapter = ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, names).apply {
      setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        setSelected(names[position])
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
  }

  private fun setSelected(name: String) {
    thread {
      val clazz = mMockTestClasses[name]!!
      val strings = StringBuilder()
      clazz.javaClass.declaredFields.forEach { field ->
        val fieldName = field.name
        val fieldValue = field.get(clazz)
        strings.append("$fieldName: $fieldValue")
        strings.appendLine()
        strings.appendLine()
      }
      runOnUiThread {
        mTextView.text = strings.toString()
      }
    }
  }

}