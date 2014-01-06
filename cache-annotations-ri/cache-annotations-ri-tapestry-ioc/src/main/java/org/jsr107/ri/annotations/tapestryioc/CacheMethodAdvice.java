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

import org.apache.tapestry5.plastic.MethodAdvice;
import org.jsr107.ri.annotations.InterceptorType;

/**
 * Common subclass for all jcache interceptors
 *
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 * @version $Revision$
 */
public interface CacheMethodAdvice extends MethodAdvice {
  /**
   * @return The type of intereceptor
   */
  InterceptorType getInterceptorType();
}
