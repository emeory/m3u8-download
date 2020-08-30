package com.gitee.nihaoa.entry;

import java.io.File;

public class VideoTs{
  private VideoMeta videoMeta;

  private Long id;
  private Long videoId;
  private int tsId;
  private String url;
  private String path;
  private String name;
  private File file;


  public VideoTs(){
  }

  public VideoTs(VideoMeta videoMeta, int tsId, String tsUrl) {
    this.videoMeta = videoMeta;
    videoId = videoMeta.getId();
    this.tsId = tsId;
    url = tsUrl;
    path = videoMeta.getTempPath();
    name = tsId + "." + videoMeta.getUuid() + ".ts";
  }

  public VideoMeta getVideoMeta() {
    return videoMeta;
  }

  public void setVideoMeta(VideoMeta videoMeta) {
    this.videoMeta = videoMeta;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getVideoId() {
    return videoId;
  }

  public void setVideoId(Long videoId) {
    this.videoId = videoId;
  }

  public int getTsId() {
    return tsId;
  }

  public void setTsId(int tsId) {
    this.tsId = tsId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public byte[] getKeyByte() {
    return videoMeta.getKeyByte();
  }

  public byte[] getKeyIvByte() {
    return videoMeta.getKeyIvByte();
  }
}
