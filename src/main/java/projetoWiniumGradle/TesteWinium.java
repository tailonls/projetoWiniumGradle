package projetoWiniumGradle;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;

public class TesteWinium {

	public static String SISTEMA = "C:\\Windows\\System32\\calc.exe";
	public static String WINIUM = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\recursos\\Winium.Desktop.Driver.exe";
	WiniumDriver driver;
	WiniumDriverService service;

	@Before
	public void setupEnvironment() {

		// Instancia Winium Desktop Options
		DesktopOptions options = new DesktopOptions();
		options.setApplicationPath(SISTEMA);

		File driverPath = new File(WINIUM);
		service = new WiniumDriverService.Builder().usingDriverExecutable(driverPath).usingPort(9999).withVerbose(true)
				.withSilent(false).buildDesktopService();
		try {
			service.start();
		} catch (IOException e) {
			System.out.println("Excecao enquanto iniciava o WINIUM service!");
			e.printStackTrace();
		}
		driver = new WiniumDriver(service, options);
	}

	@Test
	public void calculatorTest() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.id("num2Button")).click();
		driver.findElement(By.id("plusButton")).click();
		driver.findElement(By.id("num2Button")).click();
		driver.findElement(By.id("equalButton")).click();

		String resultado = driver.findElement(By.id("CalculatorResults")).getAttribute("Name");
		// driver.findElement(By.id("Close")).click();
		Assert.assertTrue("Resultado esta diferente do esperado!", resultado.contains("334"));

		System.out.println(resultado);
	}

	@After
	public void tearDown() {
		driver.findElement(By.id("Close")).click();
		service.stop();
	}
}