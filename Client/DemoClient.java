import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

public class DemoClient
{
    private static String random = "*";
    public static void main(String[] args)
    {
        
        try
        {
            ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            
            String name = "Demo";
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            demo demoRef = demoHelper.narrow(ncRef.resolve_str(name));
            
            for(int i=1; i<6;i++){
                System.out.println(random);
            }
            
            random = demoRef.getRandom();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}