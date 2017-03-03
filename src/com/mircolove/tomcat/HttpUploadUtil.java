package com.mircolove.tomcat;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

//ͨ通过Http协议发送带文件或不带文件的请求的工具类
public class HttpUploadUtil 
{
	//不带文件的请求发送方法
	//actionUrl表示请求的URL，params表示请求的参数序列
	public static String postWithoutFile(String actionUrl,Map<String, String> params){		
		//String result="";
		//1创建HttpClient对象
		HttpClient httpclient = new DefaultHttpClient();
		//2根据指定的URL，创建HTTPPOST对象
		HttpPost httppost = new HttpPost(actionUrl);
		
		//将要传递的参数保存到List集合中		
		try { 
		   List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.size()); 
		   //循环读取参数集合，并逐个添加nameValuePairs中
		   for (Map.Entry<String, String> entry : params.entrySet()) {
			   //构建表单字段内容  
	            //nameValuePairs.add(new BasicNameValuePair(entry.getKey(),MyConverter.escape(entry.getValue()))); 
	            nameValuePairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue())); 	            
	       }  
		   
		   //设置编码方式
		   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8")); 
		   //定义HttpResponse对象
		   HttpResponse response; 
		   //执行HttpClient请求
		   response=httpclient.execute(httppost); 
		   //获取结果		 
		   
		  InputStream in=response.getEntity().getContent();
		
		   //创建字节数组输出流
		   ByteArrayOutputStream baos = new ByteArrayOutputStream();
		   //将从输入流读取到输出流中
		   int ch=0;
		    while((ch=in.read())!=-1){
		      	baos.write(ch);
		    }   
		    //将输出流的数据全部保存到字节数组data中
		    byte[] data=baos.toByteArray();
		    baos.close();
		    //返回以字符串输出的数据
		    return MyConverter.unescape(new String(data).trim());
		  
		  } catch (Exception e) { 
		   e.printStackTrace(); 
		   return "error";
		  }
	}
}
