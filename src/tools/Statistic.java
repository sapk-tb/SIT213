package tools;

/**
 * Class servant à faire des statistiques
 * source principale : http://stackoverflow.com/questions/7988486/how-do-you-calculate-the-variance-median-and-standard-deviation-in-c-or-java
 * @author
 */
public class Statistic {

    Float[] data;
    int size;

    public Statistic(Float[] data) {
        this.data = data;
        size = data.length;
    }

    public Float getMin() {
        Float min = data[0];
        for (Float a : data) {
            min = (min < a) ? min : a;
        }
        return min;
    }

    public Float getMax() {
        Float max = data[0];
        for (Float a : data) {
            max = (max > a) ? max : a;
        }
        return max;
    }

    public Float getMean() {
        Float sum = 0.0f;
        for (Float a : data) {
            sum += a;
        }
        return sum / size;
    }

    public Float getVariance() {
        Float mean = getMean();
        Float temp = 0f;
        for (Float a : data) {
            temp += (mean - a) * (mean - a);
        }
        return temp / size;
    }

    public Float getStdDev() {
        return (float) Math.sqrt(getVariance());
    }
}
