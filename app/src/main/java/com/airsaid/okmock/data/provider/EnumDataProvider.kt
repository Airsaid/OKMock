package com.airsaid.okmock.data.provider

import com.airsaid.okmock.api.Mock
import com.airsaid.okmock.data.Color

/**
 * @author airsaid
 */
class EnumDataProvider {

  @Mock(randomSizeRange = [2, 2])
  lateinit var color: Color

  @Mock(randomSizeRange = [10, 20])
  lateinit var colorArray: Array<Color>

  @Mock
  lateinit var colorList: List<Color>
}