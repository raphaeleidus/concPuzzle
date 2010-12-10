import java.io.FileNotFoundException;

import org.omg.CORBA.SystemException;

import moses.controlState.CSIterator;
import moses.controller.Law;


public class Simple extends Law {

	public void adopted(String arg) 
	{
		if(!Self.equals("sec@192.168.3.19"))
		{
			doForward(Self, "cap", "sec@192.168.3.19");
		}
		System.out.println("ADOPTED "+Self);
	}
	
	private void method1()
	{
		Object obj=null;
		try
		{
			obj.hashCode();
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		catch(SystemException e)
		{
			e.printStackTrace();
		}
		catch(Throwable e)
		{
			System.out.println("Throwable");
		}
	}
	
	private void method2()
	{
		Object obj=null;
		try
		{
			obj.hashCode();
			throw new FileNotFoundException();
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(Throwable e)
		{
			System.out.println("Throwable");
		}
	}
	public void sent(String src, String msg, String dest) 
	{		
		if(CS.has(dest))
		{
			System.out.println("FORWARDED to "+dest);
			doForward();
		}
		else
		{
			doDeliver(src,"illegal message", src);
		}
		
	}
	
	public void arrived(String src, String msg, String dest) 
	{
		
		doDeliver();
		if(Self.equals("sec@192.168.3.19"))
		{
			if(msg.startsWith("cap"))
			{
				System.out.println("CAP[1] received from "+ src);
				doAdd(src);
				CSIterator csi = new CSIterator(CS);
				while(csi.hasNext())
				{
					String member = csi.next().functor;
					doForward(dest, src, member);
				}				
				doForward(dest, src, src);
			}
		}
		else 
		{
			if(src.equals("sec@192.168.3.19"))
			{
				System.out.println("CAP[0] received for "+msg);
				doAdd(msg);
				doForward(dest, "welcome", msg);
			}
		}
	}
}
