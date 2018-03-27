package JGPBanking.src.main.java.com.revature.driver;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class LoggingUtilTest {

	@Deployment
	public static Archive<?> createDeployment() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class).addAsManifestResource(EmptyAsset.INSTANCE,
				"beans.xml");
		// System.out.println(archive.toString(true));
		return archive;
	}
 // can make a new, customized tests
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
		assertTrue("2 = 2", (2==3));
	}

}
