import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

/**
 * This is the main class of the program and calls sequentially each helper
 * class to run the logic for the three different types of server interation.
 * 
 * @author Robert Logan - c3165020
 */ 
public class DemoClient
{
    public static void main(String[] args)
    { 
        try
        {
            ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            
            Synchro s = new Synchro(objRef, "Demo");
            s.synchronous();

            System.out.println("");
            
            DeferredSynch ds = new DeferredSynch(objRef, "Demo");
            ds.defSynchronous();

            System.out.println("");

            ASynch as = new ASynch(objRef, "Demo");
            as.asynchronous();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}