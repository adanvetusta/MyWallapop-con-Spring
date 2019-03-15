
package com.uniovi.tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;
import com.uniovi.entities.Product;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;
import com.uniovi.services.RolesService;
import com.uniovi.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyWallapopTests {

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	// static String PathFirefox64 = "C:\\Program Files\\Mozilla
	// Firefox\\firefox.exe";
	// static String Geckdriver022 = "C:\\Path\\geckodriver024win64.exe";
	// En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas):

	static String PathFirefox65 = "C:\\Users\\adanv\\Desktop\\FirefoxPortable\\App\\Firefox64\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\adanv\\Desktop\\EII\\SDI\\Laboratorio\\5\\PL-SDI-Sesión5-material\\pl5\\geckodriver022win64.exe";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	@Autowired
	private UsersService usersService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private UsersRepository usersRepository;

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		initdb();
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	public void initdb() {
		// Borramos todas las entidades.
		usersRepository.deleteAll();

		// Ahora las volvemos a crear
		User user1 = new User("pedro@pedro", "Pedro", "Díaz");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);
		User user2 = new User("lucas@lucas", "Lucas", "Núñez");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);
		User user3 = new User("maria@maria", "María", "Rodríguez");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);
		User user4 = new User("marta@marta", "Marta", "Almonte");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);
		User user5 = new User("pelayo@pelayo", "Pelayo", "Valdes");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[0]);
		User user6 = new User("edward@edward", "Edward", "Núñez");
		user6.setPassword("123456");
		user6.setRole(rolesService.getRoles()[0]);
		User userAdmin = new User("admin@admin", "admin", "admin");
		userAdmin.setRole(rolesService.getRoles()[1]);
		userAdmin.setPassword("admin@admin");
		Set<Product> user1Products = new HashSet<Product>();
		for (int i = 1; i < 5; i++)
			user1Products.add(
					new Product("Titulo A" + String.valueOf(i), "Descripcion" + String.valueOf(i), i * 1.0, user1));
		user1.setProducts(user1Products);
		Set<Product> user2Products = new HashSet<Product>();
		for (int i = 1; i < 5; i++)
			user2Products.add(
					new Product("Titulo B" + String.valueOf(i), "Descripcion" + String.valueOf(i), i * 1.0, user2));
		user2.setProducts(user2Products);
		Set<Product> user3Products = new HashSet<Product>();
		for (int i = 1; i < 8; i++)
			user3Products.add(
					new Product("Titulo C" + String.valueOf(i), "Descripcion" + String.valueOf(i), i * 1.0, user3));
		user3.setProducts(user3Products);
		Set<Product> user4Products = new HashSet<Product>();
		for (int i = 1; i < 15; i++)
			user4Products.add(
					new Product("Titulo D" + String.valueOf(i), "Descripcion" + String.valueOf(i), i * 1.0, user4));
		user4.setProducts(user4Products);
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(userAdmin);
	}

	// PR01. Registro de Usuario con datos válidos
	@Test
	public void PR01() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "josefo@josefo", "Josefo", "Perez", "123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "Gestión de productos");

	}

	// PR02. Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos)
	@Test
	public void PR02() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "", "", "", "123456", "123456");
		// Comprobamos que no pasa nada puesto que es un campo required. Seguimos en la
		// misma página
		PO_View.checkElement(driver, "text", "Registrate");
	}

	// PR03. Registro de Usuario con datos inválidos(repetición de contraseña
	// inválida).
	@Test
	public void PR03() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "adan@adan", "AdanVetusta", "Fernandez", "123456", "654321");
		// Comprobamos que no pasa nada puesto que es un campo required. Seguimos en la
		// misma página
		PO_View.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// PR04. Registro de Usuario con datos inválidos (email existente).
	@Test
	public void PR04() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "edward@edward", "AdanVetusta", "Fernandez", "123456", "654321");
		// Comprobamos que no pasa nada puesto que es un campo required. Seguimos en la
		// misma página
		PO_View.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
	}

	// PR05. Inicio de sesión con datos válidos (administrador).
	@Test
	public void PR05() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@admin", "admin@admin");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "text", "Gestión de usuarios");
	}

	// PR06. Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void PR06() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@pedro", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "text", "Gestión de productos");

	}

	// PR07. Inicio de sesión con datos inválidos (usuario estándar,campo email y
	// contraseña vacíos).
	@Test
	public void PR07() {

		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario con email y contraseñas vacíos
		PO_LoginView.fillForm(driver, "", "");
		// Comprobamos que no pasa nada puesto que es un campo required. Seguimos en la
		// misma página y miramos que sigue el botón
		PO_View.checkElement(driver, "text", "Identifícate");

	}

	// PR08. Inicio de sesión con datos válidos (usuario estándar, email existente,
	// pero contraseña incorrecta)
	@Test
	public void PR08() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

		// Rellenamos el formulario de manera incorrecta introduciendo una contraseña
		// errónea
		PO_LoginView.fillForm(driver, "pedro@pedro", "123456123456");

		// Comprobamos que nos muestra el mensaje de error
		PO_View.checkKey(driver, "Error.log", PO_Properties.getSPANISH());

	}

	// PR09. Inicio de sesión con datos inválidos (usuario estándar, email no
	// existente en la aplicación).
	@Test
	public void PR09() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario de manera incorrecta introduciendo un correo no
		// registrado
		PO_LoginView.fillForm(driver, "xurde@lluis", "123456");
		// Comprobamos que nos muestra el mensaje de error
		PO_View.checkKey(driver, "Error.log", PO_Properties.getSPANISH());

	}

	// PR10. Hacer click en la opción de salir de sesión y comprobar que se redirige
	// a la página de inicio
	// de sesión (Login).
	@Test
	public void PR10() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@pedro", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "text", "Gestión de productos");
		// Seleccionamos el botón de cerrar sesión
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Comprobamos que estamos en la página de login
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// PR11. Comprobar que el botón cerrar sesión no está visible si el usuario no
	// está autenticado.
	@Test
	public void PR11() {
		// De primeras estamos sin identificar, por lo que el botón de logout no debería de estar disponible
		PO_HomeView.checkNoElement(driver, "logout");
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@pedro", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "text", "Gestión de productos");
		// Seleccionamos el botón de cerrar sesión
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Comprobamos que no sale el botón
		PO_HomeView.checkNoElement(driver, "logout");
	}

	// PR12. Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema. 
	@Test
	public void PR12() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@admin", "admin@admin");
		//Pinchamos en la opción de menu de Usuarios: //li[contains(@id, 'marks-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		//Listado de usuarios
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/list')]");
		elementos.get(0).click();
		// Contamos el número de filas de notas
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		//En total hay 7 usuarios contando el administrador, por eso tendrían que salir 6
		assertTrue(elementos.size() == 6);
		
	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR13() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR14() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR15() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR16() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR17() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR18() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR19() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR20() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR21() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR22() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR23() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR24() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR25() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR26() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR27() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR28() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR29() {

	}

	// PR01. Acceder a la página principal /
	@Test
	public void PR30() {

	}

}
