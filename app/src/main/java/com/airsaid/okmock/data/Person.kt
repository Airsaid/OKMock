package com.airsaid.okmock.data

import com.airsaid.okmock.api.MockValue

/**
 * @author airsaid
 */
data class Person(val id: Long, @MockValue(stringValues = ["小王"]) val name: String, @MockValue(intValues = [18, 22, 30]) val age: Int)
