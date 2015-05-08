package sample;
import static tunit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import tunit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class HelloTestTUnit {
	
	String site, location;
	
	// Each parameter should be placed as an argument here
	// Every time runner triggers, it will pass the arguments
	public HelloTestTUnit(String site, String location) {
		this.site = site;
		this.location = location;
	}

		
	@Parameterized.Parameters
	public static Collection<Object[]> primeNumbers() {
	      return Arrays.asList(new Object[][] {
	         { "hello.com", "ENG"},
	         { "ok.net", "UKR" }
	      });
	   }
	
	@Test
	public void test1() {
		
		assertEquals("11111", "2222222");
		Assert.assertEquals("hello message test1", "aaaa", "bbbbb");
		Assert.assertEquals("hello message test1", "aaaa", "bbbbb");
	}
	
	@Test
	public void test2() {
		Assert.assertEquals("hello message test2", "aaaa", "bbbbb");
	}
	
	@Test
	public void test3() {
		
		Assert.assertEquals("hello message test3", "11111", "11111");
	}
	
	@Test
	public void test4() {
		Assert.assertEquals("hello message test4", "AAAAA", "AAAAA");
	}

}
