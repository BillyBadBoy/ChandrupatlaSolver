# Chandrupatla Solver
Java implementation of Chandrupatla's root finding algorithm

This solver is reputed to be faster than the (much better known) Brent solver.

## Example
Here we solve this function:
```
f(x) = (x + 3)(x - 1)^2
```
This same function is solved in the wikipedia page for [Brent's method](https://en.wikipedia.org/wiki/Brent%27s_method).
This allows Chandrupatla's and Brent's algorithms to be compared.
 
A root of function `f` can be found using the Chandrupatla solver like this:
```java
// the function to solve                                          
DoubleUnaryOperator f = x -> (x + 3) * (x - 1) * (x - 1);         
                                                                  
// call the solver                                                
double r = ChandrupatlaSolver.solve(                              
        f,                   // function to solve                 
        -4.0,                // initial interval lower bound      
        4.0/3,               // initial interval upper bound      
        0.0,                 // absolute tolerance                
        1e-12,               // relative tolerance                
        25                   // max number of iterations permitted
);
```
The solver is called with the initial interval: `[-4, 4/3]`. The absolute tolerance is set to zero, while the relative tolerance
is set to `1e-12` (which means we require 12 siginicant figures accuracy). The maximum number of iterations to perform is set at 
25 (usually far more than enough).

The solver quickly produces the correct result: `-3.0`. For the sake of comparison (with the 
[Brent wikipedia example](https://en.wikipedia.org/wiki/Brent%27s_method#Example)), here are the intervals after each iteration:

| #     | Lower             | Upper              | 
| -- | ----------------- | ------------------ | 
| 1  |-4.0               |-1.3333333333333333 | 
| 2  |-4.0               |-2.666666666666667  |
| 3  |-3.3333333333333335|-2.666666666666667  |
| 4  |-3.3333333333333335|-2.9700375942681925 |
| 5  |-3.0014958386666657|-2.9700375942681925 |
| 6  |-3.0014958386666657|-2.9999944542156562 |
| 7  |-3.000000000110537 |-2.9999944542156562 |
| 8  |-3.000000000110537 |-3.0                |

The algorithm termintates after 8 iterations because an exact root has been found: `f(-3.0) = 0`. As can be seen, the performance 
of the Chandrupatla solver is at least as good, if not better, than the Brent solver.
