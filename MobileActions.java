package GenericFunctionsLibrary;

import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;


public class MobileActions {

	public AndroidDriver driver;
	public GenericFunctions generic;
	public  double MIN_PERCENTAGE = 0.35;
	public  double MAX_PERCENTAGE = 0.65;
	public  double CENTER_PERCENTAGE = 0.5;


	public MobileActions(AndroidDriver driver,GenericFunctions generic){
		this.driver=driver;
		this.generic = generic;
	}

	/***********************************************************************************************
	 * Function Description : Used to tap on any element
	 * date: 23-Nov-2014
	 * *********************************************************************************************/


	public void TapOnElement_Android(By locator)
	{
		try
		{
			driver.findElement(locator).click();
		}
		catch(Exception e)
		{
			generic.GoToSleep(2000);
			driver.findElement(locator).click();
		}
	}






	/***********************************************************************************************
	 * Function Description : Used to swipe down on the device and display the above contents
	 * @author Automators, date: 26-Nov-2014
	 * *********************************************************************************************/

	public void swipedown() 
	{
		driver.swipe(500, 300, 500, 1000 , 1000);   
	}




	/***********************************************************************************************
	 * Function Description : Used to swipe up on dropdown
	 * @author Automators, date: 09-July-2015
	 * *********************************************************************************************/
	public void swipeUpDropDown_Android() 
	{
		driver.swipe(510, 900,510,300,1000);
	}

	/***********************************************************************************************
	 * Function Description : Used to swipe up on dropdown
	 * @author Automators, date: 27-Jan-2016
	 * *********************************************************************************************/
	public void swipeDownDropDown_Android() 
	{
		driver.swipe(510, 190, 510, 1000 ,1000);
	}

	/***********************************************************************************************
	 * Function Description : Used to swipe up on the device and display the below contents
	 * @author Automators, date: 25-Nov-2014
	 * *********************************************************************************************/
	public void swipeup() 
	{
		driver.swipe(500, 1000, 500, 190, 1000);
	}


	/***********************************************************************************************
	 * Function Description : Used to swipe right on the device and display the contents
	 * @author Automators, date: 12-Mar-2014
	 * *********************************************************************************************/
	public void swipeRightOnDashboardRibbon() 
	{
		driver.swipe(90, 270, 650, 270, 3000);
	}


	/***********************************************************************************************
	 * Function Description : Used to swipe left on the device and display the contents
	 * @author Automators, date: 12-Mar-2014
	 * *********************************************************************************************/
	public void swipeLeftOnDashboardRibbon() 
	{
		driver.swipe(650, 270, 90, 270, 3000);
	}



	/***********************************************************************************************
	 * Function Description : Used to swipe up on the device from half page 
	 * @author Automators, date: 29-Jan-2015
	 * *********************************************************************************************/
	public void swipeupHalfPage_Android() 
	{
		driver.swipe(500, 570, 500, 190, 3000);
	}

	/***********************************************************************************************
	 * Function Description : Used to swipe down on the device from half page 
	 * @author Automators, date: 29-Jan-2015
	 * *********************************************************************************************/
	public void swipedownHalfPage_Android() 
	{
		driver.swipe(500, 190, 500, 570, 3000);
	}



	/***********************************************************************************************
	 * Function Description : Close keyboard if suggestor is displayed
	 * @author Automators, date: 25-Nov-2014
	 * *********************************************************************************************/

	public void CloseKeyboardIfSuggestorPresent_Android()
	{
		generic.GoToSleep(500);
		try{
			driver.hideKeyboard();
			generic.GoToSleep(3000);
			driver.hideKeyboard();
			generic.GoToSleep(4000);
		}
		catch(Exception e) {
			//driver.hideKeyboard();
			System.out.println("catch of keyboard band");
		}
	}

	/***********************************************************************************************
	 * Function Description : Close keyboard if displayed
	 * @author Automators, date: 25-Nov-2014
	 * *********************************************************************************************/

	public void CloseKeyboard_Android()
	{
		System.out.println("Closing KeyBoard");
		try{
		driver.hideKeyboard();
		generic.GoToSleep(3000);
		}
		catch(Exception e)
		{
			//driver.navigate().back();
		}
	}

	/***********************************************************************************************
	 * Function Description : Choose experience on exp slider present on Search Form 
	 * @author Automators, date: 17-Jan-2015
	 * *********************************************************************************************/



