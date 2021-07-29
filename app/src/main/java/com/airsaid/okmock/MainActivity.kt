package com.airsaid.okmock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airsaid.okmock.api.Mock

class MainActivity : AppCompatActivity() {

  // 基本类型
  @Mock
  private var mMockBoolean: Boolean = false
  @Mock
  private var mMockByte: Byte = Byte.MIN_VALUE
  @Mock
  private var mMockShort: Short = Short.MIN_VALUE
  @Mock
  private var mMockInt: Int = Int.MIN_VALUE
  @Mock
  private var mMockLong: Long = Long.MIN_VALUE
  @Mock
  private var mMockFloat: Float = Float.MIN_VALUE
  @Mock
  private var mMockDouble: Double = Double.MIN_VALUE
  @Mock
  private var mMockChar: Char = Char.MIN_VALUE

  // 引用类型
  @Mock
  private lateinit var mMockString: String
  @Mock
  private lateinit var mMockPerson: Person

  // 引用集合
  @Mock
  private lateinit var mMockStrings: List<String>
  @Mock
  private lateinit var mMockPersons: Set<Person>
  @Mock
  private lateinit var mMockPersonMap: Map<Int, Person>

  // 嵌套
  @Mock
  private lateinit var mMockParents: List<Parent>
  @Mock
  private lateinit var mMockParentMap: Map<Int, List<Parent>>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  data class Person(val name: String, val age: Int)

  data class Parent(val name: String, val age: Int, val children: List<Person>)
}