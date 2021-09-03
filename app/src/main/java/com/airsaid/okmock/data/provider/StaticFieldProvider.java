package com.airsaid.okmock.data.provider;

import com.airsaid.okmock.api.Mock;
import com.airsaid.okmock.data.Person;

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
