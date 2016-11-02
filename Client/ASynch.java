import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

/**
 * This class runs the asynchronous server interaction. It creates a thread which calls
 * the server and blocks, allowing the client to continue processing until the server
 * returns a result.
 * 
 * @author Robert Logan - c3165020
 */ 
public class ASynch{
    private org.omg.CORBA.Object asynchObj;
    private String name = "";
    private static String asynchRand = "*";
    private int counter = 1;
    private boolean called = false;

    public ASynch(org.omg.CORBA.Object obj, String n){
        name = n;
        asynchObj = obj;
    }

    public void asynchronous(){
        try{
            System.out.println("Demonstrating asynchronous interaction.");
            while(counter<6){//loop five times with initial value
                System.out.println(counter+".  Value is "+asynchRand);
                Thread.sleep(1000);
                counter++;
            }
            SynchThread tAsynch = new SynchThread(asynchObj, "Demo", this);// create thread object
            Thread asynch = new Thread(tAsynch, "Synch");
            asynch.start();//start the thread
            Thread.sleep(1000);//wait 1 second

            while(asynchRand.equals("*")){//test for the change of the variable
                System.out.println(counter+".  Value is "+asynchRand);
                Thread.sleep(1000);
                counter++;
            }

            for(int i=0; i<5; i++){//loop 5 more times after variable update
                System.out.println(counter+".  Value is "+asynchRand);
                Thread.sleep(1000);
                counter++;
            }
                 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setRand(String s){//allows for callback of the server value by the thread
        asynchRand = s;
    }
}