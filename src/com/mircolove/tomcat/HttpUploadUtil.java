package com.mircolove.tomcat;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

import android.util.Log;

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
	 public static int postWithFile(String url, Map<String, String> params, Map<String, File> files)
	            throws IOException {
	        String BOUNDARY = java.util.UUID.randomUUID().toString();
	        String PREFIX = "--", LINEND = "\r\n";
	        String MULTIPART_FROM_DATA = "multipart/form-data";
	        String CHARSET = "UTF-8";


	        URL uri = new URL(url);
	        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
	        conn.setReadTimeout(10 * 1000); // 缓存的最长时间
	        conn.setDoInput(true);// 允许输入
	        conn.setDoOutput(true);// 允许输出
	        conn.setUseCaches(false); // 不允许使用缓存
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("connection", "keep-alive");
	        conn.setRequestProperty("Charsert", "UTF-8");
	        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);


	        // 首先组拼文本类型的参数
	        StringBuilder sb = new StringBuilder();
	        for (Map.Entry<String, String> entry : params.entrySet()) {
	            sb.append(PREFIX);
	            sb.append(BOUNDARY);
	            sb.append(LINEND);
	            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
	            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
	            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
	            sb.append(LINEND);
	            sb.append(entry.getValue());
	            sb.append(LINEND);
	        }


	        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
	        outStream.write(sb.toString().getBytes());
	        // 发送文件数据
	        if (files != null)
	            for (Map.Entry<String, File> file : files.entrySet()) {
	                StringBuilder sb1 = new StringBuilder();
	                sb1.append(PREFIX);
	                sb1.append(BOUNDARY);
	                sb1.append(LINEND);
	                sb1.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
	                        + file.getValue().getName() + "\"" + LINEND);
	                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
	                sb1.append(LINEND);
	                outStream.write(sb1.toString().getBytes());


	                InputStream is = new FileInputStream(file.getValue());
	                byte[] buffer = new byte[1024];
	                int len = 0;
	                while ((len = is.read(buffer)) != -1) {
	                    outStream.write(buffer, 0, len);
	                }


	                is.close();
	                outStream.write(LINEND.getBytes());
	            }


	        // 请求结束标志
	        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
	        outStream.write(end_data);
	        outStream.flush();
	        // 得到响应码
	        int res = conn.getResponseCode();
	        InputStream in = conn.getInputStream();
	        StringBuilder sb2 = new StringBuilder();
	        if (res == 200) {
	            int ch;
	            while ((ch = in.read()) != -1) {
	                sb2.append((char) ch);
	            }
	        }
	        outStream.close();
	        conn.disconnect();
	        return res;
	    }
}
