package main.java.com.revature.driver;
 import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.Admin;
import main.java.People;

public class Testing {
Admin a = new Admin();
People p = new People();

private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

@Test
public void TestAdminStatus() {	
	assertEquals(a.getStanding(), "admin");
}

@Test
public void TestPersonStatus() {
	assertEquals(p.getStanding(), "admin");
}

@Test
public void TestAStatus() {	
	assertEquals(a.getStanding(), "standby");
}

@Test
public void TestPStatus() {	
	assertEquals(a.getStanding(), "standby");
}

@Before
public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
}

@After
public void cleanUpStreams() {
    System.setOut(null);
    System.setErr(null);
}


}
