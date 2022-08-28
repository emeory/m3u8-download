package com.gitee.nihaoa;

import com.gitee.nihaoa.core.M3u8VideoDownload;
import com.iheartradio.m3u8.Encoding;
import com.iheartradio.m3u8.Format;
import com.iheartradio.m3u8.PlaylistParser;
import com.iheartradio.m3u8.data.MasterPlaylist;
import com.iheartradio.m3u8.data.MediaData;
import com.iheartradio.m3u8.data.MediaPlaylist;
import com.iheartradio.m3u8.data.Playlist;
import com.iheartradio.m3u8.data.PlaylistData;
import com.iheartradio.m3u8.data.TrackData;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class Main {

  public static void main(String[] args) throws Exception{
    String url1 = "https://1258712167.vod2.myqcloud.com/fb8e6c92vodtranscq1258712167/9683cb725285890806354666480/drm/voddrm.token.dWluPTk4MDM4MjE1OTt2b2RfdHlwZT0wO2NpZD0zMjM2MzU7dGVybV9pZD0xMDI5MTc0MTE7cGxza2V5PTAwMDQwMDAwYmMyYmQwZDQ3MTU2ZTQ3MGNkMDIwMjkxZGJiMWY0NGQyMzMwZTEyMzNhNmI5ZmNmYzE3NWUyZDZiMzI5ZGRhMTVjMDY2NDFkNDg4Y2FkMjE7cHNrZXk9.v.f56150.m3u8?t=5f5c88f8&exper=0&sign=62798698c25f6d031f1b0933bba65d69&us=3995060614510076173";
    String url2 = "https://1258712167.vod2.myqcloud.com/fb8e6c92vodtranscq1258712167/222b8f105285890793327213458/drm/master_playlist.m3u8?t=5f5c69f3&exper=0&us=8927891122224205245&sign=02414a22c07dfb7082d6c800c491394f";
    String windowsPath = "D:/download/luban/";
    String linuxPath = "/home/nihaoa/download/test/ts/";
    //LessonConstant lesson = LessonConstant.Lesson13;
    //M3u8VideoDownload videoDownload = new M3u8VideoDownload(10);
    //videoDownload.startDownload(lesson.getUrl(), lesson.getName(), windowsPath);
    InputStream inputStream = new FileInputStream("/home/emeory/Desktop/123.txt");
    PlaylistParser playlistParser = new PlaylistParser(inputStream, Format.EXT_M3U, Encoding.UTF_8);
    System.out.println(playlistParser.isAvailable());
    Playlist playlist = playlistParser.parse();
    System.out.println(playlist.getCompatibilityVersion());
    System.out.println(playlist.hasMasterPlaylist());
    System.out.println(playlist.hasMediaPlaylist());
    MediaPlaylist mediaPlaylist = playlist.getMediaPlaylist();
    System.out.println(mediaPlaylist.getPlaylistType());
    List<TrackData> tracks = mediaPlaylist.getTracks();
    for (TrackData track : tracks) {
    }
  }
}
