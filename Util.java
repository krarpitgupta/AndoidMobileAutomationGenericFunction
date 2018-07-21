package GenericFunctionsLibrary;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;

public abstract class Util {
	
	public AndroidDriver driver;
	public GenericFunctions generic;

	
	public By EducationLayer_Lbl = By.xpath("//android.widget.TextView[@text = 'Tap to Close']");
	public abstract String Compare_Error(String SheetPath, String SheetName, int i);	
	public abstract String ValidateMaxLengthOfTextField(String value,By TextFieldId,String MaxLength);
	public abstract String GetKioskEmailId();
	public abstract void CloseEducationLayer();
	public abstract boolean CheckIfUserIsLoggedIn();
	public abstract boolean Check_GenericErrorMessage();
	public abstract boolean Check_GenericSuccessMessage();
	public abstract void Click_hamburger_Img();
	public abstract String Get_pageWideErrorMessage();
	public abstract String Get_pageWideSuccessMessage();
	public abstract void Click_cross_Img();
	public abstract String Get_PageTitle_Lbl();
	public abstract boolean Check_LoaderIsDisplayed_Img();
	public abstract void WaitForLoaderToClose();
	public abstract boolean Check_FeedbackLayerIsDisplayed();
	public abstract void CloseFeedbackLayer();
	public abstract void Logout();
	public abstract void PullDownToRefresh();
	public abstract void ClickTextOn_Lbl(String textToClick);
	public abstract void ClickTextOn_Btn(String textOnButtonToClick);
	public abstract String Get_AlertTitle_Lbl();
	public abstract String Get_dropdownTitle_Lbl();
    public abstract String RemoveSpecialCharacterFromString(String inputString,String specialCharacter);
	

	
}
