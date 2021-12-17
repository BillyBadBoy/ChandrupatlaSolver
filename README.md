# Chandrupatla Solver
Java implementation of Chandrupatla's root finding algorithm

This solver is reputed to be faster than the (much better known) Brent solver.

## Example
Here we solve the same function used in the wikipedia page for [Brent's method](https://en.wikipedia.org/wiki/Brent%27s_method#Example), namely:
```
y = (x + 3)(x - 1)^2
```
A root can be found using the Chandrupatla solver like this:
```java
DoubleUnaryOperator f = x -> (x + 3) * (x - 1) * (x - 1);
double root = ChandrupatlaSolver.solve(f, -4.0, 1.3333, 0.0, 1e-12, 25); // root = -3.0
```
The solver is called with the same initial interval as is used in the wikepedia page: `[-4, 4/3]`. The absolute tolerance is set
to zero, while the relative tolerance is set to `1e-12` (which means we require 12 siginicant figures accuracy). The maximum number
of iterations to perform is set at 25 (usually far more than enough).

The solver quickly produces the correct result: `-3.0`. For the sake of comparison (with the Brent wikipedia example), here are the
intervals after each iteration:

| Iter. | Lower             | Upper              | 
| ----- | ----------------- | ------------------ | 
| 1     |-4.0               |-1.3333333333333333 | 
| 2     |-4.0               |-2.666666666666667  |
| 3     |-3.3333333333333335|-2.666666666666667  |
| 4     |-3.3333333333333335|-2.9700375942681925 |
| 5     |-3.0014958386666657|-2.9700375942681925 |
| 6     |-3.0014958386666657|-2.9999944542156562 |
| 7     |-3.000000000110537 |-2.9999944542156562 |
| 8     |-3.000000000110537 |-3.0                |

The algorithm termintates after 8 iterations because an exact root has been found: `f(-3.0) = 0`. As these figures show, the 
Chandrupatla solver performs at least as well, if not better, than the Brent solver.
