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
package org.mybatis.jpetstore.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.LineItem;
import org.mybatis.jpetstore.domain.Order;
import org.mybatis.jpetstore.mapper.ItemMapper;
import org.mybatis.jpetstore.mapper.LineItemMapper;
import org.mybatis.jpetstore.mapper.OrderMapper;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.rules.TestRule;
import org.junit.Rule;
import de.dagere.kopeme.junit.rule.KoPeMeRule;

/**
 * @author coderliux
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

  @Mock
  private ItemMapper itemMapper;

  @Mock
  private OrderMapper orderMapper;

  @Mock
  private LineItemMapper lineItemMapper;

  @InjectMocks
  private OrderService orderService;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  @de.dagere.kopeme.annotations.PerformanceTest(executionTimes = 4000, warmupExecutions = 200, logFullData = true, useKieker = false, timeout = Integer.MAX_VALUE, repetitions = 200, dataCollectors = "ONLYTIME")
  public void shouldReturnOrderWhenGivenOrderIdWithOutLineItems() {
    // given
    int orderId = 1;
    Order order = new Order();
    List<LineItem> lineItems = new ArrayList<LineItem>();

    // when
    when(orderMapper.getOrder(orderId)).thenReturn(order);
    when(lineItemMapper.getLineItemsByOrderId(orderId)).thenReturn(lineItems);
    // then
    assertThat(orderService.getOrder(orderId)).isEqualTo(order);
    assertThat(orderService.getOrder(orderId).getLineItems()).isEmpty();
  }

  @Test
  @de.dagere.kopeme.annotations.PerformanceTest(executionTimes = 10000, warmupExecutions = 0, logFullData = false, useKieker = false, timeout = Integer.MAX_VALUE, repetitions = 200, dataCollectors = "ONLYTIME")
  public void shouldReturnOrderWhenGivenOrderIdExistedLineItems() {
    // given
    int orderId = 1;
    Order order = new Order();
    List<LineItem> lineItems = new ArrayList<LineItem>();
    LineItem item = new LineItem();
    String itemId = "abc";
    item.setItemId(itemId);
    lineItems.add(item);
    // when
    when(orderMapper.getOrder(orderId)).thenReturn(order);
    when(lineItemMapper.getLineItemsByOrderId(orderId)).thenReturn(lineItems);
    when(itemMapper.getItem(itemId)).thenReturn(new Item());
    when(itemMapper.getInventoryQuantity(itemId)).thenReturn(new Integer(5));
    // then
    Order expectedOrder = orderService.getOrder(orderId);
    assertThat(expectedOrder).isEqualTo(order);
    assertThat(expectedOrder.getLineItems()).hasSize(1);
    assertThat(expectedOrder.getLineItems().get(0).getItem().getQuantity()).isEqualTo(5);

  }

  @Rule()
  public TestRule kopemeRule = new KoPeMeRule(this);

  /*
   * For running a performance-test, you have to make sure that the mocks are reset after each iteration! If not, your test will slow down and you will eventually run out of
   * memory! See: https://stackoverflow.com/questions/17437660/mockito-throws-an-outofmemoryerror-on-a-simple-test
   */
  @After
  public void resetMocks() {    
    Mockito.reset(itemMapper);
    Mockito.reset(orderMapper);
    Mockito.reset(lineItemMapper);
  }

}