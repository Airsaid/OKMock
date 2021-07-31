package com.airsaid.okmock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

/**
 * @author airsaid
 */
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val mockData = JavaMockDataTest()

    val strings = StringBuilder()
      .append("mockBoolean: ${mockData.mockBool}")
      .appendLine()
      .append("mockBooleanWrap: ${mockData.mockBoolWrap}")
      .appendLine()
      .append("mockChar: ${mockData.mockChar}")
      .appendLine()
      .append("mockCharWrap: ${mockData.mockCharWrap}")
      .appendLine()
      .append("mockByte: ${mockData.mockByte}")
      .appendLine()
      .append("mockByteWrap: ${mockData.mockByteWrap}")
      .appendLine()
      .append("mockShort: ${mockData.mockShort}")
      .appendLine()
      .append("mockShortWrap: ${mockData.mockShortWrap}")
      .appendLine()
      .append("mockInt: ${mockData.mockInt}")
      .appendLine()
      .append("mockIntWrap: ${mockData.mockIntWrap}")
      .appendLine()
      .append("mockFloat: ${mockData.mockFloat}")
      .appendLine()
      .append("mockFloatWrap: ${mockData.mockFloatWrap}")
      .appendLine()
      .append("mockLong: ${mockData.mockLong}")
      .appendLine()
      .append("mockLongWrap: ${mockData.mockLongWrap}")
      .appendLine()
      .append("mockDouble: ${mockData.mockDouble}")
      .appendLine()
      .append("mockDoubleWrap: ${mockData.mockDoubleWrap}")
      .appendLine()
      .append("mockString: ${mockData.mockString}")
      .appendLine()
      .append("mockPerson: ${mockData.mockPerson}")
      .appendLine()

    findViewById<TextView>(R.id.textView).text = strings.toString()
  }
}