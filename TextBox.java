package GenericFunctionsLibrary;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;

public class TextBox {

	public AndroidDriver driver;
	public GenericFunctions generic;

	public TextBox(AndroidDriver driver, GenericFunctions generic)
	{
		this.driver = driver;
		this.generic = generic;
	}


	/***********************************************************************************************
	 * Function Description : Fill given Text in TextBox
	 * author: Automators, 
	 * date: 19-Nov-2013
	 * *********************************************************************************************/

	public void FillTextBox_Android(By keywords_Txt,String inputdata)
	{
		if(inputdata.trim().equals(""))
		{
			driver.findElement(keywords_Txt).clear();
			return;
		}
		
		driver.findElement(keywords_Txt).click();
		driver.findElement(keywords_Txt).clear();
		driver.findElement(keywords_Txt).sendKeys(inputdata);
	}



	/***********************************************************************************************
	 * Function Description : Get Text from TextBox Android
	 * author: Automators, 
	 * date: 16-March-2015
	 * *********************************************************************************************/

	public String GetValueFromTextBox_Android(By locator)
	{
		String result="";
		result=driver.findElement(locator).getText();
		return result;
	}

	/***********************************************************************************************
	 * Function Description : Fill multi-line text box with suggester
	 * author: Automators,
	 * date: 9-Sep-2015
	 * *********************************************************************************************/

	public void FillTextBoxwithSuggestor_Android(By field_MuTxt, String inputdata, String suggestorYesNo)
	{
		MobileActions act = new MobileActions(driver, generic);
		if(inputdata.trim().length()==0) {
			driver.findElement(field_MuTxt).clear();
			act.CloseKeyboard_Android();
			return;
		}
		this.FillTextBox_Android(field_MuTxt, inputdata);
		generic.GoToSleep(1000);
		if(suggestorYesNo.equalsIgnoreCase("yes")) {
			act.CloseKeyboardIfSuggestorPresent_Android();
		}
		else {
			act.CloseKeyboard_Android();
		}
	}

	/***********************************************************************************************
	 * Function Description : Fill multi-line text box with suggester on editor screen
	 * author: Automators,
	 * date: 9-Sep-2015
	 * @return: String - to get error on editor page
	 * *********************************************************************************************/

	public String FillTextBoxwithSuggestor_Android(By field_MuTxt, String inputdata, String suggestorYesNo, String editorYesNo,
			By editorfield_Txt, By doneOnEditor_Btn, By errorOnEditor_Lbl)
	{
		/*System.out.println("all values -- " +inputdata +" + " +suggestorYesNo +" + " +editorYesNo);*/
		String error_editor = "";
		
		MobileActions act = new MobileActions(driver, generic);
		act.TapOnElement_Android(field_MuTxt); 
		driver.findElement(editorfield_Txt).clear();
		act.CloseKeyboard_Android();
		driver.findElement(editorfield_Txt).sendKeys(inputdata);
		
		if(suggestorYesNo.equalsIgnoreCase("yes")) { 
			act.CloseKeyboardIfSuggestorPresent_Android();
		}
		else {
			act.CloseKeyboard_Android();
		}

		if(editorYesNo.equalsIgnoreCase("yes"))
		{	
			if (generic.isVisible(errorOnEditor_Lbl)) {
				System.out.println("ERROR EXISTS");
				error_editor = driver.findElement(errorOnEditor_Lbl).getText();
				driver.findElement(editorfield_Txt).clear();
			}
		}
		
		act.TapOnElement_Android(doneOnEditor_Btn);

		return error_editor;

	}


}




