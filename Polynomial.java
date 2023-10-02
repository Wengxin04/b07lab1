import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial
{
	// a. fields
	// length of coefficients and exponents should be the same for a polynomial
	double[] coefficients; // it should be non-zero except for initializing a zero polynomial
	int[] exponents; // exponents should be unique
	
	public Polynomial() // d.ii & b. no-argument constructor 1
	{
		coefficients = new double[]{0};
		exponents = new int[]{0};
		
	} // initializes the polynomial to 0
	
	public Polynomial(double[] c, int[] e) // d.iii & b. constructor 2
	{
		if (c.length != e.length)
		{
			throw new IllegalArgumentException("Coefficient array length must be the same as exponent array length.");
		}
		
	    for (int i = 0; i < e.length; i++) {
	        for (int j = i + 1; j < e.length; j++) {
	            if (e[i] == e[j]) {
	                throw new IllegalArgumentException("Exponent array should not have duplicate values.");
	            }
	        }
	    }
		coefficients = c.clone();
		exponents = e.clone();
	}
	
	public Polynomial add(Polynomial p)
	{
	    double[] combinedCoefficients = new double[this.coefficients.length + p.coefficients.length];
	    int[] combinedExponents = new int[this.exponents.length + p.exponents.length];
	    int index = 0;

	    for (int i = 0; i < this.coefficients.length; i++)
	    {
	        int exponent = this.exponents[i];
	        double coefficient = this.coefficients[i];

	        int pos = indexOf(p.exponents, exponent);
	        if (pos != -1)
	        {
	            coefficient += p.coefficients[pos];
	        }

	        combinedCoefficients[index] = coefficient;
	        combinedExponents[index] = exponent;
	        index++;
	    }

	    for (int i = 0; i < p.coefficients.length; i++)
	    {
	        if (indexOf(this.exponents, p.exponents[i]) == -1)
	        {
	            combinedCoefficients[index] = p.coefficients[i];
	            combinedExponents[index] = p.exponents[i];
	            index++;
	        }
	    }

	    double[] resultCoefficients = new double[index];
	    int[] resultExponents = new int[index];
	    System.arraycopy(combinedCoefficients, 0, resultCoefficients, 0, index);
	    System.arraycopy(combinedExponents, 0, resultExponents, 0, index);

	    return new Polynomial(resultCoefficients, resultExponents);
	}

	// Helper method to find the index of an element in an array
	private int indexOf(int[] array, int value)
	{
	    for (int i = 0; i < array.length; i++)
	    {
	        if (array[i] == value)
	        {
	            return i;
	        }
	    }
	    return -1;
	}

	
	public double evaluate(double x) // d.v
	{
		double solution = 0;
		
		for (int i = 0; i < this.coefficients.length; i++)
		{
			solution += (this.coefficients[i] * Math.pow(x, this.exponents[i]));
		}
		
		return solution;
	}
	
	public boolean hasRoot(double x) // d.vi
	{
		double epsilon = 1e-10; 
		return Math.abs(this.evaluate(x)) < epsilon;
	}

    public Polynomial multiply(Polynomial p) // c.
    {
        int[] newExponents = new int[this.exponents.length * p.exponents.length];
        double[] newCoefficients = new double[this.coefficients.length * p.coefficients.length];

        int index = 0;
        for (int i = 0; i < this.exponents.length; i++)
        {
            for (int j = 0; j < p.exponents.length; j++)
            {
                newExponents[index] = this.exponents[i] + p.exponents[j];
                newCoefficients[index] = this.coefficients[i] * p.coefficients[j];
                index++;
            }
        }

        // Combine like terms (same exponents)
        int unique_count = newExponents.length;

        for (int i = 0; i < newExponents.length; i++)
        {
            if (newExponents[i] != -1)
            {
                for (int j = i + 1; j < newExponents.length; j++)
                {
                    if (newExponents[i] == newExponents[j] && newExponents[j] != -1)
                    {
                        newCoefficients[i] += newCoefficients[j];
                        newCoefficients[j] = 0;
                        newExponents[j] = -1;
                        unique_count--;
                    }
                }
            }
        }
        
        double[] resultCoefficients = new double[unique_count];
        int[] resultExponents = new int[unique_count];
        
        int resultIndex = 0;
        for (int i = 0; i < newExponents.length; i++)
        {
            if (newExponents[i] != -1)
            {
                resultExponents[resultIndex] = newExponents[i];
                resultCoefficients[resultIndex] = newCoefficients[i];
                resultIndex++;
            }
        }
        
        double[] orderCoefficients = new double[unique_count];
        int[] orderExponents = new int[unique_count];

		for (int j = 0; j < unique_count; j++)
		{
		    int min = Integer.MAX_VALUE;
		    int min_index = -1;

		    for (int i = 0; i < resultExponents.length; i++) {
		        if (resultExponents[i] < min) {
		            min = resultExponents[i];
		            min_index = i;
		        }
		    }
		    orderExponents[j] = min;
		    orderCoefficients[j] = resultCoefficients[min_index];
		    
		    if (min_index != -1) {
		        resultExponents[min_index] = Integer.MAX_VALUE;
		    }
		}

        return new Polynomial(orderCoefficients, orderExponents);
    }
    
    public Polynomial(File file) throws IOException // d.
    {
        Scanner scanner = new Scanner(file);
        String polynomialString = scanner.nextLine();
        scanner.close();

        String[] terms = polynomialString.split("(?=[-+])");
        coefficients = new double[terms.length];
        exponents = new int[terms.length];

        for (int i = 0; i < terms.length; i++)
        {
            if (terms[i].contains("x"))
            {
            	String[] parts = terms[i].split("x");
            	if (parts[0].equals("-"))
            	{
            		coefficients[i] = -1;
            	}
            	else if (parts[0].equals("+") || parts[0].isEmpty())
            	{
            		coefficients[i] = 1;
            	}
            	else
            	{
                coefficients[i] = Double.parseDouble(parts[0]);
            	}
                if (parts.length > 1)
                {
                    exponents[i] = Integer.parseInt(parts[1].substring(1));
                }
                else
                {
                    exponents[i] = 1;
                }
            }
            else
            {
                coefficients[i] = Double.parseDouble(terms[i]);
                exponents[i] = 0;
            }
        }
    }
    
    public void saveToFile(String filename) throws IOException // e.
    {
        FileWriter writer = new FileWriter(filename);
        for (int i = 0; i < this.coefficients.length; i++)
        {
            if (i != 0 && this.coefficients[i] > 0)
            {
                writer.write("+");
            }
            if (this.exponents[i] == 0)
            {
                writer.write(String.valueOf(this.coefficients[i]));
            }
            else if (this.exponents[i] == 1)
            {
                writer.write(this.coefficients[i] + "x");
            }
            else
            {
                writer.write(this.coefficients[i] + "x^" + this.exponents[i]);
            }
        }
        writer.close();
    }
}