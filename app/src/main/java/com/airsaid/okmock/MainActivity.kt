package com.airsaid.okmock

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airsaid.okmock.api.Mock
import com.airsaid.okmock.data.MockDataProvider
import com.airsaid.okmock.data.Person

/**
 * @author airsaid
 */
class MainActivity : AppCompatActivity() {

  @Mock
  private var primitiveMockDataByKotlin: Int? = null

  @Mock
  private lateinit var referenceMockDataByKotlin: List<Person>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val mockData = MockDataProvider()

    val strings = StringBuilder()
      .append("mockBoolean: ${mockData.mockBool}")
      .appendLine().appendLine()
      .append("mockBooleanWrap: ${mockData.mockBoolWrap}")
      .appendLine().appendLine()
      .append("mockChar: ${mockData.mockChar}")
      .appendLine().appendLine()
      .append("mockCharWrap: ${mockData.mockCharWrap}")
      .appendLine().appendLine()
      .append("mockByte: ${mockData.mockByte}")
      .appendLine().appendLine()
      .append("mockByteWrap: ${mockData.mockByteWrap}")
      .appendLine().appendLine()
      .append("mockShort: ${mockData.mockShort}")
      .appendLine().appendLine()
      .append("mockShortWrap: ${mockData.mockShortWrap}")
      .appendLine().appendLine()
      .append("mockInt: ${mockData.mockInt}")
      .appendLine().appendLine()
      .append("mockIntWrap: ${mockData.mockIntWrap}")
      .appendLine().appendLine()
      .append("mockFloat: ${mockData.mockFloat}")
      .appendLine().appendLine()
      .append("mockFloatWrap: ${mockData.mockFloatWrap}")
      .appendLine().appendLine()
      .append("mockLong: ${mockData.mockLong}")
      .appendLine().appendLine()
      .append("mockLongWrap: ${mockData.mockLongWrap}")
      .appendLine().appendLine()
      .append("mockDouble: ${mockData.mockDouble}")
      .appendLine().appendLine()
      .append("mockDoubleWrap: ${mockData.mockDoubleWrap}")
      .appendLine().appendLine()
      .append("mockString: ${mockData.mockString}")
      .appendLine().appendLine()
      .append("mockPerson: ${mockData.mockPerson}")
      .appendLine().appendLine()
      .append("mockGroup: ${mockData.mockGroup}")
      .appendLine().appendLine()
      .append("mockList: ${mockData.mockList}")
      .appendLine().appendLine()
      .append("mockList2: ${mockData.mockList2}")
      .appendLine().appendLine()
      .append("mockSet: ${mockData.mockSet}")
      .appendLine().appendLine()
      .append("mockSet2: ${mockData.mockSet2}")
      .appendLine().appendLine()
      .append("mockMap: ${mockData.mockMap}")
      .appendLine().appendLine()
      .append("mockMap2: ${mockData.mockMap2}")
      .appendLine().appendLine()
      .append("mockNested: ${mockData.mockNested}")
      .appendLine().appendLine()
      .append("mockNested2: ${mockData.mockNested2}")
      .appendLine().appendLine()
      .append("mockNested3: ${mockData.mockNested3}")
      .appendLine().appendLine()
      .append("primitiveMockDataByKotlin: $primitiveMockDataByKotlin")
      .appendLine().appendLine()
      .append("referenceMockDataByKotlin: $referenceMockDataByKotlin")

    findViewById<TextView>(R.id.textView).text = strings.toString()
  }
}