	@SuppressWarnings("static-access")
	public void ChooseExperience_Android(By ExperienceSliderBy, String Exp, String selectedExp)
	{
		By exp_By = By.xpath("//android.widget.TextView[@text = '"+Exp+"']");
		if(Exp.equals(""))
		{
			return;
		}
		String pointer;
		int selectednumericvalue;
		int toSelectnumericvalue;
		int startX;
		if(!(selectedExp.equals("-") || selectedExp.equals("30+")))
		{
			selectednumericvalue = Integer.parseInt(selectedExp);
			if(!(Exp.equals("30+")))
			{
				toSelectnumericvalue = Integer.parseInt(Exp);
			}
			else
			{
				toSelectnumericvalue = 31;
			}
			if(selectednumericvalue > 3 && (toSelectnumericvalue - 3) < selectednumericvalue)
			{
				int counter = 0;
				pointer = (selectednumericvalue-2)+ "";
				System.out.println("pointer :" + pointer);
				@SuppressWarnings("static-access")
				WebElement element = driver.findElement(ExperienceSliderBy.xpath("//android.widget.TextView[@text = '"+pointer+"']"));
				Point p = ((Locatable)element).getCoordinates().onPage();
				startX = p.getX();
				generic.GoToSleep(1000);
				while(!generic.isVisible(exp_By) && counter <10)
				{
					driver.swipe(startX, 650, 350, 650, 2000);
					counter++;
				}
				this.TapOnElement_Android(exp_By);
			}
			else if(selectednumericvalue>3 && (toSelectnumericvalue-3)>selectednumericvalue)
			{
				int counter = 0;
				pointer = (selectednumericvalue+2)+ "";
				System.out.println("pointer :" + pointer);
				WebElement element = driver.findElement(ExperienceSliderBy.xpath("//android.widget.TextView[@text = '"+pointer+"']"));
				Point p = ((Locatable)element).getCoordinates().onPage();
				startX = p.getX();
				generic.GoToSleep(1000);
				while(!generic.isVisible(exp_By) && counter <10)
				{
					driver.swipe(startX, 650, 350, 650, 2000);
					counter++;
				}
				this.TapOnElement_Android(exp_By);
			}
			else
			{
				this.TapOnElement_Android(exp_By);
			}

		}
		else
		{
			if(selectedExp.equals("-"))
			{
				int counter=0;
				WebElement element = driver.findElement(ExperienceSliderBy.xpath("//android.widget.TextView[@text = '2']"));
				selectedExp = element.getText();
				Point p = ((Locatable)element).getCoordinates().onPage();
				startX = p.getX();
				generic.GoToSleep(1000);
				while(!generic.isVisible(exp_By) && counter <12)
				{
					driver.swipe(startX, 650, 350, 650, 1000);
					counter++;
				}				
				this.TapOnElement_Android(exp_By);

			}
			else
			{	
				int counter = 0;
				WebElement element = driver.findElement(ExperienceSliderBy.xpath("//android.widget.TextView[@text = '28']"));
				selectedExp = element.getText();
				Point p = ((Locatable)element).getCoordinates().onPage();
				startX = p.getX();
				generic.GoToSleep(1000);
				while(!generic.isVisible(exp_By) && counter <12)
				{
					driver.swipe(startX, 650, 350, 650, 1000);
					counter++;
				}				
				this.TapOnElement_Android(exp_By);
			}
		}
	}



	/***********************************************************************************************
	 * Function Description : Used to swipe up and swipe down on the device to search field or error 
	 * and will return true if visible.In this function if bottom xpath is not given than it will 
	 * swipe up 15 times maximum to search a field
	 * @author Automators, date: 22-Jan-2015
	 * *********************************************************************************************/
	public boolean SearchElementOnWholePage(By FieldBy,By TopBy,By BottomBy) {  
		int count=0;
		if(generic.isVisible(FieldBy)) {
			return true;     
		}
		else {
			while(!generic.isVisible(FieldBy)) {
				if(generic.isVisible(TopBy)) {
					break;
				}
				swipedown();
				count++;
				if(count==7) {
					break;
				}
			}
			count=0;
			while(!generic.isVisible(FieldBy)){ 
				if(BottomBy.equals("")) {
					while( (count<7) && !(generic.isVisible(FieldBy))) {  
						swipeup();                                        
						count++;
					} 
				}
				else {   
					if(generic.isVisible(BottomBy)) {
						break;
					}
					swipeup();
					count++;
					if(count==7) {
						break;
					}
				}
			}

			if(generic.isVisible(FieldBy)){
				return true;     
			}
			else{
				return false;
			}
		}
	}


	/***********************************************************************************************
	 * Function Description : Get all network connection status, true(On)/false(Off)
	 * @author Automators, date: 16-July-2015
	 * *********************************************************************************************/

	public String GetNetworkConnection() {

		NetworkConnectionSetting networkConnection = driver.getNetworkConnection();
		String network = networkConnection.toString();

		network = network.replace("{ ", "");
		network = network.replace("}", "");
		network = network.trim();
		String[] networks = network.split(", ");
		String networkAirplane = networks[0];
		String networkWifi = networks[1];
		String networkData = networks[2];

		return "\n" + networkAirplane +"\n"+networkWifi + "\n" + networkData;
	}



	/***********************************************************************************************
	 * Function Description : Set network true(On)/false(Off), as per the requirement by passing 
	 * true for same
	 * @author Automators, date: 16-July-2015
	 * *********************************************************************************************/

	public void SetNetworkConnection(boolean setAirplaneMode, boolean setWifi, boolean setData) {

		NetworkConnectionSetting networkConnection = new NetworkConnectionSetting(setAirplaneMode, setWifi, setData);
		driver.setNetworkConnection(networkConnection);
	}



}
