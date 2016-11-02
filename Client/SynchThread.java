import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

/**
 * A runnable class to be created as a Thread to call the server
 * Utilised to demonstrate the deferred synchronous and asynchronous
 * server interactions.
 * 
 * @author Robert Logan - c3165020
 */
public class SynchThread implements Runnable{
    private org.omg.CORBA.Object synchObj;
    private String name = "";
    private String random = "";
    private ASynch callback = null;
    private boolean isAsynch = false;

    public SynchThread(org.omg.CORBA.Object obj, String n){
        name = n;
        synchObj = obj;
    }

    public SynchThread(org.omg.CORBA.Object obj, String n, ASynch a){
        name = n;
        synchObj = obj;
        callback = a;
        isAsynch = true;
    }

    public String getResult(){//called by the DeferredSynch class to retrieve the server result
        return random;
    }

    public synchronized void run(){
        try{    
            NamingContextExt ncRef = NamingContextExtHelper.narrow(synchObj);
            DemoRandom randomizer = DemoRandomHelper.narrow(ncRef.resolve_str(name));       
            System.out.println("Call to Server...");
            random = randomizer.getRandom();
            if(isAsynch){
                callback.setRand(random);//if the call is asynchronous send the result back to the caller
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}