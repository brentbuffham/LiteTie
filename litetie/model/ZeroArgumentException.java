package litetie.model;
/**
 * 
 * @author brentbuffham
 * 
 * Checked Exception Class indication that an argument is equal to zero.
 * Extends Exception Class
 *
 */
@SuppressWarnings("serial")
public class ZeroArgumentException extends Exception {

	//constructor without parameters
	public ZeroArgumentException() {}

	//constructor for exception description
	public ZeroArgumentException(String description)
	{
		super(description);
	}
}

class ZeroTest
{
	public ZeroTest(){}

	//this method will throw a PrivateException
	void method1(double a) throws ZeroArgumentException
	{
		//specify when  to generate the exception
		if(a == 0)
		{
			String description="A ZeroArgumentException has been generated\n "+ 
			" The "+a+" arguments are equal to zero!\n";
			throw new ZeroArgumentException(description);
		}
	}

	public static void main(String[] args)
	{
		ZeroTest t=new ZeroTest();
		try{
			t.method1(0); //EXCEPTION 

		}catch(ZeroArgumentException e)
		{e.printStackTrace();}

	}              

}
