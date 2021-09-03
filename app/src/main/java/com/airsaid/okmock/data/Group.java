package com.airsaid.okmock.data;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * @author airsaid
 */
public class Group {

  private String name;

  private List<Person> children;

  public Group(String name) {
    this.name = name;
  }

  public Group(String name, List<Person> children) {
    this.name = name;
    this.children = children;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Person> getChildren() {
    return children;
  }

  public void setChildren(List<Person> children) {
    this.children = children;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Group group = (Group) o;
    return Objects.equals(name, group.name) && Objects.equals(children, group.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, children);
  }

  @NonNull
  @Override
  public String toString() {
    return "Group{" +
        "name='" + name + '\'' +
        ", children=" + children +
        '}';
  }
}
