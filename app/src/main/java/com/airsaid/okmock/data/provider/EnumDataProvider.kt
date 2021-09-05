package com.airsaid.okmock.data.provider

import com.airsaid.okmock.api.Mock
import com.airsaid.okmock.data.Color

/**
 * @author airsaid
 */
class EnumDataProvider {

  @Mock
  lateinit var color: Color

  @Mock
  lateinit var colorArray: Array<Color>

  @Mock
  lateinit var colorList: List<Color>
}