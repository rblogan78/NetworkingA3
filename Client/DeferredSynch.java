import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

public class DeferredSynch{
    private org.omg.CORBA.Object synchObj;
    private String name = "";
    private String defsynchRand = "*";
    private int counter = 1;

    public DeferredSynch(org.omg.CORBA.Object obj, String n){
        name = n;
        synchObj = obj;
    }

    public void defSynchronous(){
        try{
            System.out.println("Demonstrating deferred synchronous interaction.");
            while(counter<6){
                System.out.println(counter+".  Value is "+defsynchRand);
                Thread.sleep(1000);
                counter++;
            }
            SynchThread tSynch = new SynchThread(synchObj, "Demo");
            Thread def = new Thread(tSynch, "Synch");
            def.start();
            Thread.sleep(1000);
            while(counter<11){
                System.out.println(counter+".  Value is "+defsynchRand);
                Thread.sleep(1000);
                counter++;
            }
            def.join();//waits for the thread to die
            defsynchRand = tSynch.getResult();//retrieves the result of the server call
            while(counter<16){
                System.out.println(counter+".  Value is "+defsynchRand);
                Thread.sleep(1000);
                counter++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}