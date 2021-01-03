/** @brief Sharpen your pencil on page 291 
 *  When run, main throws a java.lang.NullPointerException b/c
 *  Integer wrapper class reference is null when default
 *  initialized, whereas an int is default initialize to 0*/

public class TestBox
{
	Integer i;
	int j;

	public static void main (String[] args)
	{
		TestBox test = new TestBox();
		test.go();
	}

	public void go()
	{
		j = i; // Bug occurs on this assignement b/c autoboxing calls i.intValue() on a null reference
		System.out.println(j);
		System.out.println(i);
	}
}
