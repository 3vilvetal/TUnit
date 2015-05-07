package sample;

import org.junit.Assert;
import org.junit.AssumptionViolatedException; 
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class HelloTestJUnit {
	
	  private static String watchedLog;

	  @Rule
	  public TestRule watchman = new TestWatcher() {
		    @Override
		    public Statement apply(Statement base, Description description) {
		      return super.apply(base, description);
		    }
	
		    @Override
		    protected void succeeded(Description description) {
		      watchedLog += description.getDisplayName() + " " + "success!\n";
		      System.out.println("SUCCESS " + watchedLog);
		    }
	
		    @Override
		    protected void failed(Throwable e, Description description) {
		      watchedLog += description.getDisplayName() + " " + e.getClass().getSimpleName() + "\n";
		      System.out.println("FAIL " + watchedLog);
		    }
	
		    @Override
		    protected void skipped(AssumptionViolatedException e, Description description) {
		      watchedLog += description.getDisplayName() + " " + e.getClass().getSimpleName() + "\n";
		    }
	
		    @Override
		    protected void starting(Description description) {
		      super.starting(description);
		    }
	
		    @Override
		    protected void finished(Description description) {
		      super.finished(description);
		    }
	  };

	  @Test
	  public void fails() {
		  Assert.assertEquals("aaaa", "bbbb");
	  }

	  @Test
	  public void succeeds() {
		  Assert.assertEquals("aaaa", "aaaa");
	  }
}
