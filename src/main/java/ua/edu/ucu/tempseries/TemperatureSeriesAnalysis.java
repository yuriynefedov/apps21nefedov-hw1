package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    static final double MIN_TEMPERATURE = -273.0;
    private double[] temperatureSeries;

    public TemperatureSeriesAnalysis() { }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        if (!checkValidity(temperatureSeries)) {
            throw new InputMismatchException();
        }
        this.temperatureSeries = temperatureSeries;
    }

    private boolean checkValidity(double[] series) {
        for (double value : series) {
            if (value < MIN_TEMPERATURE) {
                return false;
            }
        }
        return true;
    }

    public double sum() {
        double result = 0.0;
        for (double value: temperatureSeries) {
            result += value;
        }
        return result;
    }

    private void throwExceptionIfRowEmpty() {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
    }

    public double average() {
        throwExceptionIfRowEmpty();
        return this.sum() / this.temperatureSeries.length;
    }

    public double deviation() {
        throwExceptionIfRowEmpty();
        double result = 0.0;
        double avg = average();
        for (double value: temperatureSeries) {
            result += (value - avg) * (value - avg);
        }
        result = result / temperatureSeries.length;
        return Math.sqrt(result);
    }

    public double min() {
        throwExceptionIfRowEmpty();
        double min = temperatureSeries[0];
        for (double value: temperatureSeries) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public double max() {
        throwExceptionIfRowEmpty();
        double max = temperatureSeries[0];
        for (double value: temperatureSeries) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public double findTempClosestToZero() {
        throwExceptionIfRowEmpty();
        double minDiff = Math.abs(temperatureSeries[0]);
        double result = temperatureSeries[0];
        for (double value: temperatureSeries) {
            double newMinDiff = Math.abs(value);
            if ((newMinDiff < minDiff)
                    || ((newMinDiff == minDiff) && (value > result))) {
                result = value;
            }
        }
        return result;
    }

    public double findTempClosestToValue(double tempValue) {
        throwExceptionIfRowEmpty();
        double minDiff = Math.abs(temperatureSeries[0] - tempValue);
        double result = temperatureSeries[0];
        for (double value: temperatureSeries) {
            double newMinDiff = Math.abs(value - tempValue);
            if ((newMinDiff < minDiff)
                    || ((newMinDiff == minDiff) && (value > result))) {
                result = value;
            }
        }
        return result;
    }

    public double[] findTempsLessThen(double tempValue) {
        int currentLength = 0;
        double[] result = new double[currentLength];
        for (double value: temperatureSeries) {
            if (value < tempValue) {
                currentLength += 1;
                double[] newResult = new double[currentLength];
                System.arraycopy(result, 0, newResult, 0, result.length);
                newResult[currentLength-1] = value;
                result = newResult;
            }
        }
        return result;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        int currentLength = 0;
        double[] result = new double[currentLength];
        for (double value: temperatureSeries) {
            if (value > tempValue) {
                currentLength += 1;
                double[] newResult = new double[currentLength];
                System.arraycopy(result, 0, newResult, 0, result.length);
                newResult[currentLength-1] = value;
                result = newResult;
            }
        }
        return result;
    }

    public TempSummaryStatistics summaryStatistics() {
        throwExceptionIfRowEmpty();
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        for (double tempValue: temps) {
            doubleUpIfNecessary();
            for (int i = 0; i < temperatureSeries.length; i++) {
                if (temperatureSeries[i] == Double.MAX_VALUE) {
                    temperatureSeries[i] = tempValue;
                    break;
                }
            }
        }
        return temperatureSeries.length;
    }

    private void doubleUpIfNecessary() {
        if (!hasEmptyPositions()) {
            doubleUpStorage();
        }
    }

    private boolean hasEmptyPositions() {
        for (double value : temperatureSeries) {
            if (value == Double.MAX_VALUE) {
                return true;
            }
        }
        return false;
    }

    private void doubleUpStorage() {
        temperatureSeries = Arrays.copyOf(temperatureSeries,
                temperatureSeries.length * 2);
    }
}
