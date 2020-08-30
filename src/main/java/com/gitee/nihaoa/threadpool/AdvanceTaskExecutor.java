package com.gitee.nihaoa.threadpool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AdvanceTaskExecutor {
  private BlockingQueue<SubTask> taskQueue;
  private List<Thread> threadPool;
  private int threadCount;

  public AdvanceTaskExecutor(int threadCount){
    int cpu = Runtime.getRuntime().availableProcessors();
    this.threadCount = Math.max(threadCount, cpu);
    taskQueue = new LinkedBlockingQueue<>();
    threadPool = new LinkedList<>();
  }

  public void submitAdvanceTask(AdvanceTask advanceTask) {
    AdvanceTaskWrapper advanceTaskWrapper = new AdvanceTaskWrapper(this, advanceTask);
    List<SubTask> subTaskList = advanceTask.getSubTaskList();
    try {
      for (SubTask subTask : subTaskList) {
        SubTaskWrapper subTaskWrapper = new SubTaskWrapper(advanceTaskWrapper, subTask);
        taskQueue.put(subTaskWrapper);
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public void submitTask(SubTask task){
    SubTaskWrapper subTaskWrapper = new SubTaskWrapper(null, task);
    try {
      taskQueue.put(subTaskWrapper);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public void startTaskExecutor() {
    for (int i = 0; i < threadCount; i++) {
      ExecutorThread threadItem = new ExecutorThread("Task_Thread_Pool_" + i);
      threadPool.add(threadItem);
      threadItem.start();
    }
    System.out.println("Advance Task Thread Pool Started...");
  }

  private class ExecutorThread extends Thread{
    public ExecutorThread() {
    }

    public ExecutorThread(String name) {
      super(name);
    }

    @Override
    public void run() {
      try {
        while (!isInterrupted()) {
          SubTask subTask = taskQueue.take();
          subTask.run();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(getName() + " 结束");
    }
  }

  public void close(){
    for (Thread thread : threadPool) {
      thread.interrupt();
    }
  }

}
