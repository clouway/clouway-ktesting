package com.clouway.telcong.testing

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import org.junit.rules.ExternalResource

/**
 * @author Georgi Georgiev (georgi.georgiev@clouway.com)
 */
class DatastoreRule : ExternalResource() {

  private val helper = LocalServiceTestHelper(LocalDatastoreServiceTestConfig().setApplyAllHighRepJobPolicy())

  override fun before() {
    helper.setUp()
  }

  override fun after() {
    helper.tearDown()
  }
}