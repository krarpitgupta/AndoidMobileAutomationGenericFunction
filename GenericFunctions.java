package GenericFunctionsLibrary;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class GenericFunctions {
	@SuppressWarnings("rawtypes")
	public AndroidDriver driver;
	public String browserType="";
	public String winHandle;

	public GenericFunctions()
	{

	}

	@SuppressWarnings("rawtypes")
	public GenericFunctions(AndroidDriver driver){
		this.driver=driver;
	}

	// To start Grid thorugh build xml
	public static void main(String args[])
    {
           GenericFunctions generic = new GenericFunctions();
           int numberOfDevices=generic.getconnectedDevicesNumber();
           System.out.println("In main method");
           if(numberOfDevices>1)
           {
                  GridDrivers grid = new GridDrivers();
                  try {
                        grid.StartGrid();
                        generic.GoToSleep(10000);
                  } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                  }
           }
    }

	/***********************************************************************************************
	 * Function Description : Method to get value set in config.properties file
	 * *********************************************************************************************/


	public String getPropertyValue(String propertyName) {

		String directory = System.getProperty("user.dir");
		String propFileName = directory+"/config.properties";
		File file = new File(propFileName);
		FileInputStream fileInput = null;
		Properties prop = new Properties();
		String propertyValue = "";

		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		propertyValue = prop.getProperty(propertyName);

		return propertyValue;
	}


	/***********************************************************************************************
	 * Function Description : Sets implicit Wait by accepting timeout in seconds
	 * author: Automators, date: 25-Feb-2013
	 * *********************************************************************************************/

	public String SetImplicitWaitInSeconds(int timeOut){
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		return "Timeout set to "+timeOut+" seconds.";
	}

	public void GoToSleep(int TimeInMillis)
	{
		try{Thread.sleep(TimeInMillis);} catch(Exception e){}
	}

	/***********************************************************************************************
	 * Function Description : Wait till invisibility of an element
	 * author: Automators, date: 09-April-2015
	 * *********************************************************************************************/
	public void WaitUntilInvisibilityOfElement(By element)
	{
		try{

			WebDriverWait wait = new WebDriverWait(this.driver, 15);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
		}
		catch(Exception e){}
	}


	/***********************************************************************************************
	 * Function Description : Wait till visibility of an element
	 * author: Automators, date: 09-April-2015
	 * *********************************************************************************************/
	public void WaitUntilVisibilityOfElement(By element)
	{
		try{
			WebDriverWait wait = new WebDriverWait(this.driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		}
		catch(Exception e){}
	}



	/***********************************************************************************************
	 * Function Description : Sets implicit Wait by accepting timeout in milliseconds
	 * author: Automators, date: 25-Feb-2013
	 * *********************************************************************************************/

	public String SetImplicitWaitInMilliSeconds(int timeOut){
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.MILLISECONDS);
		return "Timeout set to "+timeOut+" milli seconds.";
	}


	/***********************************************************************************************
	 * Function Description : Initiates the appium server via commandline
	 * @author Automators, date: 19-May-2014
	 * @throws IOException, InterruptedException
	 * *********************************************************************************************/

	public void startAppiumServer() throws IOException, InterruptedException {   

		String port = "4723"; getPropertyValue("Port");

		if(this.getconnectedDevicesNumber() > 0) {

			Runtime.getRuntime().exec("cmd.exe");
			CommandLine command = new CommandLine("cmd"); 
			command.addArgument("/c");  
			command.addArgument("C:/nodejs/node.exe");  
			command.addArgument("C:/Appium/node_modules/appium/bin/appium.js");
			command.addArgument("--address", false);  
			command.addArgument("127.0.0.1");  
			command.addArgument("--port", false);  
			command.addArgument(port);
			command.addArgument("--full-reset", false);
			command.addArgument("--command-timeout", false);
			command.addArgument("60");
			command.addArgument("--full-reset", false);  

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();  
			DefaultExecutor executor = new DefaultExecutor();  
			executor.setExitValue(1);  
			executor.execute(command, resultHandler);  

			Thread.sleep(5000);  
			System.out.println("------------>>> Appium server started");  
		}
		else 
			if(this.getconnectedDevicesNumber() <= 0){

				Assert.assertTrue(false, "Device Not Found");
				System.out.println("----------->>> Device Not Found");
			}

	}


	public void startAppiumServerWear(String portNumber) throws IOException, InterruptedException {   

		if(this.getconnectedDevicesNumber() > 0) {

			Runtime.getRuntime().exec("cmd.exe");
			CommandLine command = new CommandLine("cmd"); 
			command.addArgument("/c");  
			command.addArgument("C:/nodejs/node.exe");  
			command.addArgument("C:/Appium/node_modules/appium/bin/appium.js");  
			command.addArgument("--address", false);  
			command.addArgument("127.0.0.1");  
			command.addArgument("--port", false);  
			command.addArgument(portNumber);  
			command.addArgument("--command-timeout", false);
			command.addArgument("120");
			command.addArgument("--full-reset", false);  

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();  
			DefaultExecutor executor = new DefaultExecutor();  
			executor.setExitValue(1);  
			executor.execute(command, resultHandler);  

			Thread.sleep(5000);  
			System.out.println("------------>>> Appium server started");  
		}
		else 
			if(this.getconnectedDevicesNumber() <= 0){

				Assert.assertTrue(false, "Device Not Found");
				System.out.println("----------->>> Device Not Found");
			}
	}

	/***********************************************************************************************
	 * Function Description : Initiates the app with all the settings and main package and Activity are declared here
	 * @author Automators, date: 29-Oct-2014
	 * @throws MalformedURLException
	 * *********************************************************************************************/


	public AndroidDriver StartDriverAndroidApp(String appLocation, String appPackage, String appActivity) throws MalformedURLException{

		String Platform;
		String AppiumHost;
		GridDrivers grid = new GridDrivers();
		String ip = "127.0.0.1";//grid.GetIpAddress();
		int numberOfDevices = getconnectedDevicesNumber();
		if(SystemUtils.IS_OS_WINDOWS)
		{
			if(numberOfDevices==1)
			{
				/*try {
					stopAppiumServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
				GoToSleep(5000);*/
				Platform = "Android";
				AppiumHost= "http://"+ip+":4723/wd/hub";
			/*	try {
					startAppiumServer();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}*/
			}
			else
			{
				System.out.println("In grid");
				
				Platform = "Android";
				AppiumHost= "http://"+ip+":4444/wd/hub";
			}
		}
		else
		{
			Platform = "Linux";
			AppiumHost= "http://0.0.0.0:4723/wd/hub";

			RuntimeExec appiumObj = new RuntimeExec();
			appiumObj.stopAppiumUbuntu("killall -9 node");
			GoToSleep(2000);
			appiumObj.startAppiumUbuntu("/usr/bin/node /usr/bin/appium --address 0.0.0.0 --port 4723 --no-reset --command-timeout 60");
		}

		try{Thread.sleep(5000);}catch(Exception e){}
		DesiredCapabilities capabilities = new DesiredCapabilities();	

		//capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "TA93306FYV");
		System.out.println("==set browser==");
		capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform);
		//capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.3");
		//capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		System.out.println("==set device==");

		capabilities.setCapability(MobileCapabilityType.APP, appLocation);
		capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, appPackage);
		capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, appActivity);
