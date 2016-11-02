import demoApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

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
            while(counter<6){
                System.out.println(counter+".  Value is "+asynchRand);
                Thread.sleep(1000);
                counter++;
            }
            SynchThread tAsynch = new SynchThread(asynchObj, "Demo", this);
            Thread asynch = new Thread(tAsynch, "Synch");
            asynch.start();
            Thread.sleep(1000);

            while(asynchRand.equals("*")){
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

    public void setRand(String s){
        asynchRand = s;
    }
}