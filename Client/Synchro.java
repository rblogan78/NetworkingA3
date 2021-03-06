import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

/**
 * This class implements the logic for a synchronous interaction with the server.
 * The server is called via the orb and the client blocks waiting for reply.
 * Once received, the client continues processing.
 * 
 * @author Robert Logan - c3165020
 */ 
public class Synchro{
    private org.omg.CORBA.Object synchObj;
    private String name = "";
    private String random = "*";
    private int counter = 1;
    
    public Synchro(org.omg.CORBA.Object obj, String n){
        name = n;
        synchObj = obj;
    }

    public void synchronous(){
        try{
            System.out.println("Demonstrating synchronous interaction.");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(synchObj);
            DemoRandom synchRef = DemoRandomHelper.narrow(ncRef.resolve_str(name));

            while (counter<11){
                if(counter==6){
                    System.out.println("Call to Server...");
                    random = synchRef.getRandom();
                    System.out.println(counter+".  Value is "+random);
                    Thread.sleep(1000);
                }else{
                    System.out.println(counter+".  Value is "+random);
                    Thread.sleep(1000);
                } 
                counter++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}