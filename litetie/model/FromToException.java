package litetie.model;


/**
 * 
 * @author brent buffham
 * @category exception
 * @version beta
 * @category Exception
 * @description This is used to signify that a surfaceConnector has been attempted to connect to the same hole. 
 */

@SuppressWarnings("serial")
public class FromToException extends Exception {
	
		//constructor without parameters
		public FromToException() {}

		//constructor for exception description
		public FromToException(String description)
		    {
		    super(description);
		    }
		}

		class FromToTest
		     {
		     public FromToTest(){}
		     
		     //this method will throw a PrivateException
		     void method1(Dummy from, Dummy to) throws FromToException
		      {
		      //specify when  to generate the exception
		      if(from == to || to == from)
		        {
		        String description="A FromToException has been generated\n "+ 
		        " The from.."+from+" or to.."+to+" arguments are the same!\n";
		        throw new FromToException(description);
		        }
		      }
		             
		  

		     public static void main(String[] args){

		    	 FromToTest test=new FromToTest();

		    	 try{ 

		    		 Dummy t = new Dummy(1000, 4000, 100);
		    		 Dummy f = new Dummy(1000, 4000, 100);
		    		 test.method1(f, t); //EXCEPTION 
		    	 }
		    	 catch (ZeroArgumentException e) {
		    		 // TODO Auto-generated catch block
		    		 e.printStackTrace();
		    	 } 
		    	 catch (NegativeNumberException e) {
		    		 // TODO Auto-generated catch block
		    		 e.printStackTrace();
		    	 }
		    	 catch(FromToException e)
		    	 {e.printStackTrace();}

		     }              
		 
}
