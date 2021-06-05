package com.myproject.fileoperations;

import java.text.DecimalFormat;

public class Plural {

	public static void main(String[] args) {
		Plural plural = new Plural();
//		System.out.println("1506.0018, 1395.9583: " + plural.format(1395.9583, 1506.0018));
//		System.out.println("1506.0018, 110.04166: " + plural.format(110.04166, 1506.0018));
		int number =5;
		int f = number;
		for (int i=number-1;i>1;i--) {
			f = f*i;
		}
		System.out.println(f);

	}
	
	public String fromPlural(String key) {
		String configKey;
	    if(key.toLowerCase().endsWith("es") && ! shouldEndWithE(key)) {
	    	configKey = key.substring(0, key.toLowerCase().lastIndexOf("es"));
	    } else if(key.toLowerCase().endsWith("s")) {
	    	configKey = key.substring(0, key.toLowerCase().lastIndexOf('s'));
	    } else if(key.toLowerCase().endsWith("children")) {
	    	configKey = key.substring(0, key.toLowerCase().lastIndexOf("ren"));
	    } else if(key.toLowerCase().endsWith("people")) {
	    	configKey = key.substring(0, key.toLowerCase().lastIndexOf("ople")) + "rson";
	    } else {
	    	configKey = key;
	    }
	    return configKey;
	}
	 
	private boolean shouldEndWithE(String str) {
	    return str.toLowerCase().endsWith("nes") || str.toLowerCase().endsWith("mes")
	   	     || str.toLowerCase().endsWith("iece") || str.toLowerCase().endsWith("ice")
	   	     || str.toLowerCase().endsWith("ace") || str.toLowerCase().endsWith("ise");
	}
	
	private String format(Double sOrD, Double total) {
		DecimalFormat df = new DecimalFormat("0.00");
		double percent = (sOrD / total) * 100;
		System.out.println(percent);
		String formattedPercent = df.format(percent) + "%";
		
		return formattedPercent;
	}

}
