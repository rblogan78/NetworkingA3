import demoApp.*; 
import org.omg.CosNaming.*; 
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*; 
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
import java.util.Random;

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
        return rand;
    }
  
    public void shutdown(){
        orb.shutdown(false);
    }
}