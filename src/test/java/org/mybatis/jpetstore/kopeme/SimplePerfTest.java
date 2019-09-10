/**
 *    Copyright 2010-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.jpetstore.kopeme;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.dagere.kopeme.annotations.PerformanceTest;
import de.dagere.kopeme.junit.testrunner.PerformanceTestRunnerJUnit;

@RunWith(PerformanceTestRunnerJUnit.class)
public class SimplePerfTest {

  @Test
  @PerformanceTest(executionTimes = 1000, warmupExecutions = 0, logFullData = true, useKieker = false, timeout = 999999999, repetitions = 200, dataCollectors = "ONLYTIME")
  public void test() {
    double x = 1;
    for (int i = 1; i <= 100; i++) {
      x = x * i;
      x = Math.sqrt(x);
      x = x * x - i++ / Math.exp(x);
      System.out.println(x);
    }
  }

}
