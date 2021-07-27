package com.airsaid.okmock.compiler;

import com.airsaid.okmock.annotations.Mock;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * @author airsaid
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class OKMockProcessor extends AbstractProcessor {

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> supported = new HashSet<>();
    supported.add(Mock.class.getCanonicalName());
    return supported;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    return false;
  }
}
