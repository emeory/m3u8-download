package com.gitee.nihaoa.core;

import com.gitee.nihaoa.entry.VideoMeta;
import com.gitee.nihaoa.threadpool.AdvanceTaskExecutor;

class Downloader {
  private AdvanceTaskExecutor taskExecutor;

  public Downloader(int threadCount){
    taskExecutor = new AdvanceTaskExecutor(threadCount);
    taskExecutor.startTaskExecutor();
  }

  public void downloadVideo(VideoMeta videoMeta){
    DowanloadAdvanceTask dowanloadTask;
    try {
      dowanloadTask = new DowanloadAdvanceTask(videoMeta);
      taskExecutor.submitAdvanceTask(dowanloadTask);
    } catch (Exception e) {
      System.out.println("发生异常，停止下载");
      e.printStackTrace();
    }
  }
}
