package com.airsaid.okmock.data;

import com.airsaid.okmock.api.Mock;

import java.util.List;

/**
 * @author airsaid
 */
public class StaticFieldProvider {

  @Mock
  public static boolean mockBool;

  @Mock
  public static String mockString;

  @Mock
  public static Person mockPerson;

  @Mock
  public static List<Person> mockPersons;
}
