//law(EcELaw, authority(CA), language(java))
//authority(CA, keyHash(ADDCC7FBEE14CF7A44D0572514974387))  

import java.io.FileNotFoundException;
import java.util.StringTokenizer;

import org.omg.CORBA.SystemException;

import moses.controlState.Term;
import moses.controlState.UnifyResult;
import moses.controller.Law;
import moses.util.Const;

/**
 * @since comments
 * @author Pavel Mahmud
 * try{
 * }
 * catch(TestException)
 * {
 * }
 * finally
 * {
 * }
 */
public class Hardest extends Law{

	public static final String secretary = "sec@192.168.3.19";
	
	/*
	 again goes some comment
	 try{
	 }
	 catch(NullPointerException e)
	 {
	 }
	 comment
	 */
	private void method1()
	{
		Object obj=null;
		try
		{
			obj.hashCode();
			try{
				String value = "";
			}catch(NullPointerException e)
			{
				e.printStackTrace();
			}
			catch(SystemException e)
			{
				e.printStackTrace();
			}
			//catch(Throwable e)
			//{
			//	System.out.println("Throwable");
			//}			
		}/*catch(NullPointerException e)
		{
			e.printStackTrace();
		}
*/		catch(SystemException e)
		{
			e.printStackTrace();
		}
		catch(Throwable e)
		{
			System.out.println("Throwable");
		}
		finally
		{
			try{
				String value = "";
			}catch(NullPointerException e)
			{
				e.printStackTrace();
			}
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
		}//catch(FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
		catch(Throwable e)
		{
			System.out.println("Throwable\"dfdlksdfs try{some stmt;}catch(Throwable e){some stmt;}");
			String t="dasdlfk\"desdf try{obj.hashCode();throw new FileNotFoundException();}catch(NullPointerException e){e.printStackTrace();}";
		}
		
	}
	
	public void adopted(String arg, String[] issuer, String[] subject, String[] attributes) 
	{			
		System.out.println("ADOPTED:");
		//doDiscloseAllCS();
		if (issuer.length != 0) {
            if (issuer[0].equals("CA") && subject[0].equals(Self) ){
            	if( attributes[0].equals("group(P)")) 
            	{
            		doAdd("group(P)");
            		doForward(Self, "message(GROUP,P)", secretary);
            		System.out.println("ADOPTED: group(P)");
            	}
            	else if(attributes[0].equals("group(K)"))
            	{
            		doAdd("group(K)");
            		doForward(Self, "message(GROUP,K)", secretary);
            		System.out.println("ADOPTED: group(K)");
            	}
            }
        }
		doDiscloseAllCS();
	}
	
	public static final String parseMessage(String message, int argNum)
	{
		StringTokenizer tokenizer = new StringTokenizer(message,"(),");
		for(int index=0; index<argNum;tokenizer.nextToken(),index++);
		return tokenizer.nextToken();
	}
	
