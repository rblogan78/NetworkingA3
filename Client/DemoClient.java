import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

public class DemoClient
{
    private static String random = "*";
    private static int counter = 1;
    public static void main(String[] args)
    { 
        try
        {
            ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            
            String name = "Demo";
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            DemoRandom demoRef = DemoRandomHelper.narrow(ncRef.resolve_str(name));
            
            while (counter<6){
                long inc = 0;
                System.out.println(counter+".  Value is "+random);
                for(int j=1;j<200000;j++){
                    for(int l=1;l<200000;l++){
                        inc++;
                    }
                }
                counter++;
            }
            
            random = demoRef.getRandom();
            System.out.println("Call to Server...");
            while(counter<11){
                System.out.println(counter+".  Value is "+random);
                counter++;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}