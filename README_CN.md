[![Version](https://img.shields.io/maven-central/v/com.airsaid/okmock)](https://plugins.gradle.org/plugin/com.airsaid.okmock)

# OKMock
:balloon: OKMock 是一个用与 Android 上快速填充字段数据的 Gradle Plugin。


# 使用
1. 将下面的代码添加到 ```build.gradle``` 中:

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

2. 在需要填充数据的字段上添加 ```@Mock``` 注解:
```
@Mock
public Person person;
```

3. 使用这个字段。

# 自定义数据
默认情况下，填充数据使用的是随机的数据。如果需要自定义数据，可使用 ```@MockValue``` 注解在对象的构造方法参数上：
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

# 更多示例
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