	public void arrived(String src, String msg, String dest) 
	{
		//doDiscloseAllCS();
		System.out.println("ARRIVED: src="+src+"; msg="+msg+"; dest="+dest);
		
		//Term term = Term.parse(msg);
		//if(term == null)
		//	System.out.println("term null");
		if(Self.equals(secretary))
		{					
			//UnifyResult unifyResult = term.find("message(%Y,%X)");
			//if(unifyResult == null)
			//	System.out.println("unify null");
				String msgType = parseMessage(msg, 1);//unifyResult.getSVar("Y");
				System.out.println(msgType);
				if(msgType.equals("QUERY"))
				{
					String address = parseMessage(msg, 2);//unifyResult.getSVar("X");
					String groupP = "group("+address+",P)";
					String groupK = "group("+address+",K)";
					if(CS.has(groupP))
					{
						doForward(Self, "message(QUERYRESULT,"+address+",P)", src);
					}
					else if(CS.has(groupK))
					{
						doForward(Self, "message(QUERYRESULT,"+address+",K)", src);
					}
				}
				else if(msgType.equals("GROUP"))
				{
					String group = parseMessage(msg, 2);//unifyResult.getSVar("X");
					if(group.equals("P"))
					{
						doAdd("group("+src+",P)");
					}
					else if(group.equals("K"))
					{
						doAdd("group("+src+",K)");
					}
				}
			doDiscloseAllCS();
			return;
		}

		
		if(msg.equals("message(PROHIBIT)"))
		{
			String term1 = "prohibit("+src+")"; 
			if(!CS.has(term1))
			{
				doAdd(term1);
			}
			doDiscloseAllCS();
			return;
		}
		
		String selfType = CS.find("group(%X)").getTVar("X").functor;
		
		if(src.equals(secretary))
		{						
			String destType = parseMessage(msg, 3);
			String destination = parseMessage(msg, 2);

			UnifyResult unifyResult = CS.find("pendingMessage("+destination+",%B)");			
			Term msgTerm = unifyResult.getTVar("B");
			String message = msgTerm.functor;
			
			if(selfType.equals(destType))
			{				
				doForward(Self, "message(SIMPLE,"+message+")", destination);
			}
			else if(selfType.equals("P"))
			{
				doForward(Self, "message(COMMAND,"+message+")", destination);
				doAdd("obligation("+destination+")");
				doImposeObligation("obligation("+destination+")", 1, "sec");
			}
			else if(selfType.equals("K")&& CS.find("pendingCommand("+destination+",%Z)")!=null )
			{
				UnifyResult unifyResult2 = CS.find("pendingCommand("+destination+",%Z)"); 
				String command = unifyResult2.getSVar("Z");
				System.out.println("command = "+command+ " dest = "+destination);
				doForward(Self, "message(REPLY,"+command+","+message+")", destination);
				doRemove(unifyResult2.getTerm());
			}
			doRemove(unifyResult.getTerm());
			doDiscloseAllCS();
			return;
		}
		
		//String srcType = term.find("message(%A,%B)")
		if(msg.startsWith("message(SIMPLE"))
		{
			System.out.println("DELIVERED TO THE SAME TYPE");
			doDeliver();
		}
		else if(msg.startsWith("message(REPLY"))
		{
			if(CS.has("obligation("+src+")"))
			{
				doRepealObligation("obligation("+src+")");
				doRemove("obligation("+src+")");
				doDeliver();
			}
		}
		else if(msg.startsWith("message(COMMAND"))
		{
			String msg3 = parseMessage(msg, 2);//term.find("message(%A,%B,%C)").getSVar("%C");
			doAdd("pendingCommand("+src+","+msg3+")");
			doDeliver();
		}
		doDiscloseAllCS();
	}
	
	public void obligationDue(Term arg) 
	{
		//doDiscloseAllCS();
		System.out.println("OBLIGATIONDUE:");
		doDeliver(Self, "exception: no response", Self);
		doRemove(arg);
		doDiscloseAllCS();
	}
	
	public void sent(String src, String msg, String dest) 
	{		
		//doDiscloseAllCS();
		System.out.println("SENT: src="+src+"; msg="+msg+"; dest="+dest+ "; self="+Self);
		
		if(Self.equals(secretary))
		{
			return;
		}
		
		if(dest.equals(secretary))
		{
			return;
		}
		
		if(msg.equals("prohibit"))
		{
			doForward(src, "message(PROHIBIT)", dest);
			return;
		}
		
		if(CS.has("prohibit("+dest+")"))
		{
			doDeliver(src, "Prohibited", src);
			return;
		}
		
		doAdd("pendingMessage("+dest+","+msg+")");		
		doForward(Self, "message(QUERY,"+dest+")", secretary);
		
	}
	
	public void disconnected() {
		// TODO Auto-generated method stub
		//super.disconnected();
		System.out.println("DISCONNECTED try{some stmt}catch(TestException e){printstacktrace}");
	}
	
	public void exception(String arg0, String arg1, String arg2, String arg3,
			String arg4) {
		// TODO Auto-generated method stub
		//super.exception(arg0, arg1, arg2, arg3, arg4);
		System.out.println("EXCEPTION"+ arg0 +" "+arg1+" "+arg2+" "+arg3+" "+arg4);
	}
}
