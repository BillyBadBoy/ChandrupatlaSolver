package com.wem;

import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.*;


/**
 * <p>Implementation of Chandrupatla's root finding algorithm.</p>
 * <p>Based on the description given in 'Computational Physics,
 * Simulation of Classical and Quantum Systems.' by Philipp O.J. Scherer
 * (3rd ed. pages 109-111)</p>
 * @author William E Morgan
 */
public class ChandrupatlaSolver {
    /**
     * Finds a root of a function <tt>f(x)</tt>.
     * <p>
     * The function is specified as an instance of the built-in
     * <tt>DoubleUnaryOperator</tt> interface.
     * <p>
     * This method requires an initial interval <tt>[x_0,x_1]</tt> that is
     * known to contain a root. More precisely it is required that:
     * <tt>sign(f(x_0).sign(f(x_1) ≤ 0</tt>.
     * <p>
     * The required precision can be set using either (or both) tolerance
     * parameters. One parameter is for setting the absolute error,
     * while the other is for setting the relative error. A result is returned
     * when either tolerance is satisfied.
     * <p>
     * To prevent infinite loops the maximum number of iterations must
     * be specified. Failure to find a root can occur either because no
     * root exists or because no Java double exists that is within the
     * desired tolerance. (In other words specifying very small tolerances
     * may cause the algorithm to fail to find a root).
     * <p>
     * Example: find the value of <tt>x</tt> that satisfies: <tt>x=cos(x)</tt>.
     * i.e. find a root of the function: <tt>f(x)=x-cos(x)</tt>. This function
     * is negative for <tt>x=0</tt> and positive for <tt>x=1</tt>, so we use
     * <tt>[0.0,1.0]</tt> as the initial interval. A relative tolerance of 1e-12
     * is used, which means, roughly speaking, 12 significant figures:
     * <p>
     * <tt>ChandrupatlaSolver.solve(x -> x - Math.cos(x), 0.0, 1.0, 0.0, 1e-12, 20);</tt>
     * </p>
     * (This gives the solution as 0.7390851332151607, which is correct.)
     *
     * @param f     the function to be solved
     * @param x_0   one end of the initial search interval
     * @param x_1   the other end of the initial search interval
     * @param abs_tolerance    x absolute tolerance
     * @param rel_tolerance    x relative tolerance
     * @param maxNumIterations the maximum number of iterations to perform
     * @return a root of the function f
     */
    public static double solve(
            DoubleUnaryOperator f,
            double x_0,
            double x_1,
            double abs_tolerance,
            double rel_tolerance,
            int maxNumIterations) {

        double a, b, c, f_a, f_b, f_c;

        // initialization
        a = x_1; f_a = f.applyAsDouble(a);
        b = x_0; f_b = f.applyAsDouble(b);

        double t = 0.5;

        // check that x_0 and x_1 'bracket' a root
        if (signum(f_a) * signum(f_b) > 0) throw
                new ArithmeticException("sign(f(x_0)) and sign(f(x_1)) must be different!");

        // iteration
        for (int i = 0; i < maxNumIterations; i++) {
            double x_t = a + t * (b - a);
            double f_t = f.applyAsDouble(x_t);
            if (signum(f_t) == signum(f_a)) {
                c = a; f_c = f_a; // a => c
            }
            else {
                c = b; f_c = f_b; // b => c
                b = a; f_b = f_a; // a => b
            }
            a = x_t; f_a = f_t;   // t => a

            // pick 'better' solution out of a and b
            double x_m, f_m;
            if (abs(f_a) < abs(f_b)) {
                x_m = a; f_m = f_a; // a => m
            }
            else {
                x_m = b; f_m = f_b; // b => m
            }

            double tl = max(rel_tolerance * abs(x_m), abs_tolerance) / abs(a - b);
            if ((tl >= 1.0) || f_m == 0.0) return x_m;

            double xi  = (a - b) / (c - b);
            double phi = (f_a - f_b) / (f_c - f_b);

            t = ((1.0 - sqrt(1 - xi)) < phi && phi < sqrt(xi)) ?
                    (f_a / (f_b - f_a)) * (f_c / (f_b - f_c)) +
                            ((c - a) / (b - a)) * (f_a / (f_c - f_a)) * (f_b / (f_c - f_b)) : 0.5;


            // ensure that t is not unnecessarily close to 0.0 or 1.0
            if      (tl > 0.5)     t = 0.5;
            else if (t < tl)       t = tl;
            else if (t > 1.0 - tl) t = 1.0 - tl;
        }
        throw new ArithmeticException("No root found after " + maxNumIterations + " iterations!");
    }
}
