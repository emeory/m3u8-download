package com.gitee.nihaoa.threadpool;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AdvanceTaskWrapper implements AdvanceTask {
  private AtomicInteger subTaskCount;
  private AdvanceTask advanceTask;
  private AdvanceTaskExecutor executor;

  public AdvanceTaskWrapper(AdvanceTaskExecutor executor, AdvanceTask advanceTask){
    this.advanceTask = advanceTask;
    this.executor = executor;
    subTaskCount = new AtomicInteger(advanceTask.getSubTaskList().size());
  }

  @Override
  public void completeAll() {
    advanceTask.completeAll();
  }

  @Override
  public boolean failedRetry(SubTask subTask) {
    SubTaskWrapper subTaskWrapper = (SubTaskWrapper) subTask;
    boolean retry = advanceTask.failedRetry(subTaskWrapper.getSubTask());
    if (retry){
      executor.submitTask(subTask);
    }else {
      checkComplete();
    }
    return retry;
  }

  @Override
  public void completeOne(SubTask subTask) {
    advanceTask.completeOne(subTask);
    checkComplete();
  }

  private void checkComplete() {
    int remain = subTaskCount.decrementAndGet();
    if (remain == 0) {
      advanceTask.completeAll();
    }
  }

  @Override
  public List<SubTask> getSubTaskList() {
    return advanceTask.getSubTaskList();
  }
}
