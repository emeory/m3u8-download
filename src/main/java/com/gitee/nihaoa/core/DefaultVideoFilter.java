package com.gitee.nihaoa.core;

import com.gitee.nihaoa.entry.VideoMeta;
import java.util.List;

public class DefaultVideoFilter implements VideoListFilter {

  @Override
  public VideoMeta filter(List<VideoMeta> videoList) {
    if (videoList != null && videoList.size() > 0){
      System.out.println("解析到多个视频，已默认选择最后一个视频进行下载");
      return videoList.get(videoList.size() - 1);
    }else {
      return null;
    }
  }
}
