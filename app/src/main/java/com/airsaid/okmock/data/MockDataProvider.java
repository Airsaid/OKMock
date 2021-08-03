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
  public List<Person> mockList;

  @Mock
  public LinkedList<Integer> mockList2;

  @Mock
  public Set<Integer> mockSet;

  @Mock
  public LinkedHashSet<String> mockSet2;

  @Mock
  public Map<Integer, Integer> mockMap;

  @Mock
  public Map<Integer, Person> mockMap2;

  @Mock
  public List<List<Integer>> mockNested;

  @Mock
  public List<List<Set<Integer>>> mockNested2;

  @Mock
  public Map<Integer, List<OKMock>> mockNested3;
}
