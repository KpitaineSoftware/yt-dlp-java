package com.jfposton.ytdlp.utils;

import java.io.IOException;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {

  private InputStreamReader stream;
  private StringBuilder buffer;

  public StreamGobbler(StringBuilder buffer, InputStreamReader stream) {
    this.stream = stream;
    this.buffer = buffer;
    start();
  }

  @Override
  public void run() {
    try {
      int nextChar;
      while ((nextChar = this.stream.read()) != -1) {
        this.buffer.append((char) nextChar);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
