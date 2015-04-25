package gkxd.functionalJava;

public class _UsageExample {
    /* A basic function that returns the sum of two integers */
    static Function<Integer, NTuple<Integer, Integer>> integerAdd = new Function<Integer, NTuple<Integer, Integer>>() {
        @Override
        public Integer fun(NTuple<Integer, Integer> args) {
            return args.a + args.b;
        }
    };
    
    /* A slightly more complicated function that when given coefficients a, b, c, returns a second function
     * that represents the parabola a*x^2 + b*x + c
     */
    static Function<Function<Double, Double>, NTuple<Double, NTuple<Double, Double>>> generateParabola =
            new Function<Function<Double, Double>, NTuple<Double, NTuple<Double, Double>>>() {

        @Override
        public Function<Double, Double> fun(NTuple<Double, NTuple<Double, Double>> args) {
            
            final Double a = args.a;
            final Double b = args.b.a;
            final Double c = args.b.b;
            
            Function<Double, Double> parabola = new Function<Double, Double>() {
                @Override
                public Double fun(Double x) {
                    return a*x*x + b*x + c;
                }
            };
            
            return parabola;
        }
    };
    
    /* A function that takes two parabola functions and adds them together */
    static Function<Function<Double, Double>, NTuple<Function<Double, Double>, Function<Double, Double>>> addParabolas =
            new Function<Function<Double, Double>, NTuple<Function<Double, Double>, Function<Double, Double>>>() {
        
        @Override
        public Function<Double, Double> fun(
                NTuple<Function<Double, Double>, Function<Double, Double>> args) {
            
            final Function<Double, Double> parabola1 = args.a;
            final Function<Double, Double> parabola2 = args.b;

            Function<Double, Double> parabolaSum = new Function<Double, Double>() {
                @Override
                public Double fun(Double x) {
                    return parabola1.fun(x) + parabola2.fun(x);
                }
            };
            
            return parabolaSum;
        }
    };

    
    /* Main method */
    public static void main(String[] args) {
        Function<Double, Double> parabola1 = generateParabola.fun(new NTuple<Double, NTuple<Double, Double>>(1.0, new NTuple<Double, Double>(0.0, 1.0)));
        Function<Double, Double> parabola2 = generateParabola.fun(new NTuple<Double, NTuple<Double, Double>>(-1.0, new NTuple<Double, Double>(1.0, 0.0)));
        Function<Double, Double> parabolaSum = addParabolas.fun(new NTuple<Function<Double, Double>, Function<Double, Double>>(parabola1, parabola2));
        
        for (double i = -5; i <= 5; i++) {
            System.out.println(i + "\t" + parabola1.fun(i) + "\t" + parabola2.fun(i) + "\t" + parabolaSum.fun(i));
        }
    }
}
