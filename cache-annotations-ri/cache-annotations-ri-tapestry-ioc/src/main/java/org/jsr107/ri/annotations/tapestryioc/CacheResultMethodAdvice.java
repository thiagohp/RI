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

import org.apache.tapestry5.plastic.MethodInvocation;
import org.jsr107.ri.annotations.AbstractCacheResultInterceptor;
import org.jsr107.ri.annotations.CacheContextSource;
import org.jsr107.ri.annotations.InterceptorType;

/**
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 * @version $Revision$
 */
public class CacheResultMethodAdvice extends AbstractCacheResultInterceptor<MethodInvocation> implements CacheMethodAdvice {

  private final CacheContextSource<MethodInvocation> cacheContextSource;

  /** Single constructor of this class. */
  public CacheResultMethodAdvice(CacheContextSource<MethodInvocation> cacheContextSource) {
    this.cacheContextSource = cacheContextSource;
  }

  @Override
  public InterceptorType getInterceptorType() {
    return InterceptorType.CACHE_RESULT;
  }

  @Override
  public void advise(MethodInvocation invocation) {
    try {
      invocation.setReturnValue(this.cacheResult(cacheContextSource, invocation));
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected Object proceed(MethodInvocation invocation) throws Throwable {
    invocation.proceed();
    if (invocation.didThrowCheckedException()) {
      invocation.rethrow();
    }
    return invocation.getReturnValue();
  }

}
