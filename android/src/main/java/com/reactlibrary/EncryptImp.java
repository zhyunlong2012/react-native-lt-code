package com.reactlibrary;

import java.util.Random;
//import org.apache.log4j.Logger;

class EncryptImp
{
  private static String SECRET_KEY = "1234567890-$%*+()_qwertyuiopQWERTYUIOPasdfghjklASDFGHJKLzxcvbnm./ZXCVBNM";
  private static String SECRET_KEY_03 = "1234567890-$%*+qwertyuiopQWERTYUIOPasdfghjklASDFGHJKLzxcvbnm./ZXCVBNM";
  private static String SECRET_KEY_04 = "1234567890-$%*+()qwertyuiopQWERTYUIOPasdfghjklASDFGHJKLzxcvbnm./ZXCVBNM";
  private static String VERSION_NUM = "05";
  private static int cnt = 1;
  private static Random random = new Random();
  private String strs = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private int lastNum = -1;
//  private Logger mes_logger = Logger.getLogger(EncryptImp.class);

  String encrypt(String password)
  {
    if ((password == null) || ("".equals(password.trim()))) {
      return "";
    }
    if (cnt > 9) {
      cnt = 1;
    }
    for (int i = 0; i < cnt; i++) {
      password = translatePasswd(password);
    }
    String encrypt = insertRandomNum(password + cnt++ + VERSION_NUM);

//    this.mes_logger.info("Encrypted String --- " + encrypt);

    return encrypt;
  }

  String decrypt03(String password)
  {
    StringBuilder sb = new StringBuilder(password);

    sb.delete(password.length() - VERSION_NUM.length(), password.length());

    sb.delete(2, 4);

    int count = Integer.parseInt(String.valueOf(sb.charAt(sb.length() - 1)));

    sb.deleteCharAt(sb.length() - 1);
    for (int i = 0; i < count; i++) {
      reversePasswd03(sb);
    }
    return sb.toString();
  }

  private static void reversePasswd03(StringBuilder password)
  {
    if (password.length() == 0) {
      return;
    }
    int i = 0;

    char[] chars = SECRET_KEY_03.toCharArray();

    StringBuilder newpass = new StringBuilder();
    for (char ch : password.toString().toCharArray())
    {
      int j;
      for (j = 0; j < SECRET_KEY_03.length(); j++) {
        if (ch == chars[j]) {
          break;
        }
      }
      int k = j - i - 1;
      newpass.append(chars[k]);

      i++;
    }
    password.setLength(0);
    password.append(newpass);
  }

  String decrypt04(String password)
  {
    StringBuilder sb = new StringBuilder(password);

    sb.delete(password.length() - VERSION_NUM.length(), password.length());

    sb.delete(2, 4);

    int count = Integer.parseInt(String.valueOf(sb.charAt(sb.length() - 1)));

    sb.deleteCharAt(sb.length() - 1);
    for (int i = 0; i < count; i++) {
      reversePasswd04(sb);
    }
    return sb.toString();
  }

  private static void reversePasswd04(StringBuilder password)
  {
    if (password.length() == 0) {
      return;
    }
    int i = 0;

    char[] chars = SECRET_KEY_04.toCharArray();

    StringBuilder newpass = new StringBuilder();
    for (char ch : password.toString().toCharArray())
    {
      int j;
      for (j = 0; j < SECRET_KEY_04.length(); j++) {
        if (ch == chars[j]) {
          break;
        }
      }
      int k = j - i - 1;
      newpass.append(chars[k]);

      i++;
    }
    password.setLength(0);
    password.append(newpass);
  }

  String decrypt(String password)
  {
    StringBuilder sb = new StringBuilder(password);

    sb.delete(password.length() - VERSION_NUM.length(), password.length());

    sb.delete(2, 4);

    int count = Integer.parseInt(String.valueOf(sb.charAt(sb.length() - 1)));

    sb.deleteCharAt(sb.length() - 1);
    for (int i = 0; i < count; i++) {
      reversePasswd(sb);
    }
    return sb.toString();
  }

  private static void reversePasswd(StringBuilder password)
  {
    if (password.length() == 0) {
      return;
    }
    int i = 0;

    char[] chars = SECRET_KEY.toCharArray();

    StringBuilder newpass = new StringBuilder();
    for (char ch : password.toString().toCharArray())
    {
      int j;
      for (j = 0; j < SECRET_KEY.length(); j++) {
        if (ch == chars[j]) {
          break;
        }
      }
      int k = j - i - 1;
      if(k<0) {k = k+ SECRET_KEY.length();}
      newpass.append(chars[k]);

      i++;
    }
    password.setLength(0);
    password.append(newpass);
  }

  String getVerifyCode(String data)
  {
    byte[] tmp = data.getBytes();

    int num = 0;
    for (int i = 0; i < 18; i++)
    {
      byte b = tmp[i];
      num += ((b <= 57) && (b > 48) ? b : 0);
    }
    int avg = num / 18;
    int mod = avg % 26;
    byte res = (byte)(65 + mod);
    return new String(new byte[] { res });
  }

  private static String translatePasswd(String password)
  {
    int i = 0;

    char[] chars = SECRET_KEY.toCharArray();

    StringBuilder newpass = new StringBuilder();
    for (char ch : password.toCharArray())
    {
      int j;
      for (j = 0; j < SECRET_KEY.length(); j++) {
        if (ch == chars[j]) {
          break;
        }
      }
      int k = j + i + 1;
      if(k>=SECRET_KEY.length()) {k = k- SECRET_KEY.length();}
      newpass.append(chars[k]);

      i++;
    }
    return newpass.toString();
  }

  private static String insertRandomNum(String password)
  {
    return new StringBuilder(password).insert(2, String.valueOf(random.nextInt(89) + 10)).toString();
  }

  String olddecrypt(byte[] data)
  {
    data = decrypt2(data);

    StringBuilder out = new StringBuilder();
    int base = Integer.valueOf(new String(new byte[] { data[0] })).intValue();
    for (int i = 0; i < data.length - 3;) {
      out.append(new String(new byte[] { (byte)(Integer.valueOf(new String(new byte[] { data[(2 + i++ - 1)] })).intValue() * 100 + Integer.valueOf(new String(new byte[] { data[(++i)] })).intValue() * 10 + Integer.valueOf(new String(new byte[] { data[(i++ + 1)] })).intValue() + base * 2) }));
    }
    this.lastNum = -1;
    return out.toString();
  }

  private byte[] decrypt2(byte[] data)
  {
    StringBuilder out = new StringBuilder();
    out.append(new String(new byte[] { data[0] }));
    for (int i = 1; i < data.length; i++)
    {
      byte b = data[i];
      if ((b >= 48) && (b <= 57))
      {
        this.lastNum = (b - 48);
      }
      else
      {
        int val = this.lastNum * this.strs.length() + this.strs.indexOf(new String(new byte[] { b }));
        out.append(val < 100 ? "0" : "");
        out.append(val);
      }
    }
    return out.toString().getBytes();
  }
}
