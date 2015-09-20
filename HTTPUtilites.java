import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


@SuppressWarnings("deprecation")
public class HTTPUtilites {

	public static String invokeGETRequest(String uri)
	{
		String output = "";
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try
		{
			
			HttpGet getRequest = new HttpGet(uri);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);
			
			
			if (response.getStatusLine().getStatusCode() == 200)
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				
				String content;
				
				while((content = reader.readLine()) != null) 
				{					
				output = output + content;				
				}
			} 
			
		}		
	    catch (ClientProtocolException e) 
		{			
			e.printStackTrace();
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
		
		finally
		{
			httpClient.getConnectionManager().shutdown();
		}
		
		
		return output;
	}
	
}
