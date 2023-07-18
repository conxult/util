/*
 * Copyright (c) 2017 iTAC Software AG, Germany. All Rights Reserved.
 *
 * This software is protected by copyright. Under no circumstances may any part
 * of this file in any form be copied, printed, edited or otherwise distributed,
 * be stored in a retrieval system, or be translated into another language
 * without the written permission of iTAC Software AG.
 */

package de.conxult.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author joerg
 */

public class CheckSum {

  MessageDigest digester;
  String        checkSum;

  public CheckSum(String algorithm) {
     try {
      digester = MessageDigest.getInstance(algorithm);
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      System.err.println("CheckSum("+algorithm+")");
      System.err.println(noSuchAlgorithmException);
    }
  }

  public CheckSum digest(byte... bytes) {
    return digest(bytes, 0, bytes.length);
  }

  public CheckSum digest(byte[] bytes, int len) {
    return digest(bytes, 0, len);
  }

  public CheckSum digest(byte[] bytes, int off, int len) {
    digester.update(bytes, off, len);
    return this;
  }

  public CheckSum digest(Object value) {
    if (value == null) {
      value = "null";
    }
    digester.update(value.toString().getBytes());
    return this;
  }

  public CheckSum digest(InputStream is)
    throws IOException {
    byte[] buffer = new byte[64*1024];
    for (int bytesRead = is.read(buffer); (bytesRead > 0); bytesRead = is.read(buffer)) {
      digest(buffer, 0, bytesRead);
    }
    return this;
  }

  public String getCheckSum() {
    if (checkSum == null) {
      checkSum = toHex(digester.digest());
    }
    return checkSum;
  }

  public static String toHex(byte... buffer) {
    StringBuilder buf = new StringBuilder(buffer.length * 2);
    for (byte b : buffer) {
      buf.append(String.format("%02x", b));
    }
    return buf.toString();
  }

}
