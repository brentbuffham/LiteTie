package litetie.model;


@SuppressWarnings("serial")
public class NegativeNumberException extends Exception {
	
		//constructor without parameters
		public NegativeNumberException() {}

		//constructor for exception description
		public NegativeNumberException(String description)
		    {
		    super(description);
		    }
		}

		class NegativeTest
		     {
		     public NegativeTest(){}
		     
		     //this method will throw a PrivateException
		     void method1(double a) throws NegativeNumberException
		      {
		      //specify when  to generate the exception
		      if(a<0)
		        {
		        String description="A NegativeNumberException has been generated\n "+ 
		        " The "+a+" arguments are negative!\n";
		        throw new NegativeNumberException(description);
		        }
		      }
		             
		     //this method will throw a PrivateException
//		      void method2(int a, int b)throws NegativeNumberException
//		       {
//		       System.out.println("You called method2 !!!\n\n");
//		       //specify when to generate the exception
//		       if((a*b)&gt10) 
//		         {
//		         String description="In method \"method2\" a NegativeNumberException has been generated\n "+
//		         " The "+a+" * "+b+" is grater that 10 !!!\n"+
//		         " Call this method with smaller arguments.";
//		         throw new NegativeNumberException(description);
//		         }
//		       }       
//		     }

		
		 public static void main(String[] args)
		  {
		  NegativeTest t=new NegativeTest();
		  try{
		     t.method1(-12); //EXCEPTION 
		     
		     }catch(NegativeNumberException e)
		       {e.printStackTrace();}
		  
		}              

}
