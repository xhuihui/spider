package com.liqh.jsoup;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PhoneGet {
	static CloseableHttpClient httpclient = HttpClients.createDefault();
	static Logger log=Logger.getLogger("phone");
	static Logger log2=Logger.getLogger("phone2");
	public static void main(String[] args) throws ClientProtocolException, IOException {
		getPhone("D:/github/spider/data/2017-12-22.txt");
//		for(int i=1;i<2;i++){
//			log.info("page:"+i);
//			getPage("http://www.ljia.net/jingjiren/p-"+i+".html");
//		}
	}
	public static  void getPage(String url) throws ClientProtocolException, IOException{
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		try {
		    System.out.println(response1.getStatusLine());
		    HttpEntity entity1 = response1.getEntity();
		    String html=EntityUtils.toString(entity1,"GB2312");
		    Document doc=Jsoup.parse(html);
		    for(Element ele: doc.select("ul.boxText")){
		    	Element name=ele.select("h3").first();
		    	Element gongsi=ele.select("li:eq(1)").first();
		    	Element addr=ele.select("li:eq(2)").first();
		    	Element phone=ele.select("li:eq(3)").first();
		    	Element time=ele.select("li:eq(5)").first();
		    	log.info(phone.ownText()+"|"+time.ownText().replace("最后登录：", "")+"|"+name.text()+"|"+gongsi.text()+"|"+addr.text());
		    }
		    EntityUtils.consume(entity1);
		} finally {
		    response1.close();
		}
	}
	public static void getPhone(String file) throws IOException{
		List<String> lines=FileUtils.readLines(new File(file), "UTF-8");
		Set<String> phones=new HashSet<String>();
		for(String s:lines){
			try {
				s=s.split("\\|")[0];
				if(s.length()==11&&s.startsWith("1"))phones.add(s);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		for(String s:phones){
			log2.info(s);
		}
	}
}
