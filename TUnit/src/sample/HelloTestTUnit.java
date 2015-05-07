package sample;
import static tunit.Assert.*;
import tunit.Assert;

import org.junit.Test;

public class HelloTestTUnit {
	
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
