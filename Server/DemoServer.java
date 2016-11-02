import demoApp.*; 
import org.omg.CosNaming.*; 
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*; 
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
import java.util.Random;

/**
 * This class provides the server functionalilty for interraction with the ORB
 * The RandomImpl class selects a random char from upper/lower case alphabet
 * and returns the string to the caller. The  code includes a random wait between
 * 5 and 15 seconds to simulate server load.
 * 
 * @author Robert Logan - c3165020
 */ 
public class DemoServer
{
    public static void main(String[] args)
    {
        try
        {
            ORB orb = ORB.init(args, null);
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

            RandomImpl demo = new RandomImpl();
      		demo.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(demo);
      	    DemoRandom href = DemoRandomHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Demo";
            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path, href);

            orb.run();
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

class RandomImpl extends DemoRandomPOA{

    private ORB orb;

    public void setORB(ORB orb_val){
        orb = orb_val;
    }
    
    public String getRandom(){
        Random r = new Random();

        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char randomChar = alphabet.charAt(r.nextInt(alphabet.length()));
        String rand = Character.toString(randomChar);
        try{
            this.randomWait();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return rand;
    }

    private void randomWait() throws InterruptedException{
        Random rInt = new Random();
        int low = 5000;
        int high = 15000;
        int result = rInt.nextInt(high-low) + low;
        Thread.sleep(result);//sleeps for a random period of time between 5 and 15 seconds
    }
  
    public void shutdown(){
        orb.shutdown(false);
    }
}