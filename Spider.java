package scraping;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Spider {
	public Spider() {
		
	}
	
	public String getContentFromUrl(String url) throws Exception {
		try(final WebClient webClient = new WebClient()) {
			webClient.getOptions().setCssEnabled(false);  
			webClient.getOptions().setJavaScriptEnabled(false); 
			
			final HtmlPage page = webClient.getPage(url);
			String content = page.getBody().asText();
			
			content = content.replace("\r\n" + 
					"From Wikipedia, the free encyclopedia\r\n" + 
					"\r\n" + 
					" Jump to navigation Jump to search\r\n", "");
			try {
			content = content.replace(content.subSequence(content.indexOf("\r\nContents\r\n"), content.indexOf(" External links\r\n")), "");
			}catch(Exception e) {
				
			}
			
			content = content.substring(0, content.indexOf("\r\nNavigation menu\r\n" + 
					"\r\n" + 
					"Personal tools\r\n" + 
					"\r\n"));
			content = content.replace("\r\n", " ");
			return content;
		}
	}
	
	public List<String> getLinksFromUrl(String url) throws Exception {
		try(final WebClient webClient = new WebClient()) {
			webClient.getOptions().setCssEnabled(false);  
			webClient.getOptions().setJavaScriptEnabled(false); 
			
			final HtmlPage page = webClient.getPage(url);
			List<?> divs = page.getByXPath("//a");
			
			List list = new ArrayList<String>();

			int x = 0;
			for(Object d : divs) { //filter
				try {
					if(d.toString().substring(d.toString().indexOf("=\"") + 2, d.toString().indexOf("\" ")).equals(page.getUrl().getPath()))
						if(!list.isEmpty())
						break;
				}catch(Exception e) {
					//System.out.println(d.toString());
				}
				
				if(		d.toString().contains("/wiki/") && !(d.toString().contains(".jpg\"")) && !(d.toString().contains(".gif\"")) 
						&& !(d.toString().contains("#")) && !(d.toString().contains("/Portal:")) && !(d.toString().contains("/Help:")) 
						&& !(d.toString().contains("/Wikipedia:")) && !(d.toString().contains("/Talk:")) 
						&& !(d.toString().contains("/Category:")) && !(d.toString().contains("/Special:"))
						&& !(d.toString().contains("https://")) && !(d.toString().contains("/File:"))
						&& !(d.toString().contains("/Template:")) && !(d.toString().contains("/Template_talk:")) 
						&& !(d.toString().contains("/Template:")) ) 
					try {
						list.add(d.toString().substring(d.toString().indexOf("=\"") + 2, d.toString().indexOf("\" ")));
						
						if(!list.get(list.size()-1).toString().contains("/wiki/")) // extra error handling when links are none consistent
							list.remove(list.size()-1);
					}catch(Exception e) {
					}

			}
			
			
			return list;
		}
	}
	
}
