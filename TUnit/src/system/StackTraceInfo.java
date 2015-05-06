package system;

public class StackTraceInfo
{
    private static final int CLIENT_CODE_STACK_INDEX;

    static {
        // Finds out the index of "this code" in the returned stack trace - funny but it differs in JDK 1.5 and 1.6
        int i = 0;
        for (StackTraceElement ste: Thread.currentThread().getStackTrace())
        {
            i++;
            if (ste.getClassName().equals(StackTraceInfo.class.getName()))
            {
                break;
            }
        }
        CLIENT_CODE_STACK_INDEX = i;
    }
    
    //making additional overloaded method call requires +1 offset
    public static String getCurrentMethodName()
    {
        return getCurrentMethodName(1);     
    }

    private static String getCurrentMethodName(int offset)
    {
        return Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX + offset].getMethodName();
    }
    
    //making additional overloaded method call requires +1 offset
    public static String getCurrentClassName()
    {
        return getCurrentClassName(1);      
    }

    private static String getCurrentClassName(int offset)
    {
    return Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX + offset].getClassName();
    }
    
    //making additional overloaded method call requires +1 offset
    public static String getCurrentFileName()
    {
        return getCurrentFileName(1);     
    }

    private static String getCurrentFileName(int offset)
    {
        String filename = Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX + offset].getFileName();
        int lineNumber = Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX + offset].getLineNumber();

        return filename + ":" + lineNumber;
    }

    public static String getInvokingMethodName()
    {
        return getInvokingMethodName(2); 
    }
    
    //re-uses getCurrentMethodName() with desired index
    private static String getInvokingMethodName(int offset)
    {
        return getCurrentMethodName(offset + 2);    
    }

    public static String getInvokingClassName()
    {
        return getInvokingClassName(2); 
    }
    
    //re-uses getCurrentClassName() with desired index
    private static String getInvokingClassName(int offset)
    {
        return getCurrentClassName(offset + 1);     
    }

    public static String getInvokingFileName()
    {
        return getInvokingFileName(2); 
    }
    
    //re-uses getCurrentFileName() with desired index
    private static String getInvokingFileName(int offset)
    {
        return getCurrentFileName(offset + 1);     
    }
}
