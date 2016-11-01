import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

public class SynchThread implements Runnable{
    private org.omg.CORBA.Object synchObj;
    private String name = "";
    private String random = "";

    public SynchThread(org.omg.CORBA.Object obj, String n){
        name = n;
        synchObj = obj;
    }

    public String getResult(){
        return random;
    }

    public synchronized void run(){
        try{    
            NamingContextExt ncRef = NamingContextExtHelper.narrow(synchObj);
            DemoRandom defSynch = DemoRandomHelper.narrow(ncRef.resolve_str(name));       
            System.out.println("Call to Server...");
            random = defSynch.getRandom();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


}