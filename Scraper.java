package scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Scraper {
	private Spider spider;
	private FileGenerator FG;
	private Queue<String> nextLinks;
	private List<String> usedLinks;
	private String wikiURLFirstPart;
	
	Scraper(String startingPath, String wikiURLFirstPart) throws IOException{
		spider = new Spider();
		FG = new FileGenerator(startingPath);
		nextLinks = new LinkedList<>();
		usedLinks = new ArrayList<String>();
		this.wikiURLFirstPart = wikiURLFirstPart;
	}
	
	public void scrape(String URL, int numOfFiles) throws Exception {
		List<String> L;
		
		FG.writeCollectionOfLinks(URL, L = spider.getLinksFromUrl(wikiURLFirstPart + URL));
		FG.writeCollectionOfWords(URL, spider.getContentFromUrl(wikiURLFirstPart + URL));
		usedLinks.add(wikiURLFirstPart + URL);
		
		for(String l : L) {
			if(!(usedLinks.contains(l) && nextLinks.contains(l))) {
				nextLinks.add(l);
			}
		}
		
		if(FG.isNumFiles(numOfFiles)) {
			System.out.println("done");
			return;
		}else {
			if(nextLinks.isEmpty()) {
				System.out.println("There are no more links!");
				return;
			}
			scrape(nextLinks.poll(), numOfFiles);
		}
	}
	
}
