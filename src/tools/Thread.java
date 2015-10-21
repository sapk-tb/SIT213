package tools;

/**
 *
 * @author Antoine GIRARD
 */
public class Thread {

    public static class AddToDoubleArray implements Runnable {

        private final Double[] output;
        private final Double[] t1;
        private final Double[] t2;
        private final int index_start;
        private final int index_end;

        AddToDoubleArray(Double[] output, Double[] t1, Double[] t2, int start, int end) throws Exception {
            this.output = output;
            this.t1 = t1;
            this.t2 = t2;
            index_start = start;
            index_end = end;
            if(end >= output.length){
                throw new Exception("End index en dehors du tableau de sorti");
            }
        }

        @Override
        public void run() {
            if (t1.length == t2.length) {
                for (int i = index_start; i <= index_end; i++) {
                    output[i] = t1[i] + t2[i];
                }
            } else {
                for (int i = index_start; i <= index_end; i++) {
                    output[i] = ((t1.length > i) ? t1[i] : 0) + ((t2.length > i) ? t2[i] : 0);
                }
            }
        }
    }

    public static class AddToNativeDoubleArray implements Runnable {

        private final double[] output;
        private final double[] t1;
        private final double[] t2;
        private final int index_start;
        private final int index_end;

        AddToNativeDoubleArray(double[] output, double[] t1, double[] t2, int start, int end) throws Exception {
            this.output = output;
            this.t1 = t1;
            this.t2 = t2;
            index_start = start;
            index_end = end;
            if(end >= output.length){
                throw new Exception("End index en dehors du tableau de sorti");
            }
        }

        @Override
        public void run() {
            if (t1.length == t2.length) {
                for (int i = index_start; i <= index_end; i++) {
                    output[i] = t1[i] + t2[i];
                }
            } else {
                for (int i = index_start; i <= index_end; i++) {
                    output[i] = ((t1.length > i) ? t1[i] : 0) + ((t2.length > i) ? t2[i] : 0);
                }
            }
        }
    }
}
