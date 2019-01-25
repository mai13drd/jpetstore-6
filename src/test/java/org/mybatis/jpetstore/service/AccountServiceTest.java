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

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.mapper.AccountMapper;
import de.dagere.kopeme.annotations.Assertion;
import de.dagere.kopeme.annotations.MaximalRelativeStandardDeviation;
import de.dagere.kopeme.junit.testrunner.PerformanceTestRunnerJUnit;
import org.junit.rules.TestRule;
import org.junit.Rule;
import de.dagere.kopeme.junit.rule.KoPeMeRule;

/**
 * @author Eduardo Macarron
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

  @Mock
  private AccountMapper accountMapper;

  @InjectMocks
  private AccountService accountService;

  @Test
  @de.dagere.kopeme.annotations.PerformanceTest(executionTimes = 100, warmupExecutions = 0, logFullData = true, useKieker = true, timeout = 300000, repetitions = 100, dataCollectors = "ONLYTIME")
  public void shouldCallTheMapperToInsertAnAccount() {
    // given
    Account account = new Account();
    // when
    accountService.insertAccount(account);
    // then
    verify(accountMapper).insertAccount(eq(account));
    verify(accountMapper).insertProfile(eq(account));
    verify(accountMapper).insertSignon(eq(account));
  }

  @Rule()
  public TestRule kopemeRule = new KoPeMeRule(this);
}
