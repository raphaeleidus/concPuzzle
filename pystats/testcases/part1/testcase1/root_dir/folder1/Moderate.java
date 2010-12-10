
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.security.PrivateKey;

import org.omg.CORBA.SystemException;

import moses.controlState.Term;
import moses.controlState.UnifyResult;
import moses.member.*;
import moses.security.LGICert;
import moses.security.Secu;
import moses.security.certCreation;
import moses.util.*;

public class Moderate implements Agent 
{
	/*
	 This is just a simple comment
	 */
	
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
	
	//single line comment1 
	//single line comment2
	//single line comment3	
	private void run(String []args) 
	{
		String lawURL = args[0];
		String controllerHost = args[1];
		int controllerPort = Integer.parseInt(args[2]);
		String agentName = args[3];		
		
		System.out.println(args[0]+" "+args[1]+" "+args[2]+" "+args[3]);
		Member member = new Member(lawURL, Const.URL_LAW, controllerHost, controllerPort, agentName);
		
		if(args.length > 4)
		{
/*			System.out.println(args[4]+" "+args[5]);
			PrivateKey privateKey = certCreation.getPrivateKey(args[4]);
			LGICert cert = certCreation.getCert(args[5]);
			byte []sign = Secu.signSelfCertificate(cert, privateKey);			
			member.addCertificate(cert, sign);
*/		}
		
		System.out.println(member.adopt("somepassword", "somearguments"));
		
		new Receiver(this,member).start();
		
		while(true)
		{
			try
			{				
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String command  = in.readLine();
				if (command.equals("exit")) 
				{
				    member.close();
				    System.out.println("The Agent is shutting down...");
				    System.exit(0);
				}
				if (command.startsWith("send")) 
				{
				    Term ct = Term.parse(command);
				    if(ct == null) 
				    {
				    	System.out.println("send(msg,dest) -- not understood. Try again!");
				    	continue;
				    }
				    Term tt = Term.parse("send(%M,%D)");
				    UnifyResult ur = ct.unify(tt);
				    if(ur == null) 
				    {
				    	System.out.println("send(msg,dest) -- not understood. Try again!");
				    	continue;
				    }
				    String msg = ur.getSVar("M");
				    String dest = ur.getSVar("D");
				    member.send_lg(msg,dest);
				}
			    } catch(Exception e) {
				;
			    }
		}
		
	}
	
	
	public void processReply(Member arg0, String arg1) {
		
		System.out.println("Received1: " + arg1);
	}

	public void processReply(Member arg0, byte[] arg1) {
		
		System.out.println("Received2: " + arg1);
	}

	public void processReply(Member arg0, Object arg1) {
		// TODO Auto-generated method stub
		System.out.println("Received3: " + arg1);
	}

	public void processRequest(Member arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		System.out.println("Received4: " + arg1+ " "+arg2);
	}
	
	public static void main(String[] args) {
		Moderate somea = new Moderate();
		somea.run(args);
	}
}
