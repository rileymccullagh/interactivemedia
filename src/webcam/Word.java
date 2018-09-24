package webcam;

import org.jsoup.*;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.*;

public class Word {
	
	String[] frequencyAnalysis(String url, Integer numberOfWords) {
	
	Document doc = null;
	  try {
	    doc = Jsoup.connect(url).get();
	  } 
	  catch (IOException e) {
	    System.out.println(e.getMessage());
	    return null;
	  }
	  
	  String text = doc.text();
	  text = text.replaceAll("[^A-Za-z ]+|\\b\\w{1,4}\\b", "");
	  String[] words = text.split(" +");
	  
	  Map <String, Integer> map = new HashMap <String, Integer> ();
	 
	  for (String str: words) {
	      if (map.containsKey(str)) {
	          map.put(str, map.get(str) + 1);
	      } else {
	          map.put(str, 1);
	      }
	  }
	  
	  Map<String, Integer> sortedMap = sortByValue(map);
	  String[] returnArray = new String[numberOfWords];
	  Integer counter = 0;
	  for(Map.Entry entry: sortedMap.entrySet()) {
	    if(counter>=numberOfWords)
	      break;
	    returnArray[counter] = entry.getKey().toString();
	    counter++;
	  }
	  
	  
	  return returnArray;
	}
	
	private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {
	    List<Map.Entry<String, Integer>> list =
	            new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
	    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	        @Override
			public int compare(Map.Entry<String, Integer> o1,
	                           Map.Entry<String, Integer> o2) {
	            return (o2.getValue()).compareTo(o1.getValue());
	        }
	    });
	    Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
	    for (Map.Entry<String, Integer> entry : list) {
	        sortedMap.put(entry.getKey(), entry.getValue());
	    }
	    return sortedMap;
	}

}

