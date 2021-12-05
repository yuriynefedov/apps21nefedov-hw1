package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;
import org.junit.Test;

public class TempSummaryStatisticsTest {
    @Test
    public void testStatistics() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0, 2.5, 14.0, 21.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        TempSummaryStatistics expResult = new TempSummaryStatistics(9.125,
                8.820253681159063, -1.0, 21.0);

        // call tested method
        TempSummaryStatistics actualResult = seriesAnalysis.summaryStatistics();

        // compare expected result with actual result
        assertEquals(expResult.getAvgTemp(), actualResult.getAvgTemp(), 0.0001);
        assertEquals(expResult.getDevTemp(), actualResult.getDevTemp(), 0.0001);
        assertEquals(expResult.getMaxTemp(), actualResult.getMaxTemp(), 0.0001);
        assertEquals(expResult.getMinTemp(), actualResult.getMinTemp(), 0.0001);

    }
}
