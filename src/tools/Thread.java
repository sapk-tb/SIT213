package tools;

/**
 *
 * @author Antoine GIRARD
 */
public class Thread {

    public static class AddToFloatArray implements Runnable {

        private final Float[] output;
        private final Float[] t1;
        private final Float[] t2;
        private final int index_start;
        private final int index_end;

        AddToFloatArray(Float[] output, Float[] t1, Float[] t2, int start, int end) throws Exception {
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

    public static class AddToNativeFloatArray implements Runnable {

        private final float[] output;
        private final float[] t1;
        private final float[] t2;
        private final int index_start;
        private final int index_end;

        AddToNativeFloatArray(float[] output, float[] t1, float[] t2, int start, int end) throws Exception {
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
