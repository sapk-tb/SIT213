package tools;

/**
 * Classe servant ра faire des statistiques
 * source principale : http://stackoverflow.com/questions/7988486/how-do-you-calculate-the-variance-median-and-standard-deviation-in-c-or-java
 * @author Antoine GIRARD
 */
public class Statistic {

    double[] data;
    int size;

    public Statistic(Double[] data) {
        size = data.length;
        this.data = new double[size];
        for (int i = 0; i < size; i++) {
            this.data[i] = data[i];
        }
    }
    public Statistic(double[] data) {
        this.data = data;
        size = data.length;
    }

    public double getMin() {
        double min = data[0];
        for (double a : data) {
            min = (min < a) ? min : a;
        }
        return min;
    }

    public double getMax() {
        double max = data[0];
        for (double a : data) {
            max = (max > a) ? max : a;
        }
        return max;
    }

    public double getMean() {
        double sum = 0.0f;
        for (double a : data) {
            sum += a;
        }
        return sum / size;
    }

    public double getVariance() {
        double mean = getMean();
        double temp = 0f;
        for (double a : data) {
            temp += (mean - a) * (mean - a);
        }
        return temp / size;
    }

    public double getStdDev() {
        return Math.sqrt(getVariance());
    }
}
