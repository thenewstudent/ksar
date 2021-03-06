/*
 * Copyright 2008 The kSAR Project. All rights reserved.
 * See the LICENSE file in the project root for more information.
 */

package net.atomique.ksar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VersionNumber {

  private static final Logger log = LoggerFactory.getLogger(VersionNumber.class);

  private static VersionNumber instance = new VersionNumber();

  public static VersionNumber getInstance() {
    return instance;
  }

  VersionNumber() {
    StringBuilder tmpstr = new StringBuilder();
    BufferedReader reader = null;
    try {
      InputStream is = this.getClass().getResourceAsStream("/kSar.version");
      if (is == null) {
        tmpstr.append("0.0.0");
      } else {
        InputStreamReader isr = new InputStreamReader(is);
        reader = new BufferedReader(isr);
        String line = "";
        while ((line = reader.readLine()) != null) {
          tmpstr.append(line);
        }
        reader.close();
      }
    } catch (IOException ex) {
      log.error("Unable to read Current version", ex);
      return;
    }
    setVersionNumber(tmpstr.toString());
    return;
  }

  public void setVersionNumber(String version) {
    String tmp[] = version.split("\\.");
    if (tmp.length != 3) {
      return;
    }
    major = new Integer(tmp[0]);
    minor = new Integer(tmp[1]);
    micro = new Integer(tmp[2]);
    return;
  }

  public static String getVersionNumber() {
    return major + "." + minor + "." + micro;
  }

  public static Integer getVersionNumberint() {
    return (major * 100) + (minor * 10) + micro;
  }

  public static boolean isOlderThan(String version) {
    Integer mymajor;
    Integer myminor;
    Integer mymicro;

    String[] tmp = version.split("\\.");
    if (tmp.length != 3) {
      return false;
    }

    mymajor = new Integer(tmp[0]);
    myminor = new Integer(tmp[1]);
    mymicro = new Integer(tmp[2]);


    if (major.intValue() < mymajor.intValue()) {
      return true;
    }

    if (minor.intValue() < myminor.intValue()) {
      return true;
    }

    if (micro.intValue() < mymicro.intValue()) {
      return true;
    }

    return false;
  }


  private static Integer major;
  private static Integer minor;
  private static Integer micro;

}
