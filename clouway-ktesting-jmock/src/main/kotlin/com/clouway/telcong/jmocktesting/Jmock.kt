package com.clouway.telcong.jmocktesting

import org.hamcrest.Matcher
import org.jmock.AbstractExpectations
import org.jmock.Expectations
import org.jmock.Mockery
import org.jmock.integration.junit4.JUnitRuleMockery

/**
 * @author Ianislav Nachev <qnislav.nachev@clouway.com>
 */

inline fun <reified T: Any> Mockery.mock(): T = mock(T::class.java)


fun JUnitRuleMockery.expecting(block: Expektations.() -> Unit){
  this.checking(Expektations().apply(block))
}

class Expektations : Expectations() {

  inline fun <reified T : Any> spying(value: T): T {
    super.with(AbstractExpectations.anything<T>())
    return value
  }

}