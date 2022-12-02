package com.jfposton.ytdlp;


import com.jfposton.ytdlp.mapper.VideoInfo;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class YtDlpResponseTest {

  @Test
  public void getFormatsCanHandleLargerVideos() throws YtDlpException {
    VideoInfo videoInfo = YtDlp.getVideoInfo("https://www.youtube.com/watch?v=jPTO3lcPpik");
    assertEquals("jPTO3lcPpik", videoInfo.getId());
    assertNotNull(videoInfo.getFormats());
    assertFalse(videoInfo.getFormats().isEmpty());
  }
}
