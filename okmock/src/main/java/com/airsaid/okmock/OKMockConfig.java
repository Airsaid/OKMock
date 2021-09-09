/*
 * Copyright 2021 Airsaid. https://github.com/airsaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.airsaid.okmock;

import java.util.Arrays;

/**
 * Configuration object for {@link OKMock}.
 *
 * @author airsaid
 */
public class OKMockConfig {

  private final int[] randomSizeRange;

  public OKMockConfig(int[] randomSizeRange) {
    this.randomSizeRange = randomSizeRange;
    checkConfig();
  }

  public int getSize() {
    int startInclusive = randomSizeRange[0];
    int endExclusive = randomSizeRange[1];
    if (startInclusive == endExclusive) {
      return startInclusive;
    }
    return RandomDataProvider.nextInt(startInclusive, endExclusive);
  }

  private void checkConfig() {
    if (randomSizeRange.length != 2) {
      throw new IllegalArgumentException("RandomSizeRange must have two values. current length: " + randomSizeRange.length);
    }
    if (randomSizeRange[0] > randomSizeRange[1]) {
      throw new IllegalArgumentException("The randomSizeRange range is incorrect: " + Arrays.toString(randomSizeRange));
    }
  }

  @Override
  public String toString() {
    return "OKMockConfig{" +
        "randomSizeRange=" + Arrays.toString(randomSizeRange) +
        '}';
  }
}
