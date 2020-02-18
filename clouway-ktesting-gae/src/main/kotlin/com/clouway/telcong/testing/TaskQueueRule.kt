package com.clouway.telcong.testing

import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig
import org.junit.rules.ExternalResource

/**
 * @author Stanimir Iliev <stanimir.iliev@clouway.com />
 */

class TaskQueueRule(queueXmlPath: String) : ExternalResource() {

  private val helper = LocalServiceTestHelper(LocalTaskQueueTestConfig().setQueueXmlPath(queueXmlPath))

  override fun before() {
    helper.setUp()
  }

  override fun after() {
    helper.tearDown()
  }
}