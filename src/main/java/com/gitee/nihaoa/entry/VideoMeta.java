package com.gitee.nihaoa.entry;

import com.gitee.nihaoa.utils.CommonUtil;
import com.gitee.nihaoa.utils.HttpClientUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.Response;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class VideoMeta{
  private Long id;
  private int version;
  private String path;
  private String tempPath;
  private String name;
  private String uuid;
  private String url;
  private byte[] keyByte;
  private String keyUrl;
  private byte[] keyIvByte;
  private String keyMethod;
  private String property;

  private List<VideoTs> videoTsList;

  public VideoMeta(){
  }

  public VideoMeta(String videoPath, String videoName, String url){
    this.path = videoPath;
    this.name = videoName;
    this.url = url;
    uuid = CommonUtil.get16MD5(name + url);
    tempPath = path + uuid + '/';
    System.out.println("文件名称映射：" + name + " -> " + uuid);
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public String getKeyMethod() {
    return keyMethod;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
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

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public byte[] getKeyByte() {
    return keyByte;
  }

  public void setKeyByte(byte[] keyByte) {
    this.keyByte = keyByte;
  }

  public String getKeyUrl() {
    return keyUrl;
  }

  public void setKeyUrl(String keyUrl) {
    this.keyUrl = keyUrl;
  }

  public byte[] getKeyIvByte() {
    return keyIvByte;
  }

  public void setKeyIvByte(byte[] keyIvByte) {
    this.keyIvByte = keyIvByte;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public void setKeyMethod(String keyMethod) {
    this.keyMethod = keyMethod;
  }

  public List<VideoTs> getVideoTsList() {
    return videoTsList;
  }

  public void setVideoTsList(List<VideoTs> videoTsList) {
    this.videoTsList = videoTsList;
  }

  public String getTempPath() {
    return tempPath;
  }

  public void setTempPath(String tempPath) {
    this.tempPath = tempPath;
  }
}
