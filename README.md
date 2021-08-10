[![Version](https://img.shields.io/gradle-plugin-portal/v/com.airsaid.okmock)](https://img.shields.io/gradle-plugin-portal/v/com.airsaid.okmock)

# OKMock
:balloon: OKMock is a Android mock tool, used to quickly auto-fill field data.

# Usage
1. Add the following to your ```build.gradle```:

```
buildscript {
    repositories {
        ......
        maven { url "https://plugins.gradle.org/m2/" }
    }
    dependencies {
        ......
        classpath "com.airsaid:okmock-plugin:$version"
    }
}
```

```
plugins {
    ......
    id "com.airsaid.okmock"
}
dependencies {
    ......
    debugImplementation "com.airsaid:okmock:$version"
}

```

2. Add ```@Mock``` annotations to the fields to be filled with data:
```
@Mock
public Person person;
```

3. Use the field.

# Custom Data
Random data is used for filling by default, or you can use the ```@MockValue``` annotation to specify the data to be filled:
```
public class Person {

  private String name;
  private int age;

  public Person(@MockValue(stringValues = {"Airsaid", "Jack", "John", "Otto"}) String name, @MockValue(intValues = {18, 22, 30}) int age) {
    this.name = name;
    this.age = age;
  }
}
```

# More Examples
```
@Mock
public List<Person> personList;

@Mock
public Person[] personArray;

@Mock
public Person[][] personArrayDimension;

@Mock
public List<Person>[] personListArray;

@Mock
public LinkedHashSet<String> stringSet;

@Mock
public Map<Integer, Person> personMap;

@Mock
public Map<Integer, Person>[] personMapArray;

@Mock
public Map<Integer, List<Person>> personMapNested;
```


# License
```
Copyright 2021 Airsaid

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
