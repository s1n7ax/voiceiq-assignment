package common;

import org.openqa.selenium.By;

public class WebElements {
   public static By resolveX(String text, Object ... args) {
      return By.xpath(String.format(text, args));
   }
}
