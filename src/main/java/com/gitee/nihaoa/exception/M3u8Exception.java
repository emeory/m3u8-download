package com.gitee.nihaoa.exception;

public class M3u8Exception extends RuntimeException{

  public M3u8Exception(String msg) {
    super(msg);
  }

  public M3u8Exception(Exception e){
    super(e);
  }

}
