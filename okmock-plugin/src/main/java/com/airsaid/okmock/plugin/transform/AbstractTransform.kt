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
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * @author airsaid
 */
abstract class AbstractTransform : Transform(), TransformHandle {

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
            jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)

        val jarInputClass: HashSet<Pair<String, ByteArray>> = hashSetOf()
        ZipInputStream(FileInputStream(inputJar)).use { zipInputStream ->
          var entry: ZipEntry? = zipInputStream.nextEntry
          while (entry != null) {
            if (!entry.isDirectory && entry.name.endsWith(SdkConstants.DOT_CLASS)) {
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
        val outputDir = transformInvocation.outputProvider.getContentLocation(dirInput.name,
          dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY)

        FileUtils.getAllFiles(inputDir).filter {
          it?.extension?.equals(SdkConstants.EXT_CLASS) ?: false
        }.toHashSet().forEach { inputFile ->
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
}