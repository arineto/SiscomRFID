package util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {

	public static void startBrowser(String url) {		
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();		
		try{
			if (os.indexOf( "win" ) >= 0) {
		        // this doesn't support showing urls in the form of "page.html#nameLink" 
		        rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
		    } else if (os.indexOf( "mac" ) >= 0) {
		        rt.exec( "open " + url);
	        } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {
		        // Do a best guess on unix until we get a platform independent way
		        // Build a list of browsers to try, in this order.
		        String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
		       			             "netscape","opera","links","lynx"};
		        // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
		        StringBuffer cmd = new StringBuffer();
		        for (int i=0; i<browsers.length; i++) {
		        	cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");	
		        }		            	        	
		        rt.exec(new String[] { "sh", "-c", cmd.toString() });

	        } else {
	        	System.out.println("nope!");
	        	return;
	        }
		}catch (Exception e){
			System.out.println("nooope!");
			return;
		}
	}
	
	public static void criarTags(int qtdTags) {
		StringBuilder builder = null;
		List<String> l = null;
		BigInteger a = null;
		String tag = null;
		
		for(int i = 0; i < 1000; i++) {
			builder = new StringBuilder();
			l = new ArrayList<String>();
			
			for(int j = 0; j < qtdTags; j++) {
				a = new BigInteger(120, new Random());
				
				if(!l.contains(a)) {
					tag = a.toString(2).substring(0, 96);
					l.add(tag);
					builder.append(tag.concat("\r\n"));
				}
			}
		}
	}

//	public static void main(String[] args) {
//		criarTags(1000);
//	}
}
