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
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.google.common.collect.ImmutableSet
import com.google.common.io.Files
import org.gradle.api.Project
import org.gradle.api.provider.Property
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * @author airsaid
 */
abstract class AbstractTransform(private val project: Project, private val isDebug: Property<Boolean>) : Transform(), TransformHandle {

  private var mStartInvocationTime = 0L

  override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
    TransformManager.CONTENT_CLASS

  override fun getScopes(): MutableSet<in QualifiedContent.Scope> =
    ImmutableSet.of(QualifiedContent.Scope.PROJECT, QualifiedContent.Scope.SUB_PROJECTS)

  override fun isIncremental() = true

  override fun isCacheable() = true

  override fun onTransformBefore(invocation: TransformInvocation) {
    if (isDebug.get()) {
      mStartInvocationTime = System.nanoTime()
    }
  }

  override fun transform(transformInvocation: TransformInvocation) {
    super.transform(transformInvocation)
    log("> Transform isIncremental: ${transformInvocation.isIncremental}")
    if (!transformInvocation.isIncremental) {
      transformInvocation.outputProvider.deleteAll()
    }

    onTransformBefore(transformInvocation)

    for (input in transformInvocation.inputs) {
      for (jarInput in input.jarInputs) {
        log("+---------------------------------------")
        log("| Start handler input jar: ${jarInput.file.canonicalPath}")

        if (transformInvocation.isIncremental) {
          log("| Skip: ${jarInput.status}")
          if (jarInput.status == Status.NOTCHANGED) {
            continue
          } else if (jarInput.status == Status.REMOVED) {
            jarInput.file.delete()
            continue
          }
        }

        val inputJar = jarInput.file
        val outputJar = transformInvocation.outputProvider.getContentLocation(
          jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR
        )
        handlerJar(transformInvocation, inputJar, outputJar)
      }

      for (dirInput in input.directoryInputs) {
        log("+---------------------------------------")
        log("| Start handler input dir: ${dirInput.file.canonicalPath}")

        val inputDir = dirInput.file
        val outputDir = transformInvocation.outputProvider.getContentLocation(
          dirInput.name, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY
        )

        if (transformInvocation.isIncremental) {
          if (dirInput.changedFiles.isEmpty()) {
            log("| Skip: changedFiles is empty!")
            continue
          }

          dirInput.changedFiles.forEach { (changedFile, state) ->
            if (state == Status.REMOVED) {
              log("| changedFile: ${changedFile.canonicalPath}, state: $state")
              changedFile.delete()
            } else {
              changedFile.filterTransformFile { inputFile ->
                log("| changedFile: ${inputFile.canonicalPath}, state: $state")
                val outputFile = File(outputDir, FileUtils.relativePossiblyNonExistingPath(inputFile, inputDir))
                inputFile.transform(transformInvocation, outputFile)
              }
            }
          }
        } else {
          inputDir.filterTransformFile { inputFile ->
            log("| ${inputFile.canonicalPath}")
            val outputFile = File(outputDir, FileUtils.relativePossiblyNonExistingPath(inputFile, inputDir))
            inputFile.transform(transformInvocation, outputFile)
          }
        }
      }
    }

    onTransformAfter(transformInvocation)
  }

  private fun handlerJar(invocation: TransformInvocation, inputJar: File, outputJar: File) {
    val jarInputClass: HashSet<Pair<String, ByteArray>> = hashSetOf()
    ZipInputStream(FileInputStream(inputJar)).use { zipInputStream ->
      var entry: ZipEntry? = zipInputStream.nextEntry
      while (entry != null) {
        if (!entry.isDirectory && entry.name.endsWith(SdkConstants.DOT_CLASS)) {
          log("| ${entry.name}")
          val outputBytes = onTransform(invocation, zipInputStream.readBytes())
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

  private fun File.filterTransformFile(filter: (file: File) -> Unit) {
    if (isDirectory) {
      FileUtils.getAllFiles(this)
        .toHashSet()
        .forEach { it.filterTransformFile(filter) }
    } else {
      filter(this)
    }
  }

  private fun File.transform(invocation: TransformInvocation, output: File) {
    Files.createParentDirs(output)
    if (extension == SdkConstants.EXT_CLASS) {
      onTransform(invocation, readBytes()).write(output)
    } else {
      copyTo(output, true)
    }
  }

  private fun ByteArray.write(output: File) =
    FileOutputStream(output).use { it.write(this) }

  override fun onTransformAfter(invocation: TransformInvocation) {
    log("+------ Execution time: ${(System.nanoTime() - mStartInvocationTime) / 1000 / 1000}ms ------")
  }

  private fun log(message: String) {
    if (isDebug.get()) {
      project.logger.quiet(message)
    }
  }
}