import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * @author Raj Tilak
 * Sep 21, 2015
 *
 */
public class Startup {	
	
	
	public static void main(String args[])
	{
		 String uri = "http://api.viki.io/v4/videos.json?app=100250a&per_page=10&page=";		
		
		 //arrayList to old the id's of objects with hd:true/false
		 ArrayList<String> listOfHDTrue = new ArrayList<String>();
		 ArrayList<String> listOfHDFalse = new ArrayList<String>();		 
		 
		 int counter = 1;
		 int maximum = 1000000;
		 
		 //loop to invoke api until value of field "more" is false
		 while(counter < maximum)
		 {
			 String response = HTTPUtilites.invokeGETRequest(uri + counter);
			 
			 JSONParser parser = new JSONParser();
             
			 try
			 {
				 Object object = parser.parse(response);
					
				 JSONObject jsonObject = (JSONObject) object;
				  
				 boolean hasMore = (boolean) jsonObject.get("more");
				  
				 if(hasMore == false)
				  {
					 counter = counter -1;
					 break;
				  }
				  
				 JSONArray jsonArray = (JSONArray)jsonObject.get("response");
				  
				 for(int i = 0; i < jsonArray.size(); i++)
				  {
					  jsonObject = (JSONObject) jsonArray.get(i);
					  
					  // getting the value of "id" 
					  String id = (String) jsonObject.get("id");
					  
					  jsonObject = (JSONObject) jsonObject.get("flags");  
					  
					  boolean valueOfHD = (boolean) jsonObject.get("hd");
						
					  if(valueOfHD == true)
					  {
						  listOfHDTrue.add(id);
					  }
					  else if (valueOfHD == false)
					  {
						  listOfHDFalse.add(id);
					  }
				  }
				  
				  counter++;
			 }				 
			 catch(ParseException e)
			 {
				 e.printStackTrace();
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
			 }
			 
		
		System.out.println("Total no.of pages with {\"more\":\"true\"}:    " + counter);
		
		System.out.println("Total No. of objects with {\"hd\":\"true\"}:    " + listOfHDTrue.size());
		
		System.out.println("Total No. of objects with {\"hd\":\"false\"}:    " + listOfHDFalse.size()); 	
     }
	
	}
	
}
