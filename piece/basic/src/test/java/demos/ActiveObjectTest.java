package demos;

import org.junit.Test;

import patterns.ActiveObject;

public class ActiveObjectTest {

  @Test
  public void test() throws InterruptedException {
    ActiveObject ao = new ActiveObject();

    ao.doSomeThing();

    ao.doSomeThing();
    ao.doSomeThing();
    ao.doSomeThing();

    ao.doSomeThingElse();
  }
}
