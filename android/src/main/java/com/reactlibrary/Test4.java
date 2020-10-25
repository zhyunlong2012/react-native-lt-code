package com.reactlibrary;

import java.io.PrintStream;
import java.util.Scanner;

public class Test4
{
  public static void main(String[] args)
  {
    EncryptWS ei = new EncryptWS();

    Scanner in = new Scanner(System.in);
    for (;;)
    {
//      String input = in.next();
      String input  = "LC301K6080100001002902010-17T/A";
      System.out.println("输入: " + input);

      String en = ei.encrypt(input);

      System.out.println("加密后结果: " + en);

      String un = ei.decrypt(en);

      System.out.println("解密后的 : " + un);

//      String oldun04 = ei.decrypt("Y*51$7cnq)equUOadYdlhFHKLxb-E//5s0SBM$KO204");
////      String oldun04 = ei.decrypt("m035yPIuKZN86rQIgdz.m5%$oudSkcwcS705");
//
//      System.out.println("04版解密后的 :  " + oldun04);

//      String oldun03 = ei.decrypt("2$38*5$aML105");
//      String oldun03 = ei.decrypt("SkcwcS705");
//      String oldun03 = ei.decrypt("m035yPIuKZN86rQIgdz.m5%$oudSkcwcS705");
//      System.out.println("05版解密后的: " + oldun03);
//
//      String oldun = ei.decrypt("51ODI0op1FD0ormnmmmmmqpntmnrqoj1D0npml1F0g");
//
//      System.out.println("旧版解密后的:  " + oldun);

      System.out.println("********************************************");
    }
  }
}
