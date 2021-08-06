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
public class CollectionMockDataTest {

  @Mock
  public List<Boolean> booleanList;

  @Mock
  public List<Person> personList;

  @Mock
  public LinkedList<Group> groupList;

  @Mock
  public Set<Integer> intSet;

  @Mock
  public LinkedHashSet<String> stringSet;

  @Mock
  public Map<Integer, Integer> intIntMap;

  @Mock
  public Map<Integer, Person> intPersonMap;

  @Mock
  public List<List<Integer>> listNested;

  @Mock
  public List<List<Set<Integer>>> listNested2;

  @Mock
  public Map<Integer, List<OKMock>> mapNested;
}
