import java.io.File;
import java.io.IOException;

public class Driver
{
    public static void main(String[] args)
    {
        // Test the default constructor
        Polynomial p = new Polynomial();
        System.out.println("p(3)=" + p.evaluate(3));
        System.out.println("It should return: 0.0");

        // Test the constructor with coefficient and exponent arrays
        double[] c1 = {-2, -0.5, 1};
        int[] e1 = {0, 1, 3};
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {-0.5, -1};
        int[] e2 = {1, 2};
        Polynomial p2 = new Polynomial(c2, e2);

        // Test the add method
        Polynomial s = p1.add(p2);
        System.out.println("p1+p2 at x=1 is: s(1)=" + s.evaluate(1));
        System.out.println("It should return: -3.0");

        System.out.println("p1+p2 at x=2 is: s(2)=" + s.evaluate(2));
        // Test the hasRoot method
        if (s.hasRoot(2))
            System.out.println("2 is a root of s");
        else
            System.out.println("2 is not a root of s");
        
        double[] c3 = {1, 1};
        int[] e3 = {0, 1};
        Polynomial p3 = new Polynomial(c3, e3);

        double[] c4 = {1, -2};
        int[] e4 = {2, 3};
        Polynomial p4 = new Polynomial(c4, e4);
        // Test the multiply method and evaluate method
        Polynomial product = p3.multiply(p4);
        System.out.println("Product of p3 and p4 at x=1: " + product.evaluate(1));
        System.out.println("It should return: -2.0");

        // Test the constructor that reads from a file and the saveToFile method
        try
        {
            String filename = "polynomial.txt";
            p3.saveToFile(filename);
            Polynomial pFromFile = new Polynomial(new File(filename));
            System.out.println("Polynomial from file evaluated at x=3: " + pFromFile.evaluate(3));
            System.out.println("It should return: 4.0");
        }
        catch (IOException e)
        {
            System.out.println("Error reading or writing to file: " + e.getMessage());
        }
    }
}