//		capabilities.setCapability("unicodeKeyboard", true) ;
//		 capabilities.setCapability("resetKeyboard", true);
		System.out.println("==set app==");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 1000);
		try{
			driver = new AndroidDriver(new URL(AppiumHost), capabilities);
		}
		catch(Exception e)
		{
			GoToSleep(10000);
			try{
				driver = new AndroidDriver(new URL(AppiumHost), capabilities);
			}
			catch(Exception e1)
			{
				GoToSleep(10000);
				try{
					driver = new AndroidDriver(new URL(AppiumHost), capabilities);
				}
				catch(Exception e2)
				{
					GoToSleep(10000);
					driver = new AndroidDriver(new URL(AppiumHost), capabilities);
				}
			}
		}


		
		driver.manage().timeouts().implicitlyWait(120000, TimeUnit.MILLISECONDS);
		System.out.println("==========complete launchApp========");
		SetImplicitWaitInSeconds(15);

		return driver;
	}	

	public void startAppium(String comand) {
		Runtime rt = Runtime.getRuntime();
		RuntimeExec rte = new RuntimeExec();
		GenericFunctionsLibrary.GenericFunctions.RuntimeExec.StreamWrapper error, output;

		try {
			Process proc = rt.exec(comand);
			error = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
			output = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
			//   int exitVal = 0;

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String s;
			while((s = stdInput.readLine()) != null){
				System.out.println(s);
				if(s.contains("Appium REST http")){
					break;
				}
			}
			error.start();
			output.start();
			error.join(3000);
			output.join(3000);
			// exitVal = proc.waitFor();
			System.out.println("Output: "+output.message+"\nError: "+error.message);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/***********************************************************************************************
	 * Function Description : Created this class to support Ubuntu :- StartAppium and Stop Appium through Command Line
	 * @author Automators, date: 23-March-2016
	 * command to start Appium is : /usr/bin/node /usr/bin/appium --address 0.0.0.0 --port 4723 --no-reset --command-timeout 60
	 * command to stop appium is : killall -9 node
	 * *********************************************************************************************/

	private class RuntimeExec {
		public StreamWrapper getStreamWrapper(InputStream is, String type){
			return new StreamWrapper(is, type);
		}

		private class StreamWrapper extends Thread {
			InputStream is = null;
			@SuppressWarnings("unused")
			String type = null;
			String message = null;

			StreamWrapper(InputStream is, String type) {
				this.is = is;
				this.type = type;
			}

			public void run() {
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					StringBuffer buffer = new StringBuffer();
					String line = null;
					while ( (line = br.readLine()) != null) {
						buffer.append(line);//.append("\n");
					}
					message = buffer.toString();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}  
		}

		public void startAppiumUbuntu(String comand) {
			Runtime rt = Runtime.getRuntime();
			RuntimeExec rte = new RuntimeExec();
			StreamWrapper error, output;

			try {
				Process proc = rt.exec(comand);
				GoToSleep(5000);
				error = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
				output = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
				//   int exitVal = 0;

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				String s;
				while((s = stdInput.readLine()) != null){
					System.out.println(s);
					if(s.contains("Appium REST http")){
						break;
					}
				}
				error.start();
				output.start();
				error.join(3000);
				output.join(3000);
				// exitVal = proc.waitFor();
				System.out.println("Output: "+output.message+"\nError: "+error.message);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


		public void stopAppiumUbuntu(String comand) {
			Runtime rt = Runtime.getRuntime();
			RuntimeExec rte = new RuntimeExec();
			StreamWrapper error, output;

			try {
				Process proc = rt.exec(comand);
				GoToSleep(5000);
				error = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
				output = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
				error.start();
				output.start();
				error.join(3000);
				output.join(3000);
				if(error.message.equals("") && output.message.equals("")) {
				//closed appium server	
				}
				else 
					if(error.message.contains("No matching processes belonging to you were found")){
					//Display nothing as no instances of Appium Server were found running
				}
				else{
					System.out.println("Output: "+output.message+"\nError: "+error.message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/***********************************************************************************************
	 * Function Description : Kill the appium server via commandline
	 * @author Automators, date: 19-May-2014
	 * @throws IOException, InterruptedException
	 * *********************************************************************************************/

	public  void stopAppiumServer() throws IOException {  

		Runtime.getRuntime().exec("cmd.exe");
		String AppiumServerPortNumber = "4723";//getPropertyValue("port");
		String command = "cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in"
				+ " (`netstat -nao ^| findstr /R /C:\"" + AppiumServerPortNumber + "\"`) do (FOR /F \"usebackq\" %b in"
				+ " (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)";

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
			System.out.println(" ---------->>> Exception happened: ");
			e.printStackTrace();
		}

		System.out.println("------------>>> Appium server stopped");
	}

	public  void stopAppiumServerWear(String portNumber) throws IOException {  

		Runtime.getRuntime().exec("cmd.exe");
		String AppiumServerPortNumber = portNumber;
		String command = "cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in"
				+ " (`netstat -nao ^| findstr /R /C:\"" + AppiumServerPortNumber + "\"`) do (FOR /F \"usebackq\" %b in"
				+ " (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)";

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
			System.out.println(" ---------->>> Exception happened: ");
			e.printStackTrace();
		}

		System.out.println("------------>>> Appium server stopped");
	}

	/***********************************************************************************************
	 * Function Description : Stops the driver
	 * @author Automators, date: 25-May-2015
	 * *********************************************************************************************/

	public String StopDriver(String appPackage){

		driver.removeApp(appPackage);
		int numberOfDevices=getconnectedDevicesNumber();
		if(SystemUtils.IS_OS_WINDOWS) {
			if(numberOfDevices==1)
			{
				try {
					stopAppiumServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			RuntimeExec appiumObj = new RuntimeExec();
			appiumObj.stopAppiumUbuntu("killall -9 node");
			/*	File file = new File("/home/jenkins-android/Common_Resources/stopAppium.sh");
					String appPath = file.getAbsolutePath();
			//in mac oxs
					String command = "sh " + appPath;
			//in windows
			String command = "ping -n 3 " + domainName;
			String output = executeCommand(command);*/
		}
		return("------------>>> Browser closed");
	}

	public String StopDriverWear(String appPackage, String portNumber){

		driver.removeApp(appPackage);

		try {
			stopAppiumServerWear(portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return("------------>>> Browser closed");
	}



	/***********************************************************************************************
	 * Function Description : Takes ScreenShot and returns the screenshot name
	 * author: Automators, date: 25-Feb-2013
	 * *********************************************************************************************/
	public String TakeScreenshot(){
		String directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "\\\\");

		String SaveName = Calendar.getInstance().getTime().toString().replace(":", "").replace(" ", "").trim();
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {

			FileUtils.copyFile(scrFile, new File(directory+"\\screenshots\\"+SaveName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String directoryT = System.getProperty("user.dir");
		String SaveNameT = Calendar.getInstance().getTime().toString().replace(":", "").replace(" ", "").trim();
		String filename = directoryT+"\\screenshots\\"+SaveNameT+".png";

		return filename;
	}


	/***********************************************************************************************
	 * Function Description : Accepts the alert box message
	 * author: Automators, date: 25-Feb-2013
	 * *********************************************************************************************/
	public String AlertBox_Accept(){
		// Get a handle to the open alert, prompt or confirmation
		Alert alert = driver.switchTo().alert();
		// And acknowledge the alert (equivalent to clicking "OK")
		alert.accept();
		return("accepted");
	}

	/***********************************************************************************************
	 * Function Description : Dismisses the alert box message
	 * author: Automators, date: 25-Feb-2013
	 * *********************************************************************************************/
	public String AlertBox_Dismiss(){
		// Get a handle to the open alert, prompt or confirmation
		Alert alert = driver.switchTo().alert();
		// And acknowledge the alert (equivalent to clicking "cancel")
		alert.dismiss();
		return("Alert '"+alert.getText()+"' dismissed");
	}


	/***********************************************************************************************
	 * Function Description : gets the handle for the current window
	 * author: Automators, date: 25-Feb-2013
	 * *********************************************************************************************/
	public void GetWindowHandle(){
		winHandle = driver.getWindowHandle();
	}

	/***********************************************************************************************
	 * Function Description : Switches to the most recent window opened
	 * author: Automators, date: 25-Feb-2013
	 * *********************************************************************************************/
	public void SwitchtoNewWindow(){
		for(String windowsHandle : driver.getWindowHandles()){
			driver.switchTo().window(windowsHandle);
		}
	}







	/***********************************************************************************************
	 * Function Description : Closes the window
	 * author: Automators, date: 25-Feb-2013
	 * *********************************************************************************************/
	public void CloseNewWindow(){
		driver.close();
	}

	/***********************************************************************************************
	 * Function Description : Switches back to original window
	 * author: Automators, date: 25-Feb-2013
	 * *********************************************************************************************/
	public void SwitchtoOriginalWindow(){
		driver.switchTo().window(winHandle);
	}





	/***********************************************************************************************
	 * Function Description : 
	 * author: Automators, date: 5-Apr-2013
	 * *********************************************************************************************/

	public String CompareTwoGivenCommaSeperatedList(String ListA , String ListB)
	{
		String result="";
		String ListAresult="";
		String ListBresult="";
		if(!ListA.contains(",") && !ListB.contains(","))
		{
			if(ListA.trim().equals(ListB.trim()))
			{
				return "Pass";
			}
			else 
			{
				return "Fail";
			}
		}
		String[] ListArray1=ListA.split(",");
		String[] ListArray2=ListB.split(",");
		String tokenA;
		String tokenB;


		for(int i=0;i<ListArray1.length;i++)
		{
			tokenA=ListArray1[i];

			for(int j=0;j<ListArray2.length;j++)
			{
				if(tokenA.trim().equals(ListArray2[j].trim()))
				{
					break;
				}
				else if(j==ListArray2.length-1)
				{
					ListAresult=ListAresult+"::"+tokenA;
				}
			}

		}

		for(int i=0;i<ListArray2.length;i++)
		{
			tokenB=ListArray2[i];

			for(int j=0;j<ListArray1.length;j++)
			{
				if(tokenB.trim().equals(ListArray1[j].trim()))
				{
					break;
				}
				else if(j==ListArray1.length-1)
				{
					ListBresult=ListBresult+"::"+tokenB;
				}
			}

		}

		if(ListAresult.equals("") && ListBresult.equals(""))
		{
			result="Lists are equal so Pass";
		}
		else
		{
			result = "Extra in List A =>" + ListAresult + "  Extra in List B =>" + ListBresult;
		}
		System.out.println(result);
		return result;
	}

	/***********************************************************************************************
	 * Function Description : It Checks The Presence of a element on page of given path
	 * author: Automators, date: 11-Apr-2013
	 * *********************************************************************************************/

	public boolean isPresent(By element)
	{

		SetImplicitWaitInMilliSeconds(500);
		if(driver.findElements(element).size()!=0)
		{
			SetImplicitWaitInMilliSeconds(10000);
			return true;
		}
		else
		{
			SetImplicitWaitInMilliSeconds(10000);
			return false;
		}


	}



	/***********************************************************************************************
	 * Function Description : Checks Presence and Visibility of an element on page of given path using id of element
	 * @author Automators, date: 27-Nov-2014
	 * *********************************************************************************************/


	public Boolean isVisible(By element)
	{
		try{

			WebDriverWait wait = new WebDriverWait(driver, 15);

			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			return true;
		}catch(Exception e){
			return false;
		}
	}


	/***********************************************************************************************
	 * Function Description : Checks Presence and Visibility of element on page by given xpath of element
	 * @author Automators, date: 27-Nov-2014
	 * *********************************************************************************************/

	/*
	public Boolean isVisibleUsingXpath(String elementXpath)
	{
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));
			return true;
		}catch(Exception e){
			return false;
		}
	}

	 */

	/***********************************************************************************************
	 * Function Description : It Checks The Maximum allowed char limit in the field
	 * author: Automators date: 11-Apr-2013
	 * *********************************************************************************************/


	public String CheckMaxLimit(By element,int ExpectedMaxLimit)
	{

		for(int count =1;count<=ExpectedMaxLimit+5;count++){
			driver.findElement(element).sendKeys("1");
		}

		int ActualMaxLimit =  driver.findElement(element).getAttribute("value").length();
		if(ActualMaxLimit==ExpectedMaxLimit){
			return "Pass";
		}
		else{
			return "Fail";
		}

	}



	/***********************************************************************************************
	 * Function Description : It Generates Name using timestamp
	 * author: Automators, date: 20-May-2013
	 * *********************************************************************************************/	

	public String GetNameAsCurrentTimeStamp(){
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		return Calendar.getInstance().getTime().toString().replaceAll(":", "").replace(" ", "");
	}


	/***********************************************************************************************
	 * Function Description : It returns a unique name derived from Timestamp
	 * author: Automators date: 5-May-2013
	 * *********************************************************************************************/

	public String getNameAsTimeStamp() {
		String TimeStamp = Calendar.getInstance().getTime().toString();
		TimeStamp = TimeStamp.replace(":", "").replace(" ", "").replace("+", "");
		return TimeStamp;
	}

	/***********************************************************************************************
	 * Function Description : It returns the value for the attribute specified
	 * author: Automators, date: 14-Jun-2013
	 * *********************************************************************************************/

	public String getAttribute(By element,String attributeName) {
		return driver.findElement(element).getAttribute(attributeName);
	}


	/***********************************************************************************************
	 * Function Description : It Checks a specific value is present in given list
	 * author: Automators, date: 23-July-2013
	 * *********************************************************************************************/

	public Boolean CheckExistenceOfAValueInAList(String Value,List<String> InputList) 
	{
		Boolean result=false;
		for(String option : InputList)
		{
			if(option.trim().equals(Value.trim()))
			{
				result=true;
				break;
			}

		}

		return result;
	}

	/***********************************************************************************************
	 * Function Description : Set/Write  String List at given excel path sheetname and column.
	 * author: Automators, date: 13-Jun-2013
	 * *********************************************************************************************/
	public void SetStringListInXLColumn(Xls_Reader datatable, String sheetname, String colname, List<String> list){

		for(int i=0; i<list.size(); i++){
			datatable.setCellData(sheetname, colname, i+2, list.get(i));
		}

	}

	/***********************************************************************************************
	 * Function Description : Compares two provided xl columns and set the status
	 * author: Automators, date: 13-Jun-2013
	 * *********************************************************************************************/
	public void CompareTwoXLColumns(Xls_Reader datatable, String sheetname, String colname1, String colname2, String statuscol){
		for(int i=2; i<datatable.getRowCount(sheetname)+1;i++){
			if(datatable.getCellData(sheetname, colname1, i).equals(datatable.getCellData(sheetname, colname2, i))){
				datatable.setCellData(sheetname, statuscol, i, "pass");
			}else{
				datatable.setCellData(sheetname, statuscol, i, "fail");
			}
		}
	}


	public void CompareTwoXLColumnsSaveResultInOtherXL(Xls_Reader datatable, String sheetname,String resultsheetname, String colname1, String colname2, String statuscol){
		String col1="";
		String col2="";

		for(int i=2; i<datatable.getRowCount(sheetname)+1;i++){
			col1=datatable.getCellData(sheetname, colname1, i);
			col2=datatable.getCellData(sheetname, colname2, i);
			if(col1.equals(col2)){
				datatable.setCellData(resultsheetname, statuscol, i, "pass");
			}else{
				datatable.setCellData(resultsheetname, statuscol, i, "fail : => "+col1+ " "+col2 + "");
			}
		}
	}

	public void CompareTwoDifferentXLColumnsSaveResultInOtherXL(Xls_Reader datatable, String sheetname1,String sheetname2,String resultsheetname, String colname1, String colname2, String statuscol){
		String col1="";
		String col2="";

		for(int i=2; i<datatable.getRowCount(sheetname1)+1;i++){
			col1=datatable.getCellData(sheetname1, colname1, i);
			col2=datatable.getCellData(sheetname2, colname2, i);
			if(col1.equals(col2)){
				datatable.setCellData(resultsheetname, statuscol, i, "pass");
			}else{
				datatable.setCellData(resultsheetname, statuscol, i, "fail : => "+col1+ " "+col2 + "");
			}
		}
	}

	/***********************************************************************************************
	 * Function Description : Will go to provided div/span/xpath and fetch the attribute of provided tagname
	 * Example: Fetching links text from a provided div: input required div xpath, tagname as "a" and attribute as"text"
	 * Example: Fetching links url from a provided div: input required div xpath, tagname as "a" and attribute as"href"
	 * author: Automators, date: 13-Jun-2013
	 * *********************************************************************************************/
	public List<String> FetchFromParent(By parent_element, String tagname, String attribute){

		ArrayList<String> list = new ArrayList<String>();
		List<WebElement> elements =driver.findElement(parent_element).findElements(By.tagName(tagname));
		for(int i=0; i< elements.size(); i++){
			if(attribute.equalsIgnoreCase("text"))
				list.add(elements.get(i).getText());
			else
				list.add(elements.get(i).getAttribute(attribute));	
		}
		return list;
	}





	/***********************************************************************************************
	 * Function Description : Navigate through all pages from SRP and fetches provided attribute and return all attributes as list  
	 * author: Automators, date: 13-Jun-2013
	 * *********************************************************************************************/
	@SuppressWarnings("unchecked")
	public List<String> FetchFromSRP(By by_whatToFetch, By by_nextbuttonONSRP, String attributeToFetch){

		ArrayList<String> list = new ArrayList<String>();
		boolean pagesDone=false;
		while(pagesDone==false){


			List<WebElement> we = driver.findElements(by_whatToFetch);
			for(int i=0; i< we.size(); i++){
				if(attributeToFetch.equalsIgnoreCase("text"))
					list.add(we.get(i).getText());
				else
					list.add(we.get(i).getAttribute(attributeToFetch));	

			}

			if(isVisible(by_nextbuttonONSRP)){
				driver.findElement(by_nextbuttonONSRP).click();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				pagesDone=true;
			}

		}			return list;

	}


	/***********************************************************************************************
	 * Function Description : Compares two given String Lists
	 * author: Automators, date: 13-Jun-2013
	 * *********************************************************************************************/
	public String CompareTwoStringLists(List<String> list1, List<String> list2){
		String result ="";
		if(list1.size() != list2.size()){
			return "fail count is not same";			
		}else{

			for(int i=0; i<list1.size(); i++){
				if(!(list1.get(i).equals(list2.get(i)))){
					result= result+list1.get(i)+"is not same as:"+list2.get(i)+", ";
				}

				if(result.equals("")){ result= "pass";}else{return "Fail, "+result;}
			}
		}


		return result;
	}


	/***********************************************************************************************
	 * Function Description : Returns the current directory absolute path
	 * author: Automators, date: 18-Apr-2014
	 * *********************************************************************************************/
	public String GetCurrentDirectoryAbsolutePath() {
		String current = "";
		try {
			current = new java.io.File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return current;
	}


	@SuppressWarnings("deprecation")
	public String SetKIOSKEmailasCurrentTimeStamp(){
		String Email = "";

		String Date = String.valueOf(Calendar.getInstance().getTime().getDate());
		String Month = String.valueOf(Calendar.getInstance().getTime().getMonth());
		String Year = String.valueOf(Calendar.getInstance().getTime().getYear()+1900);
		String Hours = String.valueOf(Calendar.getInstance().getTime().getHours());
		String Minutes = String.valueOf(Calendar.getInstance().getTime().getMinutes());
		String Seconds = String.valueOf(Calendar.getInstance().getTime().getSeconds());

		if(Date.length() < 2)
			Date = "0" + Date;

		if(Month.length() < 2)
			Month = "0" + Month;

		if(Year.length() < 4)
			Year = "0000";

		if(Hours.length() < 2)
			Hours = "0" + Hours;

		if(Minutes.length() < 2)
			Minutes = "0" + Minutes;

		if(Seconds.length() < 2)
			Seconds = "0" + Seconds;

		Email = "KIOSK-" + Date + Month + Year + "-" + Hours + Minutes + Seconds + "@yopmail.com"; 

		return Email;
	}





	/***********************************************************************************************
	 * Function Description : To get list of all connected Android devices (with UDIDs of Android Devices)
	 * @author Automators, date: 19-Oct-2015
	 * *********************************************************************************************/

	public List<String> getConnectedDevicesList(){

		List<String> devicesID = new ArrayList<String>();

		String command = "adb devices";
		try {
			Process process = Runtime.getRuntime().exec(command);       
			BufferedReader reader=new BufferedReader( new InputStreamReader(process.getInputStream()));
			String s;                
			while ((s = reader.readLine()) != null){         
				if(s.contains("device") && ! s.contains("attached")){
					String[] device = s.split("\t");
					devicesID.add(device[0]);

				}
			}  


		} catch (IOException e) {
			e.printStackTrace();
		}
		return devicesID;
	}


	/***********************************************************************************************
	 * Function Description : To get list of all connected Android devices (with UDIDs of Android Devices)
	 * @author Automators, date: 19-Oct-2015
	 * *********************************************************************************************/

	public int getconnectedDevicesNumber(){

		int connectDevices;
		connectDevices = this.getConnectedDevicesList().size();

		return connectDevices;
	}



}