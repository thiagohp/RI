/**
 *  Copyright 2011 Terracotta, Inc.
 *  Copyright 2011 Oracle America Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.jsr107.ri.annotations.tapestryioc.module;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.cache.annotation.CacheKeyGenerator;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResolverFactory;
import javax.cache.annotation.CacheResult;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Match;
import org.jsr107.ri.annotations.CacheContextSource;
import org.jsr107.ri.annotations.DefaultCacheKeyGenerator;
import org.jsr107.ri.annotations.DefaultCacheResolverFactory;
import org.jsr107.ri.annotations.tapestryioc.CacheLookupUtil;
import org.jsr107.ri.annotations.tapestryioc.CacheMethodAdvice;
import org.jsr107.ri.annotations.tapestryioc.CachePutMethodAdvice;
import org.jsr107.ri.annotations.tapestryioc.CacheRemoveAllMethodAdvice;
import org.jsr107.ri.annotations.tapestryioc.CacheRemoveMethodAdvice;
import org.jsr107.ri.annotations.tapestryioc.CacheResultMethodAdvice;

/**
 * Standard cache module for binding all cache interceptors to their respective annotations. 
 *
 * @author Thiago H. de Paula Figueiredo.
 * @version $Revision$
 */
public final class CacheAnnotationsModule {

  private CacheAnnotationsModule() {}

  /**
   * Declares some services.
   * @param binder a {@link ServiceBinder}.
   */
  public static void bind(ServiceBinder binder) {
    binder.bind(CacheKeyGenerator.class, DefaultCacheKeyGenerator.class);
    binder.bind(CacheResolverFactory.class, DefaultCacheResolverFactory.class);
    binder.bind(CacheContextSource.class, CacheLookupUtil.class);
  }
  
  /**
   * Applies the advice to the services.
   * @param receiver a {@link MethodAdviceReceiver}.
   * @param objectLocator an {@link ObjectLocator}.
   */
  @Match("*")
  public static void advise(MethodAdviceReceiver receiver, ObjectLocator objectLocator) {
    advise(CachePut.class, objectLocator.autobuild(CachePutMethodAdvice.class), receiver);
    advise(CacheRemoveAll.class, objectLocator.autobuild(CacheRemoveAllMethodAdvice.class), receiver);
    advise(CacheRemove.class, objectLocator.autobuild(CacheRemoveMethodAdvice.class), receiver);
    advise(CacheResult.class, objectLocator.autobuild(CacheResultMethodAdvice.class), receiver);
  }
  
  private static void advise(Class<? extends Annotation> annotationClass, CacheMethodAdvice advice, MethodAdviceReceiver methodAdviceReceiver) {
    if (methodAdviceReceiver.getClassAnnotationProvider().getAnnotation(annotationClass) != null) {
      methodAdviceReceiver.adviseAllMethods(advice);
    } else {
      for (Method method : methodAdviceReceiver.getInterface().getMethods()) {
        if (methodAdviceReceiver.getMethodAnnotation(method, annotationClass) != null) {
          methodAdviceReceiver.adviseMethod(method, advice);
        }
      }
    }
  }

}
