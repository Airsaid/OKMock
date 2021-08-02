package com.airsaid.okmock.data;

import com.airsaid.okmock.OKMock;
import com.airsaid.okmock.api.Mock;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author airsaid
 */
public class MockDataProvider {
  @Mock
  public boolean mockBool;

  @Mock
  public Boolean mockBoolWrap;

  @Mock
  public char mockChar;

  @Mock
  public Character mockCharWrap;

  @Mock
  public byte mockByte;

  @Mock
  public Byte mockByteWrap;

  @Mock
  public short mockShort;

  @Mock
  public Short mockShortWrap;

  @Mock
  public int mockInt;

  @Mock
  public Integer mockIntWrap;

  @Mock
  public float mockFloat;

  @Mock
  public Float mockFloatWrap;

  @Mock
  public long mockLong;

  @Mock
  public Long mockLongWrap;

  @Mock
  public double mockDouble;

  @Mock
  public Double mockDoubleWrap;

  @Mock
  public String mockString;

  @Mock
  public Person mockPerson;

  @Mock
  public Group mockGroup;

  @Mock
  public List<String> mockList;

  @Mock
  public LinkedList<String> mockList2;

  @Mock
  public Set<String> mockSet;

  @Mock
  public LinkedHashSet<String> mockSet2;

  @Mock
  public Map<Integer, String> mockMap;

  @Mock
  public Map<Integer, Person> mockMap2;

  @Mock
  public List<List<String>> mockNested;

  @Mock
  public List<List<Set<String>>> mockNested2;

  @Mock
  public Map<Integer, List<OKMock>> mockNested3;
}
