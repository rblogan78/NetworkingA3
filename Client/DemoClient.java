import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

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
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}