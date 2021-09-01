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

package com.airsaid.okmock.plugin.transform

import com.android.SdkConstants
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.google.common.collect.ImmutableSet
import com.google.common.io.Files
import org.gradle.api.Project
import org.gradle.api.provider.Property
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * @author airsaid
 */
abstract class AbstractTransform(private val project: Project, private val isDebug: Property<Boolean>) : Transform(), TransformHandle {

  override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
    TransformManager.CONTENT_CLASS

  override fun getScopes(): MutableSet<in QualifiedContent.Scope> =
    ImmutableSet.of(QualifiedContent.Scope.PROJECT, QualifiedContent.Scope.SUB_PROJECTS)

  override fun isIncremental() = false

  override fun transform(transformInvocation: TransformInvocation) {
    super.transform(transformInvocation)
    if (!transformInvocation.isIncremental) {
      transformInvocation.outputProvider.deleteAll()
    }

    onTransformBefore(transformInvocation)

    transformInvocation.inputs.forEach { input ->
      input.jarInputs.forEach { jarInput ->
        val inputJar = jarInput.file
        val outputJar = transformInvocation.outputProvider.getContentLocation(
          jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR
        )
        log("+---------------------------------------")
        log("| Start handler input jar: ${inputJar.canonicalPath}")
        val jarInputClass: HashSet<Pair<String, ByteArray>> = hashSetOf()
        ZipInputStream(FileInputStream(inputJar)).use { zipInputStream ->
          var entry: ZipEntry? = zipInputStream.nextEntry
          while (entry != null) {
            if (!entry.isDirectory && entry.name.endsWith(SdkConstants.DOT_CLASS)) {
              log("| ${entry.name}")
              val outputBytes = onTransform(transformInvocation, zipInputStream.readBytes())
              jarInputClass.add(entry.name to outputBytes)
            }
            entry = zipInputStream.nextEntry
          }
        }

        Files.createParentDirs(outputJar)
        ZipOutputStream(FileOutputStream(outputJar)).use { zos ->
          jarInputClass.forEach {
            val entryName = it.first
            val outputBytes = it.second
            zos.putNextEntry(ZipEntry(entryName))
            zos.write(outputBytes)
          }
        }
      }

      input.directoryInputs.forEach { dirInput ->
        val inputDir = dirInput.file
        log("+---------------------------------------")
        log("| Start handler input dir: ${inputDir.canonicalPath}")
        val outputDir = transformInvocation.outputProvider.getContentLocation(
          dirInput.name,
          dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY
        )
        FileUtils.getAllFiles(inputDir).filter {
          it?.extension?.equals(SdkConstants.EXT_CLASS) ?: false
        }.toHashSet().forEach { inputFile ->
          log("| ${inputFile.canonicalPath}")
          val outputBytes = onTransform(transformInvocation, inputFile.inputStream().readBytes())

          val outputFile = File(outputDir, FileUtils.relativePossiblyNonExistingPath(inputFile, inputDir))

          Files.createParentDirs(outputFile)
          FileOutputStream(outputFile).use {
            it.write(outputBytes)
          }
        }
      }
    }

    onTransformAfter(transformInvocation)
  }

  private fun log(message: String) {
    if (isDebug.get()) {
      project.logger.quiet(message)
    }
  }
}