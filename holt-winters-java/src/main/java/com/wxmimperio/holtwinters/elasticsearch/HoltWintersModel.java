package com.wxmimperio.holtwinters.elasticsearch;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className HoltWintersModel.java
 * @description This is the description of HoltWintersModel.java
 * @createTime 2021-01-12 12:02:00
 */
public class HoltWintersModel {
    public static final String NAME = "holt_winters";

    public static final double DEFAULT_ALPHA = 0.3;
    public static final double DEFAULT_BETA = 0.1;
    public static final double DEFAULT_GAMMA = 0.3;
    public static final int DEFAULT_PERIOD = 1;
    public static final SeasonalityType DEFAULT_SEASONALITY_TYPE = SeasonalityType.ADDITIVE;
    public static final boolean DEFAULT_PAD = false;

    public enum SeasonalityType {
        ADDITIVE((byte) 0, "add"), MULTIPLICATIVE((byte) 1, "mult");

        private final byte id;
        private final String name;

        SeasonalityType(byte id, String name) {
            this.id = id;
            this.name = name;
        }


        public byte getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Controls smoothing of data.  Also known as "level" value.
     * Alpha = 1 retains no memory of past values
     * (e.g. random walk), while alpha = 0 retains infinite memory of past values (e.g.
     * mean of the series).
     */
    private final double alpha;

    /**
     * Controls smoothing of trend.
     * Beta = 1 retains no memory of past values
     * (e.g. random walk), while alpha = 0 retains infinite memory of past values (e.g.
     * mean of the series).
     */
    private final double beta;

    /**
     * Controls smoothing of seasonality.
     * Gamma = 1 retains no memory of past values
     * (e.g. random walk), while alpha = 0 retains infinite memory of past values (e.g.
     * mean of the series).
     */
    private final double gamma;

    /**
     * Periodicity of the data
     */
    private final int period;

    /**
     * Whether this is a multiplicative or additive HW
     */
    private final SeasonalityType seasonalityType;

    /**
     * Padding is used to add a very small amount to values, so that zeroes do not interfere
     * with multiplicative seasonality math (e.g. division by zero)
     */
    private final boolean pad;
    private final double padding;

    public HoltWintersModel() {
        this(DEFAULT_ALPHA, DEFAULT_BETA, DEFAULT_GAMMA, DEFAULT_PERIOD, DEFAULT_SEASONALITY_TYPE, DEFAULT_PAD);
    }

    public HoltWintersModel(double alpha, double beta, double gamma, int period, SeasonalityType seasonalityType, boolean pad) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.period = period;
        this.seasonalityType = seasonalityType;
        this.pad = pad;
        this.padding = inferPadding();
    }

    /**
     * Only pad if we are multiplicative and padding is enabled. the padding amount is not currently user-configurable.
     */
    private double inferPadding() {
        return seasonalityType.equals(SeasonalityType.MULTIPLICATIVE) && pad ? 0.0000000001 : 0;
    }

    /**
     * Predicts the next `n` values in the series, using the smoothing model to generate new values.
     * Unlike the other moving averages, HoltWinters has forecasting/prediction built into the algorithm.
     * Prediction is more than simply adding the next prediction to the window and repeating.  HoltWinters
     * will extrapolate into the future by applying the trend and seasonal information to the smoothed data.
     *
     * @param values         Collection of numerics to movingAvg, usually windowed
     * @param numPredictions Number of newly generated predictions to return
     * @return Returns an array of doubles, since most smoothing methods operate on floating points
     */
    public double[] doPredict(Collection<Double> values, int numPredictions) {
        return next(values, numPredictions);
    }

    public double next(Collection<Double> values) {
        return next(values, 1)[0];
    }

    /**
     * Calculate a doubly exponential weighted moving average
     *
     * @param values       Collection of values to calculate avg for
     * @param numForecasts number of forecasts into the future to return
     * @return Returns a Double containing the moving avg for the window
     */
    public double[] next(Collection<Double> values, int numForecasts) {
        return MovingFunctions.holtWintersForecast(values.stream().mapToDouble(Double::doubleValue).toArray(),
                alpha, beta, gamma, period, padding, seasonalityType.equals(SeasonalityType.MULTIPLICATIVE), numForecasts);
    }

}
