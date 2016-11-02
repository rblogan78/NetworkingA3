import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

public class SynchThread implements Runnable{
    private org.omg.CORBA.Object synchObj;
    private String name = "";
    private String random = "";
    private ASynch callback = null;

    public SynchThread(org.omg.CORBA.Object obj, String n){
        name = n;
        synchObj = obj;
    }

    public SynchThread(org.omg.CORBA.Object obj, String n, ASynch a){
        name = n;
        synchObj = obj;
        callback = a;
    }

    public String getResult(){
        return random;
    }

    public synchronized void run(){
        try{    
            NamingContextExt ncRef = NamingContextExtHelper.narrow(synchObj);
            DemoRandom randomizer = DemoRandomHelper.narrow(ncRef.resolve_str(name));       
            System.out.println("Call to Server...");
            random = randomizer.getRandom();
            if(callback!=null){
                callback.setRand(random);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


}