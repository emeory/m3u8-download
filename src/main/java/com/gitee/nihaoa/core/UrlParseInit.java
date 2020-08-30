package com.gitee.nihaoa.core;

import com.gitee.nihaoa.entry.VideoMeta;
import com.gitee.nihaoa.entry.VideoTs;
import com.gitee.nihaoa.exception.M3u8Exception;
import com.gitee.nihaoa.utils.CommonUtil;
import com.gitee.nihaoa.utils.HttpClientUtil;
import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.List;
import okhttp3.Response;

/**
 * 初始化下载链接
 */
class UrlParseInit {
  private String name;
  private String path;
  private String url;
  private BufferedReader bufferedReader;

  public UrlParseInit(String m3u8Url, String videoPath, String videoName) {
    if (!videoPath.endsWith("/")){
      path = videoPath + '/';
    }else {
      path = videoPath;
    }
    this.url = m3u8Url;
    path = videoPath;
    name = videoName;
  }


  private void checkField() {
    if (!url.contains(".m3u8")) {
      throw new IllegalArgumentException("不是M3U8下载链接");
    }
    if (!url.startsWith("http"))
      url = "http://" + url;
  }

  public void initUrl(){
    checkField();
    Response response = HttpClientUtil.sendGetRequest(url, 200);
    bufferedReader = new BufferedReader(response.body().charStream());
  }

  public List<VideoMeta> parseVideoFile() {
    String masterUrl = url;
    try {
      List<VideoMeta> resultVideo = new LinkedList<>();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        if (line.startsWith("#EXT-X-STREAM-INF")){
          VideoMeta videoMeta = parseStreamVideo(masterUrl, line, bufferedReader.readLine());
          resultVideo.add(videoMeta);
        }
      }
      if (resultVideo.isEmpty()){
        VideoMeta videoMeta = parseVideoMeta(masterUrl, null);
        resultVideo.add(videoMeta);
      }
      return resultVideo;
    }catch (Exception e){
      throw new M3u8Exception(e);
    }
  }

  private VideoMeta parseStreamVideo(String masterUrl, String propertyLine, String streamUrl) {
    String property = propertyLine.split(":", 2)[1];
    if (!streamUrl.startsWith("http")){
      streamUrl = masterUrl.substring(0, masterUrl.lastIndexOf("/") + 1) + streamUrl;
    }
    VideoMeta videoPlayItem = parseVideoMeta(streamUrl, property);
    return videoPlayItem;
  }

  public VideoMeta parseVideoMeta(String videoUrl, String property){
    VideoMeta videoMeta = new VideoMeta(path, name, videoUrl);
    videoMeta.setProperty(property);
    List<VideoTs> videoTsList = new LinkedList<>();
    Response response = HttpClientUtil.sendGetRequest(videoUrl, 200);
    BufferedReader bufferedReader = new BufferedReader(response.body().charStream());
    try {
      String line;
      int fileId = 0;
      while ((line = bufferedReader.readLine()) != null) {
        if (line.startsWith("#EXT-X-VERSION")){
          videoMeta.setVersion(Integer.parseInt(line.split(":")[1]));
        }else if (line.startsWith("#EXT-X-KEY")){
          if (videoMeta.getKeyUrl() == null){
            String keyProperty = line.split(":", 2)[1];
            String[] properties = keyProperty.split(",");
            for (String propertyItem : properties){
              String[] split = propertyItem.split("=", 2);
              String key = split[0];
              String value = split[1];
              if ("URI".equals(key)){
                value = value.replace("\"", "");
                Response keyResponse = HttpClientUtil.sendGetRequest(value, 200);
                videoMeta.setKeyByte(keyResponse.body().bytes());
                videoMeta.setKeyUrl(value);
                continue;
              }else if ("IV".equals(key)){
                if (value.startsWith("0x")){
                  value = value.substring(2);
                }
                videoMeta.setKeyIvByte(CommonUtil.hexStringToByteArray(value));
                continue;
              }
            }
          }
        }else if (line.startsWith("#EXTINF")) {
          String tsUrl = bufferedReader.readLine();
          if (!tsUrl.startsWith("http")){
            tsUrl = videoUrl.substring(0, videoUrl.lastIndexOf("/") + 1) + tsUrl;
          }
          VideoTs videoTs = new VideoTs(videoMeta, ++fileId, tsUrl);
          videoTsList.add(videoTs);
        }
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    videoMeta.setVideoTsList(videoTsList);
    return videoMeta;
  }

}
