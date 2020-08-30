package com.gitee.nihaoa.threadpool;

public class SubTaskWrapper implements SubTask {
  private AdvanceTask parentTaskWrapper;
  private SubTask subTask;

  public SubTaskWrapper(AdvanceTask advanceTaskWrapper, SubTask subTask){
    parentTaskWrapper = advanceTaskWrapper;
    this.subTask = subTask;
  }

  public SubTask getSubTask() {
    return subTask;
  }

  @Override
  public void run() {
    try {
      subTask.run();
    }catch (Exception e){
      if (parentTaskWrapper != null)
        parentTaskWrapper.failedRetry(this);
      e.printStackTrace();
    }
    if (parentTaskWrapper != null){
      parentTaskWrapper.completeOne(subTask);
    }
  }
}
