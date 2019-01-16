package scraping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("input directory for data generation output: ");
		String dir = br.readLine();
		
		System.out.print("Input the web adress: ");
		String path = br.readLine();
		
		System.out.print("Input the starting sub page: ");
		String pathPart = br.readLine();
		
		int i = 0;
		System.out.print("Input number of files to generate: ");
		try{
            i = Integer.parseInt(br.readLine());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }
		
		try {
			if(i > 0) {
				Scraper SC = new Scraper(dir,path);
				SC.scrape(pathPart, i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
