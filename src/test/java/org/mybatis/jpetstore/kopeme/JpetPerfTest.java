
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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.jpetstore.domain.Order;

import de.dagere.kopeme.annotations.PerformanceTest;
import de.dagere.kopeme.junit.testrunner.PerformanceTestRunnerJUnit;

@RunWith(PerformanceTestRunnerJUnit.class)
public class JpetPerfTest {

  @Test
  @PerformanceTest(executionTimes = 8000, warmupExecutions = 200, logFullData = true, useKieker = false, timeout = 999999999, repetitions = 200, dataCollectors = "ONLYTIME")
  public void test() {
    Order order = new Order();
    order.setBillCity("Leipzig");
    order.setBillState("Sachsen");
    order.setBillCountry("Germany");
    ArrayList<String> orderPlace = new ArrayList<String>();
    orderPlace.add(order.getBillCity());
    orderPlace.add(order.getBillState());
    orderPlace.add(order.getBillCountry());

    ArrayList<String> expected = new ArrayList<String>();
    expected.add("Leipzig");
    expected.add("Sachsen");
    expected.add("Germany");

    assertEquals(orderPlace, expected);
  }
}
