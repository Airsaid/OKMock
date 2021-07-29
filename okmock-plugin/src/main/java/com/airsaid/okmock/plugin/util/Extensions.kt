package com.airsaid.okmock.plugin.util

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.GradleException
import org.gradle.api.Project

fun Project.getExtension(): BaseExtension {
  val appExtension = extensions.findByType(AppExtension::class.java)
  if (appExtension != null) {
    return appExtension
  }

  val libraryExtension = extensions.findByType(LibraryExtension::class.java)
  if (libraryExtension != null) {
    return libraryExtension
  }

  throw GradleException("$displayName is not an Android project.")
}