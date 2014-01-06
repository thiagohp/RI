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

package org.jsr107.ri.annotations.tapestryioc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.cache.annotation.CacheKeyGenerator;
import javax.cache.annotation.CacheResolverFactory;
import javax.inject.Singleton;

import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.jsr107.ri.annotations.AbstractCacheLookupUtil;
import org.jsr107.ri.annotations.InternalCacheInvocationContext;
import org.jsr107.ri.annotations.InternalCacheKeyInvocationContext;
import org.jsr107.ri.annotations.StaticCacheInvocationContext;
import org.jsr107.ri.annotations.StaticCacheKeyInvocationContext;

/**
 * Adapted to Tapestry-IoC from the Guice implementation.
 * @author Thiago H. de Paula Figueiredo
 * @version $Revision$
 */
@Singleton
public class CacheLookupUtil extends AbstractCacheLookupUtil<MethodInvocation> {

  private final ObjectLocator objectLocator;
  private final CacheKeyGenerator defaultCacheKeyGenerator;
  private final CacheResolverFactory defaultCacheResolverFactory;

  /**
   * Single constructor of this class.
   * @param defaultCacheKeyGenerator    the default CacheKeyGenerator
   * @param defaultCacheResolverFactory the default CacheResolverFactory
   */
  public CacheLookupUtil(ObjectLocator objectLocator,
                         CacheKeyGenerator defaultCacheKeyGenerator,
                         CacheResolverFactory defaultCacheResolverFactory) {
    this.objectLocator = objectLocator;
    this.defaultCacheKeyGenerator = defaultCacheKeyGenerator;
    this.defaultCacheResolverFactory = defaultCacheResolverFactory;
  }

  @SuppressWarnings({"unchecked", "rawtypes" })
  @Override
  protected InternalCacheKeyInvocationContext<? extends Annotation> createCacheKeyInvocationContextImpl(
      StaticCacheKeyInvocationContext<? extends Annotation> staticCacheKeyInvocationContext, MethodInvocation invocation) {
    return new TapestryIoCInternalCacheKeyInvocationContext(staticCacheKeyInvocationContext, invocation);
  }

  @SuppressWarnings({"unchecked", "rawtypes" })
  @Override
  protected InternalCacheInvocationContext<? extends Annotation> createCacheInvocationContextImpl(
      StaticCacheInvocationContext<? extends Annotation> staticCacheInvocationContext, MethodInvocation invocation) {
    return new TapestryIoCInternalCacheInvocationContext(staticCacheInvocationContext, invocation);
  }

  @Override
  protected Class<?> getTargetClass(MethodInvocation invocation) {
    return invocation.getInstance().getClass();
  }

  @Override
  protected Method getMethod(MethodInvocation invocation) {
    return invocation.getMethod();
  }

  @Override
  protected <T> T getObjectByType(Class<T> type) {
    return this.objectLocator.getObject(type, null);
  }

  @Override
  protected CacheKeyGenerator getDefaultCacheKeyGenerator() {
    return this.defaultCacheKeyGenerator;
  }

  @Override
  protected CacheResolverFactory getDefaultCacheResolverFactory() {
    return this.defaultCacheResolverFactory;
  }
}
