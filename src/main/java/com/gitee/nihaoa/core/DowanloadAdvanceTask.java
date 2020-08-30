package com.gitee.nihaoa.core;

import com.gitee.nihaoa.entry.VideoMeta;
import com.gitee.nihaoa.entry.VideoTs;
import com.gitee.nihaoa.threadpool.AdvanceTask;
import java.util.concurrent.atomic.AtomicInteger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

public class DowanloadAdvanceTask implements AdvanceTask<TsFileTask> {
  private VideoMeta videoMeta;
  private List<TsFileTask> tsFileTaskList;
  private File videoFile;
  private File tempPath;
  private AtomicInteger atomicInteger;

  public DowanloadAdvanceTask(VideoMeta videoMeta) throws Exception{
    this.videoMeta = videoMeta;
    tempPath = new File(videoMeta.getTempPath());
    tempPath.mkdirs();
    videoFile = new File(videoMeta.getPath() + videoMeta.getName());
    if (videoFile.exists()){
      videoFile.delete();
    }else {
      videoFile.createNewFile();
    }
  }


  @Override
  public void completeAll() {
    try {
      System.out.println("全部分片下载完成，开始合并文件");
      FileOutputStream outputStream = new FileOutputStream(videoFile);
      byte[] tempByte = new byte[1024 * 1024 * 5];
      int readLen;
      for (VideoTs fileTS : videoMeta.getVideoTsList()) {
        FileInputStream inputStream = new FileInputStream(fileTS.getFile());
        while ((readLen = inputStream.read(tempByte)) != -1){
          outputStream.write(tempByte, 0, readLen);
        }
        inputStream.close();
        fileTS.getFile().delete();
      }
      outputStream.flush();
      outputStream.close();
      tempPath.delete();
      System.out.println(videoMeta.getName() + " 下载合并成功");
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public boolean failedRetry(TsFileTask subTask) {
    return false;
  }

  @Override
  public void completeOne(TsFileTask subTask) {
    System.out.println(subTask.getVideoName() + " 下载完成, 剩余：" + atomicInteger.decrementAndGet());
  }

  @Override
  public List<TsFileTask> getSubTaskList() {
    if (tsFileTaskList != null){
      return tsFileTaskList;
    }else {
      tsFileTaskList = new LinkedList<>();
      List<VideoTs> videoFileList = videoMeta.getVideoTsList();
      for (VideoTs videoFileTS : videoFileList) {
        TsFileTask tsFileTask = new TsFileTask(videoFileTS);
        tsFileTaskList.add(tsFileTask);
      }
      atomicInteger = new AtomicInteger(tsFileTaskList.size());
      return tsFileTaskList;
    }
  }
}
