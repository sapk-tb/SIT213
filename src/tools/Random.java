package tools;

/**
 *
 * @author Antoine GIRARD
 */
public class Random extends java.util.Random {

    public Random() {
        super();
    }

    public Random(long seed) {
        super(seed);
    }

    /**
     * 
     * @return Renvoie un double respectant une loi gaussienne en ne se basant pas sur MathStrict comme la version originelle.
     */
    @Override
    public double nextGaussian() {
        return Math.sqrt(-2 * Math.log(nextDouble())) * Math.cos(2 * Math.PI * nextDouble());
    }
}
