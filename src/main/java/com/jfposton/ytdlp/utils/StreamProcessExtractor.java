package com.jfposton.ytdlp.utils;


import com.jfposton.ytdlp.DownloadProgressCallback;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamProcessExtractor extends Thread {
  private static final String GROUP_PROGRESS = "progress";
  private InputStreamReader stream;
  private StringBuilder buffer;
  private final DownloadProgressCallback callback;

  private Pattern p =
      Pattern.compile(
          "(?<progress>\\[download\\]\\s+\\d+\\.\\d% .* ETA \\d+:\\d+)");

  public StreamProcessExtractor(
          StringBuilder buffer, InputStreamReader stream, DownloadProgressCallback callback) {
    this.stream = stream;
    this.buffer = buffer;
    this.callback = callback;
    this.start();
  }

  @Override
  public void run() {
    try {
      StringBuilder currentLine = new StringBuilder();
      int nextChar;
      while ((nextChar = stream.read()) != -1) {
        buffer.append((char) nextChar);
        if (nextChar == '\r' && callback != null) {
          processOutputLine(currentLine.toString());
          currentLine.setLength(0);
          continue;
        }
        currentLine.append((char) nextChar);
      }
    } catch (IOException ignored) {
      ignored.printStackTrace();
    }
  }

  private void processOutputLine(String line) {
    Matcher m = p.matcher(line);
    if (m.matches()) {
      String progress = m.group(GROUP_PROGRESS);
      callback.onProgressUpdate(progress);
    }
  }

  private int convertToSeconds(String minutes, String seconds) {
    return Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);
  }
}
