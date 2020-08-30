package com.gitee.nihaoa.core;

import com.gitee.nihaoa.entry.VideoMeta;
import java.util.List;

public interface VideoListFilter {

  public VideoMeta filter(List<VideoMeta> videoList);

}
