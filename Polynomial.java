public class Polynomial
{
	double[] coefficients; // d.i
	
	public Polynomial() // d.ii no-argument constructor
	{
		coefficients = new double[1];
	} // sets the polynomial to zero (element of array is the default value 0).
	
	public Polynomial(double[] c) // d.iii
	{
		coefficients = new double[c.length];
		
		for (int i = 0; i < c.length; i++) // copy array from argument
		{
			coefficients[i] = c[i];
		}
	}
	
	public Polynomial add(Polynomial p) // d.iv
	{
		int max_length = Math.max(this.coefficients.length, p.coefficients.length);
		double[] result = new double[max_length];
		
		for (int i = 0; i < max_length; i++)
		{
            if (i < this.coefficients.length)
            {
                result[i] += this.coefficients[i];
            }
            if (i < p.coefficients.length)
            {
                result[i] += p.coefficients[i];
            }
        }

        return new Polynomial(result); // invoke constructor 2
	}
	
	public double evaluate(double x) // d.v
	{
		double solution = 0;
		
		for (int i = 0; i < this.coefficients.length; i++)
		{
			solution += this.coefficients[i] * Math.pow(x, i);
		}
		
		return solution;
	}
	
	public boolean hasRoot(double x) // d.vi
	{
		return this.evaluate(x) == 0;
	}
}