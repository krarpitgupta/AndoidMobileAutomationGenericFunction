package GenericFunctionsLibrary;

import io.appium.java_client.android.AndroidDriver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DropDown {

	AndroidDriver driver;
	GenericFunctions generic;

	public DropDown(AndroidDriver driver, GenericFunctions generic) {
		this.driver = driver;
		this.generic = generic;
	}

	/***********************************************************************************************
	 * Function Description : Select Values in a Dropdown using search
	 * suggestor. If single select dropdown then send values as string
	 * 
	 * @author Automators, date: 17-Aug-2015
	 * *********************************************************************************************/

	
	public void SelectValueFromSingleSelectDropdownUsingSearch_Android(
			By dropdownId, By searchInDD, String value) {
		String valueToSelectXpath = "";

		By resetBy = By.xpath("//android.widget.TextView[@text = 'Reset']");
		By clearBy = By.xpath("//android.widget.TextView[@text = 'Clear']");
		MobileActions act = new MobileActions(driver, generic);
		TextBox tb = new TextBox(driver, generic);

		act.TapOnElement_Android(dropdownId);
		generic.GoToSleep(500);
		if (generic.isVisible(resetBy)) {
			driver.findElement(resetBy).click();
		} else if (generic.isVisible(clearBy)) {
			driver.findElement(clearBy).click();
		}

		if (value.trim().equals(""))
			return;

		act.TapOnElement_Android(dropdownId);
		generic.GoToSleep(500);
		driver.findElement(searchInDD).click();

		driver.findElement(searchInDD).clear();
		tb.FillTextBox_Android(searchInDD, value.toLowerCase());
		valueToSelectXpath = "//android.widget.TextView[@text='" + value.trim()
				+ "']";
		if (generic.isVisible(By.xpath(valueToSelectXpath))) {
			driver.findElement(By.xpath(valueToSelectXpath)).click();
		}

	}
	public void SelectValueFromSingleSelectDropdownUsingSearchMaterial_Android(
			By dropdownId, By searchInDD, String value) {
		String valueToSelectXpath = "";
		MobileActions act = new MobileActions(driver, generic);


		TextBox tb = new TextBox(driver, generic);

		act.TapOnElement_Android(dropdownId);
		generic.GoToSleep(500);
		
		
		if (value.trim().equals(""))
			{driver.navigate().back();
			 return;
			}

		driver.findElement(searchInDD).click();

		driver.findElement(searchInDD).clear();
		tb.FillTextBox_Android(searchInDD, value.toLowerCase());
		valueToSelectXpath = "//android.widget.TextView[@text='" + value.trim()
				+ "']";
		if (generic.isVisible(By.xpath(valueToSelectXpath))) {
			driver.findElement(By.xpath(valueToSelectXpath)).click();
		}

	}
	/***********************************************************************************************
	 * Function Description : Select Values in a Dropdown using search
	 * suggestor. If multiselect dropdown then send values as string array else
	 * string
	 * 
	 * @author Automators, date: 17-Aug-2015
	 * *********************************************************************************************/

	public void SelectValueFromDropdownUsingSuggestor_Android(By dropdownId,
			By searchInDD, Object object) {
		String valueToSelectXpath = "";
		By done_Btn = By.xpath("//android.widget.Button[@text = 'Done']");
		By resetBy = By.xpath("//android.widget.TextView[@text = 'Reset']");
		By clearBy = By.xpath("//android.widget.TextView[@text = 'Clear']");
		MobileActions act = new MobileActions(driver, generic);
		TextBox tb = new TextBox(driver, generic);

		act.TapOnElement_Android(dropdownId);
		generic.GoToSleep(500);
		if (generic.isVisible(resetBy)) {
			driver.findElement(resetBy).click();
		} else if (generic.isVisible(clearBy)) {
			driver.findElement(clearBy).click();
		}

		if (object.toString().trim().equals(""))
			return;

		act.TapOnElement_Android(dropdownId);
		generic.GoToSleep(500);
		driver.findElement(searchInDD).click();

		if (object instanceof String) {

			driver.findElement(searchInDD).clear();
			tb.FillTextBox_Android(searchInDD, object.toString().toLowerCase());
			valueToSelectXpath = "//android.widget.TextView[@text='"
					+ object.toString() + "']";
			if (generic.isVisible(By.xpath(valueToSelectXpath))) {
				driver.findElement(By.xpath(valueToSelectXpath)).click();
			}
			try {
				act.CloseKeyboard_Android();
			} catch (Exception e) {
			}
			if (generic.isVisible(done_Btn)) {
				driver.findElement(done_Btn).click();
			}
		} else if (object instanceof String[]) {
			String value[] = (String[]) object;
			for (int i = 0; i < value.length; i++) {
				driver.findElement(searchInDD).clear();
				tb.FillTextBox_Android(searchInDD, value[i].toLowerCase());
				valueToSelectXpath = "//android.widget.TextView[@text='"
						+ value[i] + "']";
				if (generic.isVisible(By.xpath(valueToSelectXpath))) {
					driver.findElement(By.xpath(valueToSelectXpath)).click();
				}

			}
			try {
				act.CloseKeyboard_Android();
			} catch (Exception e) {
			}
			if (generic.isVisible(done_Btn))
				driver.findElement(done_Btn).click();
		}
	}

	/***********************************************************************************************
	 * Function Description : Select value from a single select dropdown It
	 * Requires xpath of dropdown and value to select
	 * 
	 * @author Automators, date: 02-Dec-2014
	 * *********************************************************************************************/

	public void SelectValueinSingleSelectDropDown_Android(By textboxBy,
			String valueToSelect) {
		int counter = 0;
		MobileActions act = new MobileActions(driver, generic);
		String valueToSelct_Xpath = "//android.widget.TextView[@text = '"
				+ valueToSelect + "']";
		By reset_By = By.xpath("//android.widget.TextView[@text = 'Reset']");
		By clear_By = By.xpath("//android.widget.TextView[@text = 'Clear']");

		System.out.println("xpath to select: " + valueToSelct_Xpath);

		// removed use of reset dropdown method, as that doesn't work for both
		// NG and Naukri
		act.TapOnElement_Android(textboxBy);
		if (generic.isVisible(reset_By)) {
			driver.findElement(reset_By).click();
		} else if (generic.isVisible(clear_By)) {
			driver.findElement(clear_By).click();
		}

		if (valueToSelect.trim().equals("")) {
			return;
		} else {
			act.TapOnElement_Android(textboxBy);
			while (!generic.isVisible(By.xpath(valueToSelct_Xpath))
					&& counter < 10) {
				act.swipeup();
				counter++;
			}

			act.TapOnElement_Android(By.xpath(valueToSelct_Xpath));
		}

	}
	public void SingleSelectValueinMultiDropDown_Android(By textboxBy,
			String valueToSelect1,String valueToSelect2) {
		int counter = 0;
		MobileActions act = new MobileActions(driver, generic);
		String valueToSelct_Xpath1 = "//android.widget.TextView[@text = '"
				+ valueToSelect1 + "']";
		String valueToSelct_Xpath2 = "//android.widget.TextView[@text = '"
				+ valueToSelect2 + "']";

		System.out.println("xpath to select: " + valueToSelct_Xpath1);

		
	
		if (valueToSelect1.trim().equals("")) {
			return;
		} else {
			act.TapOnElement_Android(textboxBy);
			while (!generic.isVisible(By.xpath(valueToSelct_Xpath1))
					&& counter < 10) {
				act.swipeup();
				counter++;
			}

			act.TapOnElement_Android(By.xpath(valueToSelct_Xpath1));
			act.TapOnElement_Android(By.xpath(valueToSelct_Xpath2));
			
		}
		

	}
	/***********************************************************************************************
	 * Function Description : Select Values in a Dropdown using search suggestor
	 * for material app. Pass value to select and deselect for dropdown. It will
	 * first deselct if you pass some than select. If both are null than it will
	 * click on cancel
	 * 
	 * @author Automators, date: 22-April-2016
	 * *********************************************************************************************/

	public void selectDeselectDropdownUsingSearch_AndroidMaterial(
			By dropdownId, By searchInDD, String valuesToSelect,
			String valuesToDeSelect, String dropdownHelpText) {
		TextBox tb = new TextBox(driver, generic);
		By done_Btn = By.xpath("//android.widget.Button[@text = 'Done']");
		By cancel_Btn = By.xpath("//android.widget.Button[@text = 'Cancel']");
		MobileActions act = new MobileActions(driver, generic);

		act.TapOnElement_Android(dropdownId);

		if (valuesToDeSelect.equalsIgnoreCase(dropdownHelpText)) {
			valuesToDeSelect = "";
		}

		if (valuesToSelect.equals("") && valuesToDeSelect.equals("")) {
			act.TapOnElement_Android(cancel_Btn);
			return;
		} else if (!valuesToDeSelect.equals("")) {

			String[] deselectValue = valuesToDeSelect.split(",");

			act.TapOnElement_Android(searchInDD);
			for (int i = 0; i < deselectValue.length; i++) {

				// driver.findElement(searchInDD).clear();
				tb.FillTextBox_Android(searchInDD, deselectValue[i].toString());
				By xpath = By.xpath("//android.widget.TextView[@text='"
						+ deselectValue[i].toString() + "']");
				act.TapOnElement_Android(xpath);
			}
		} else if (!valuesToSelect.equals("")) {
			String[] selectValue = valuesToSelect.split(",");

			act.TapOnElement_Android(searchInDD);
			for (int i = 0; i < selectValue.length; i++) {
				// driver.findElement(searchInDD).clear();
				tb.FillTextBox_Android(searchInDD, selectValue[i].toString());
				By xpath = By.xpath("//android.widget.TextView[@text='"
						+ selectValue[i].toString() + "']");
				act.TapOnElement_Android(xpath);
			}
		}
		act.TapOnElement_Android(done_Btn);
	}

	/***********************************************************************************************
	 * Function Description : Select value from single select overlay Dropdowns
	 * 
	 * @author Automators, date: 04-May-2016
	 * *********************************************************************************************/

	public void selectValueFromSingleSelectDropdown_AndroidMaterial(
			By dropdownId, String valueToSelect, int startX, int startY,
			int endX, int endY) {
		MobileActions act = new MobileActions(driver, generic);

		if (valueToSelect.trim().equals(""))
			return;

		By valueToSelectXpath = By.xpath("//android.widget.TextView[@text = '"
				+ valueToSelect + "']");
		int counter = 0;

		act.TapOnElement_Android(dropdownId);
		if (generic.isVisible(valueToSelectXpath)) {
			act.TapOnElement_Android(valueToSelectXpath);
		} else {
			while (!generic.isVisible(valueToSelectXpath) && counter < 15) {
				driver.swipe(startX, startY, endX, endY, 1000);
				counter++;
			}
			act.TapOnElement_Android(valueToSelectXpath);
		}

	}
	
	
	
	

	/***********************************************************************************************
	 * Function Description : Reset value of any DropDown author: Rajat Jain,
	 * date: 16-March-2015
	 * *********************************************************************************************/
	public void ResetDropDown_Android(By DropDownBy, By ResetBy) {
		MobileActions act = new MobileActions(driver, generic);
		if (generic.isVisible(DropDownBy)) {
			act.TapOnElement_Android(DropDownBy);
			generic.GoToSleep(1000);
			act.TapOnElement_Android(ResetBy);
		} else {
			System.out.println("unable to find dropdown");
		}
	}

	/*************************************************************************************************************
	 * Description : Select value from a multi select dropdown with reset option
	 * available and Done button for Android It Requires xpath of dropdown,
	 * values to select along with Title of DD, also the last value of any
	 * dropdown (all values should be comma separated like a,b,c )
	 * 
	 * @author Automators, date: 01-Dec-2014
	 * ***********************************************************************************************************/

	public void SelectValueusInMultiSelectDropdownHavingResetAndDone_Android(
			By textboxBy, String valueToSelect, String locationDDTitle,
			String lastValueOfDD) {
		MobileActions act = new MobileActions(driver, generic);
		By titleOfDropdown_By = By.xpath("//android.widget.TextView[@text = '"
				+ locationDDTitle + "']");
		By resetButton_By = By
				.xpath("//android.widget.TextView[@text = 'Reset']");
		By clearButton_By = By
				.xpath("//android.widget.TextView[@text = 'Clear']");
		By doneButton_By = By.xpath("//android.widget.Button[@text = 'Done']");
		By lastValueofDD_By = By.xpath("//android.widget.TextView[@text = '"
				+ lastValueOfDD + "']");

		if (valueToSelect.equals("")) {
			driver.findElement(textboxBy).click();
			int count = 0;
			while (!(generic.isVisible(titleOfDropdown_By))) {
				driver.findElement(textboxBy).click();
				count++;
				if (count == 2) {
					break;
				}
			}

			if (generic.isVisible(clearButton_By)) {

				driver.findElement(clearButton_By).click();

			} else {
				driver.findElement(resetButton_By).click();
			}
			return;
		}

		String[] values = valueToSelect.split(",");
		driver.findElement(textboxBy).click();

		if (generic.isVisible(clearButton_By)) {

			driver.findElement(clearButton_By).click();

		} else {
			driver.findElement(resetButton_By).click();
		}
		generic.GoToSleep(1000);
		driver.findElement(textboxBy).click();
		for (int j = 0; j < values.length; j++) {
			try {
				int count = 0;
				while (!generic.isVisible(By
						.xpath("//android.widget.TextView[@text= '"
								+ values[j].toString() + "']"))) {
					act.swipeup();
					if (generic.isVisible(lastValueofDD_By)) {
						break;
					}
					count++;
					if (count == 20) {
						break;
					}
				}
				int count1 = 0;
				while (!generic.isVisible(By
						.xpath("//android.widget.TextView[@text = '"
								+ values[j].toString() + "']"))) {
					act.swipedown();
					count1++;
					if (count1 == 20) {
						break;
					}
				}
				// System.out.println("============================>>> Value to be clicked in dropdown ----> "
				// + values[j].toString());
				WebElement location = driver.findElement(By
						.xpath("//android.widget.TextView[@text = '"
								+ values[j].toString() + "']"));
				generic.GoToSleep(500);
				location.click();

			} catch (Exception e) {
				System.out.println("I am in try catch");
				System.out.println("exception:" + e);
			}
		}
		driver.findElement(doneButton_By).click();
	}

	/******************************************************************************************
	 * Description : Get all dropdown values Android It Requires id of dropdown,
	 * Id of dropdown container and its element and last value in the dropdown
	 * 
	 * @author Automators, date: 09-June-2015
	 * *****************************************************************************************/

	public List<String> GetDropdownValues_Android(By textboxBy,
			By dropdownContainerBy, By dropdownValue, String lastValueOfDD) {
		String LastValueOfDDOnPreview = "";
		MobileActions act = new MobileActions(driver, generic);

		List<String> Dropdown = new ArrayList<String>();
		int counter = 0;

		driver.findElement(textboxBy).click();

		if (generic.isVisible(By.xpath("//android.widget.TextView[@text='"
				+ lastValueOfDD + "']"))) {
			List<WebElement> container = driver
					.findElement(dropdownContainerBy).findElements(
							dropdownValue);
			for (WebElement option : container) {
				Dropdown.add(option.getText());
			}
		} else {
			while (!generic.isVisible(By
					.xpath("//android.widget.TextView[@text='" + lastValueOfDD
							+ "']"))
					&& counter < 30) {
				List<WebElement> container = driver.findElement(
						dropdownContainerBy).findElements(dropdownValue);
				int j = 0;
				if (!LastValueOfDDOnPreview.equals("")) {
					for (WebElement option : container) {
						if (option.getText().equals(LastValueOfDDOnPreview)) {
							break;
						}
						j++;
					}

					for (int i = 0; i <= j; i++) {
						container.remove(0);
					}
				}
				for (WebElement option : container) {
					Dropdown.add(option.getText());
				}

				LastValueOfDDOnPreview = Dropdown.get(Dropdown.size() - 1);
				act.swipeUpDropDown_Android();
				j = 0;
				if (generic.isVisible(By
						.xpath("//android.widget.TextView[@text='"
								+ lastValueOfDD + "']"))) {

					container = driver.findElement(dropdownContainerBy)
							.findElements(dropdownValue);
					if (!LastValueOfDDOnPreview.equals("")) {

						for (WebElement option : container) {
							if (option.getText().equals(LastValueOfDDOnPreview)) {
								break;
							}
							j++;
						}
						for (int i = 0; i <= j; i++) {
							container.remove(0);
						}
					}
					for (WebElement option : container) {
						Dropdown.add(option.getText());
					}
				}
				counter++;
			}
		}
		return Dropdown;

	}

	/******************************************************************************************
	 * Description : Get selected dropdown values Android It Requires xpath of
	 * dropdown, Id of dropdown container and last value in the dropdown
	 * 
	 * @author Automators, date: 21-Jan-2014
	 * *****************************************************************************************/

	public List<String> GetSelectedDropdownValues_Android(By textboxBy,
			By dropdownTitleBy, By dropdownContainerBy, By lastValueOfDDBy) {
		MobileActions act = new MobileActions(driver, generic);
		List<String> Dropdown = new ArrayList<String>();
		generic.GoToSleep(1000);
		driver.findElement(textboxBy).click();

		generic.GoToSleep(500);
		if (generic.isVisible(dropdownTitleBy)) {
			List<WebElement> container = driver
					.findElements(dropdownContainerBy);
			while (!generic.isVisible(lastValueOfDDBy)) {
				for (WebElement option : container) {
					if (option.isSelected()) {
						Dropdown.add(option.getText());
					}
				}
				act.swipeup();
				// Dropdown.add(option.getText());
				// System.out.println("Location :"+ option.getText());
			}
		}
		driver.navigate().back();
		return Dropdown;
	}

}
