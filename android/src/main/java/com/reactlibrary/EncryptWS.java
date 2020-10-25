package com.reactlibrary;

//import org.apache.log4j.Logger;

public class EncryptWS
{
  private EncryptImp enc = new EncryptImp();
//  private Logger mes_logger = Logger.getLogger(EncryptWS.class);

  public String encrypt(String args0)
  {
    return this.enc.encrypt(args0);
  }

  public String decrypt(String args0)
  {
//    this.mes_logger.info("Decrypt String --- " + args0);
//	  System.out.println("Decrypt String --- " + args0);
    if (args0.endsWith("05")) {
//    	System.out.println("Decrypt String --- end with 05");
      return this.enc.decrypt(args0);
    }
    if (args0.endsWith("04")) {
      return this.enc.decrypt04(args0);
    }
    if (args0.endsWith("03")) {
      return this.enc.decrypt03(args0);
    }
    return this.enc.olddecrypt(args0.getBytes());
  }

  public String getVerifyCode(String args0)
  {
    return this.enc.getVerifyCode(args0);
  }

  public String hello(String name)
  {
    return "Hello " + name;
  }
}
