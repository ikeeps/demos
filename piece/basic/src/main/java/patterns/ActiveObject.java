package patterns;

import java.util.concurrent.LinkedBlockingDeque;

public class ActiveObject {

  private LinkedBlockingDeque<Runnable> queue;
  private double value;

  public ActiveObject() {
    queue = new LinkedBlockingDeque<>(100);
    Thread thread = new Thread(() -> {
      while (true) {
        try {
          queue.take().run();
          System.out.println(value);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    });

    thread.start();
  }

  public void doSomeThing() throws InterruptedException {
    queue.put(() -> value = value + 2.0);
  }

  public void doSomeThingElse() throws InterruptedException {
    queue.put(() -> value = 100);
  }
}
