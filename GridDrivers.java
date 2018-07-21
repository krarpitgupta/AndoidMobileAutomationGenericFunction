package GenericFunctionsLibrary;
import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;



import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static java.lang.System.out;



public class GridDrivers  {



	/* Function :- This will write any file.For Grid we are using this to write json and bat commands for starting grid hub and nodes
	 * @author: AAutomators, date: 08-April-2016
	 */
	public void writeFile(String path,String content)
	{
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter( new FileWriter(path));
			writer.write(content);
		}
		catch ( IOException e)
		{

		}
		finally
		{
			try
			{
				if ( writer != null)
					writer.close( );
			}
			catch ( IOException e)
			{
			}
		}

	}
	
	
	/* Function :- This will start grid hub and node. Pass the command to start grid and node  
	 * @author: Automators, date: 08-April-2016
	 */
	public void StartGridHubAndNode(String command,int i) throws IOException, InterruptedException
	{
		String directory;
		directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "/");
		writeFile(directory+"/"+"node"+i+".bat", command);

		try {
			String cmmd = "cmd /c start "+directory+"/"+"node"+i+".bat";
			Process p =  Runtime.getRuntime().exec(cmmd) ;   
			i++;
		} catch (IOException ex) {
		}

	}

	/* Function :- This will stop port.  
	 * @author: Automators, date: 08-April-2016
	 */

	public void stopPort(String port) throws IOException
	{

		Runtime.getRuntime().exec("cmd.exe");
		String command = "cmd /c echo off & FOR /F \"tokens=5 delims= \" %a IN ('netstat -a -n -o ^| findstr :"+port+"') do taskkill /F /PID  %a";

		String s = null;
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			System.out.println(" ---------->>> Exception happened:While stopping port ");
			e.printStackTrace();
		}

		System.out.println("------------>>> Port : "+port+" has been stopped");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Function :- This will Get the ip address of system.Require to generate jsons  
	 * author: Automators, date: 08-April-2016
	 */
	public String GetIpAddress()
	{
		String ipname="";
		Enumeration<NetworkInterface> nets = null;
		try {
			nets = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block

			e1.printStackTrace();
		}
		for (NetworkInterface netint : Collections.list(nets))
		{
			try {
				ipname=displayInterfaceInformation(netint);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!ipname.equals(""))
			{

				break;
			}
		}
		ipname = ipname.replace("/","");
		ipname=ipname.trim();   
		System.out.println(ipname);
		return ipname;
	}

	/* Function :- Complete process to start grid and nodes. Description mentioned in code  
	 * author: Automators, date: 08-April-2016
	 */
	public void StartGrid() throws IOException, InterruptedException {

		final GridDrivers grid = new GridDrivers(); 
		List<String> devices = new ArrayList<String>();
		GenericFunctions generic = new GenericFunctions();
		HashMap<String, String> data = new HashMap<String,String>();
		String directory;
		String commonResources = System.getenv("Common_Resources");
		String appiumDirectory="C:/Appium";
		commonResources=commonResources.replace("\\", "/");
		grid.stopPort("4444"); // stop grid hub if already working
		String ip="127.0.0.1";//grid.GetIpAddress();  // Get Ip address
		devices=generic.getConnectedDevicesList();
		int numberOfDevices=generic.getconnectedDevicesNumber();
		String device[] = new String[numberOfDevices];

		directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "/");
		String command[]= new String[numberOfDevices+1]; // In this array grid command and node command will get save
		Thread[] threads = new Thread[numberOfDevices+1]; // thread to start grid and nodes

		if(numberOfDevices==0)
		{
			return;
		}
		for(int i=0;i<numberOfDevices;i++)
		{
			device[i]=devices.get(i).toString();

			int port;
			if(i==0)
			{
				port=4723;
			}
			else
			{
				port=4723+i*10;  /// making different port numbers for different devices
			}

			// Note If You are changing hub port than you have to change it in start driver of android in generic function class
			grid.makeJson(4444, ip, port, device[i].toString().trim(),device[i]+".json");
			data.put("port"+i, port+"");
			data.put("deviceId"+i,device[i]);
			data.put("jsonFile"+i, device[i]+".json");
			File dir = new File(device[i]+"_Grid");
			dir.mkdir();
			data.put("Gridfolder"+i,device[i]+"_Grid");


			if(i==0)
			{
				grid.stopPort("4723");
			}
			else
			{ 
				int portnum = 4723+i*10;
				String portToStop = portnum+"";
				grid.stopPort(portToStop);
			}
		}

		// This for loop is till i equals numberofDevices and above for loop is for i equals numberofdevices-1 		
		for(int i=0;i<=numberOfDevices;i++)
		{
			if(i==0)
			{
				command[i]="java -jar "+commonResources+"/jars/selenium-server-standalone-2.47.1.jar -host 127.0.0.1 -port 4444 -role hub";
			}
			else
			{
				int j=i-1;
				command[i]=appiumDirectory+"/node.exe "+appiumDirectory+"/node_modules/appium/bin/appium.js --nodeconfig "+directory+"/"+data.get("jsonFile"+j)+" -p "+data.get("port"+j).trim()+" -U "+data.get("deviceId"+j)+" --tmp "+directory+"/"+data.get("Gridfolder"+j);

			}
			final String commands =command[i].toString();
			final int j =i;

			threads[i] = new Thread(){
				public void run()
				{

					try {
						grid.StartGridHubAndNode(commands,j);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			threads[i].start();

		}


	}


	/* Function :- This will make json of devices.   
	 * author: Automators, date: 08-April-2016
	 */

	public void makeJson(int hubPort,String ip,int nodePort,String deviceId,String FileName) throws FileNotFoundException, IOException
	{
		APIFunctions api = new APIFunctions();
		JsonObject json = new JsonObject();
		JsonObject json2 = new JsonObject(); 	   
		JsonArray json3=new JsonArray();
		JsonObject json5 = new JsonObject();
		String url ="http://"+ip+":"+nodePort+"/wd/hub";
		String hub ="http://"+ip+":"+hubPort+"/grid/register/";

		json.addProperty("cleanUpCycle",2000);
		json.addProperty("timeout",30000);
		json.addProperty("proxy", "org.openqa.grid.selenium.proxy.DefaultRemoteProxy");

		json.addProperty("url",url);
		json.addProperty("hub",hub);
		json.addProperty("host",ip);
		json.addProperty("port",nodePort);
		json.addProperty("maxSession",5);
		json.addProperty("register",true);
		json.addProperty("registerCycle",5000);
		json.addProperty("hubPort",hubPort);
		json.addProperty("hubHost",ip);

		JsonObject json1=  api.MakeJsonObjectOfKeyAndJsonObject("configuration", json);
		json2.addProperty("browserName",deviceId);
		String version;
		json2.addProperty("version","4.3");
		/*if(deviceId.equals("4df707995bd4215b"))
		{
			json2.put("version","4.3");
		}
		else
		{
			json2.put("version","5.0.2");
		}*/
		json2.addProperty("maxInstances",5);
		json2.addProperty("platform","Android");
		json3.add(json2);
		JsonObject json4 = api.MakeJsonObjectOfKeyAndJsonArray("capabilities", json3);
		String finalJson=api.mergeJsonObjects(json4, json1);
		//System.out.println(json5);
		String directory;
		directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "/");
		writeFile(directory+"/"+FileName, finalJson);

	}


	/* Function :- Used in getipaddress function for getting ip  
	 * author: Automators, date: 08-April-2016
	 */
	public String displayInterfaceInformation(NetworkInterface netint) throws SocketException {

		Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();

		//Realtek PCIe GBE Family Controller
		if(netint.getDisplayName().contains("WiFi"))
		{
			for (InetAddress inetAddress : Collections.list(inetAddresses)) {
				return inetAddress+""; 
				//   out.printf("InetAddress: %s\n", inetAddress);
			}
			return "";
		}
		else if(netint.getDisplayName().contains("Realtek PCIe GBE Family Controller"))
		{
			for (InetAddress inetAddress : Collections.list(inetAddresses)) {
				return inetAddress+""; 
				//   out.printf("InetAddress: %s\n", inetAddress);
			}
			return "";
		}
		else
		{
			return "";
		}


	}


}
