package scraping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

public class FileGenerator {
	File root;
	File Links;
	File Words;
	
	FileGenerator(String root) throws IOException{
	this.root = new File(root);	
	
	Links = new File(root + "/Links");
	Words = new File(root + "/Words");
	
	if(!Links.exists()) {
		Links.mkdirs();
	}
	if(!Words.exists()) {
		Words.mkdirs();
	}
	
	}
	
	public boolean linkExsists(String name) {
		File f = new File(Links.getAbsolutePath() + name);
		return f.exists();
	}
	
	public void writeCollectionOfLinks(String adress, List<String> links) {
		String toOut = "";
		
		for(String l : links) {
			toOut += l + "\r";
		}
		File nF = new File(Links.getAbsolutePath() + "/" + adress.substring(6));
		if(!linkExsists(Links.getAbsolutePath() + "/" + adress.substring(6))) {
			try {
				writer(nF, toOut);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void writeCollectionOfWords(String adress, String words) {
		File nF = new File(Words.getAbsolutePath() + "/" + adress.substring(6));
		if(!linkExsists(Words.getAbsolutePath() + "/" + adress.substring(6))) {
			try {
				writer(nF, words);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void writer(File f, String toPrint) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(f.getAbsolutePath()), "utf-8"))) {
	   writer.write(toPrint);
		}
	}
	
	public boolean isNumFiles(int value) {
		return value <= Words.list().length;
	}
	
}
