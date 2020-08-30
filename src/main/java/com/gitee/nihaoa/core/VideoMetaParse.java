package com.gitee.nihaoa.core;

import com.gitee.nihaoa.entry.VideoMeta;

import java.util.List;

public interface VideoMetaParse {

  public List<VideoMeta> parseVideoMeta(String path, String name);

}
