package com.gitee.nihaoa.core;

import com.gitee.nihaoa.entry.VideoTs;
import com.gitee.nihaoa.threadpool.SubTask;
import com.gitee.nihaoa.utils.CommonUtil;
import com.gitee.nihaoa.utils.HttpClientUtil;
import okhttp3.Response;

import java.io.File;
import java.io.FileOutputStream;

public class TsFileTask implements SubTask {
    private VideoTs videoFileTS;
    private File tsFile;

    public TsFileTask(VideoTs videoFileTS){
      this.videoFileTS = videoFileTS;
    }

    public String getVideoName(){
      return videoFileTS.getName();
    }

    @Override
    public void run() {
      tsFile = new File(videoFileTS.getPath() + videoFileTS.getName());
      try {
        if (tsFile.exists()){
          tsFile.delete();
        }else {
          tsFile.createNewFile();
          FileOutputStream fileOutputStream = new FileOutputStream(tsFile);
          Response response = HttpClientUtil.sendGetRequest(videoFileTS.getUrl(), 200);
          byte[] byteData = response.body().bytes();
          if (videoFileTS.getKeyByte() != null){
            byte[] keyByte = videoFileTS.getKeyByte();
            byte[] ivByte = videoFileTS.getKeyIvByte();
            byteData = CommonUtil.decrypt(byteData, keyByte, ivByte);
          }
          fileOutputStream.write(byteData);
          fileOutputStream.flush();
          fileOutputStream.close();
          response.close();
        }
        videoFileTS.setFile(tsFile);
      }catch (Exception e){
        System.out.println(videoFileTS.getName() + " 下载失败");
        tsFile.delete();
        e.printStackTrace();
        System.exit(0);
      }
    }
  }