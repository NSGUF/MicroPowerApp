package com.example.micropowerapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {
	public static String readInputStream(InputStream is){
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			int len=0;
			byte[] buffer=new byte[1024];
			while((len=is.read(buffer))!=-1){
				baos.write(buffer,0,len);
			}
			is.close();
			baos.close();
			byte[] result=baos.toByteArray();
			String temp=new String(result);
			return temp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ªÒ»° ß∞‹";
		}
	}

}
