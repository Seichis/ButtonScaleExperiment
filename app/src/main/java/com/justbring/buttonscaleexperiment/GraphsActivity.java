package com.justbring.buttonscaleexperiment;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GraphsActivity extends Activity implements OnChartValueSelectedListener, OnChartGestureListener {
    @Bind(R.id.barChart)
    BarChart barChart;
    @Bind(R.id.lineChart)
    LineChart lineChart;
    @Bind(R.id.radarChart)
    RadarChart radarChart;
    @Bind(R.id.bubbleChart)
    BubbleChart bubbleChart;
    @Bind(R.id.pieChart)
    PieChart pieChart;
    @Bind(R.id.candleStickChart)
    CandleStickChart candleStickChart;
    @Bind(R.id.scatterChart)
    ScatterChart scatterChart;
    List<Chart> myCharts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        ButterKnife.bind(this);

        myCharts = new ArrayList<>();
        myCharts.add(barChart);
        myCharts.add(lineChart);
        myCharts.add(candleStickChart);
        myCharts.add(scatterChart);
        myCharts.add(radarChart);
        myCharts.add(pieChart);
        myCharts.add(bubbleChart);

        for (Chart c : myCharts) {
            c.setOnChartValueSelectedListener(this);
            c.setOnChartGestureListener(this);
            c.animateXY(1000, 1000);
        }

        fillLineChart();
        fillBarChart();
        fillBubbleChart();
        fillCandleChart();
//        fillPieChart();
        fillRadarChart();
        fillScatterChart();
    }

    private void fillLineChart() {
        ArrayList<Entry> valsComp1 = new ArrayList<>();
        ArrayList<Entry> valsComp2 = new ArrayList<>();

        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        Entry c1e3 = new Entry(100.000f, 2); // 2 == quarter 3
        valsComp1.add(c1e3);
        Entry c1e4 = new Entry(50.000f, 3); // 3 == quarter 4 ...
        valsComp1.add(c1e4);

        Entry c2e1 = new Entry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        Entry c2e3 = new Entry(120.000f, 2); // 2 == quarter 3
        valsComp2.add(c2e3);
        Entry c2e4 = new Entry(110.000f, 3); // 3 == quarter 4 ...
        valsComp2.add(c2e4);

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setCircleColors(ColorTemplate.LIBERTY_COLORS);
        setComp1.setColors(ColorTemplate.LIBERTY_COLORS);
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");
        setComp2.setColors(ColorTemplate.VORDIPLOM_COLORS);
        setComp1.setCircleColors(ColorTemplate.VORDIPLOM_COLORS);

        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        // use the interface ILineDataSet
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("2.Q");
        xVals.add("3.Q");
        xVals.add("4.Q");

        LineData data = new LineData(xVals, dataSets);
        lineChart.setData(data);

        lineChart.invalidate(); // refresh
    }

    private void fillBarChart() {
        ArrayList<BarEntry> valsComp1 = new ArrayList<>();
        ArrayList<BarEntry> valsComp2 = new ArrayList<>();

        BarEntry c1e1 = new BarEntry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        BarEntry c1e2 = new BarEntry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        BarEntry c1e3 = new BarEntry(100.000f, 2); // 2 == quarter 3
        valsComp1.add(c1e3);
        BarEntry c1e4 = new BarEntry(50.000f, 3); // 3 == quarter 4 ...
        valsComp1.add(c1e4);

        BarEntry c2e1 = new BarEntry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        BarEntry c2e2 = new BarEntry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        BarEntry c2e3 = new BarEntry(120.000f, 2); // 2 == quarter 3
        valsComp2.add(c2e3);
        BarEntry c2e4 = new BarEntry(110.000f, 3); // 3 == quarter 4 ...
        valsComp2.add(c2e4);

        BarDataSet setComp1 = new BarDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColors(ColorTemplate.LIBERTY_COLORS);
        BarDataSet setComp2 = new BarDataSet(valsComp2, "Company 2");
        setComp2.setColors(ColorTemplate.VORDIPLOM_COLORS);

        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        // use the interface IBarDataSet
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("2.Q");
        xVals.add("3.Q");
        xVals.add("4.Q");

        BarData data = new BarData(xVals, dataSets);
        barChart.setData(data);

        barChart.invalidate(); // refresh
    }

    private void fillRadarChart() {
        ArrayList<Entry> valsComp1 = new ArrayList<>();
        ArrayList<Entry> valsComp2 = new ArrayList<>();

        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        Entry c1e3 = new Entry(100.000f, 2); // 2 == quarter 3
        valsComp1.add(c1e3);
        Entry c1e4 = new Entry(50.000f, 3); // 3 == quarter 4 ...
        valsComp1.add(c1e4);

        Entry c2e1 = new Entry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        Entry c2e3 = new Entry(120.000f, 2); // 2 == quarter 3
        valsComp2.add(c2e3);
        Entry c2e4 = new Entry(110.000f, 3); // 3 == quarter 4 ...
        valsComp2.add(c2e4);

        RadarDataSet setComp1 = new RadarDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColors(ColorTemplate.LIBERTY_COLORS);
        RadarDataSet setComp2 = new RadarDataSet(valsComp2, "Company 2");
        setComp2.setColors(ColorTemplate.VORDIPLOM_COLORS);

        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        // use the interface IRadarDataSet
        ArrayList<IRadarDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("2.Q");
        xVals.add("3.Q");
        xVals.add("4.Q");

        RadarData data = new RadarData(xVals, dataSets);
        radarChart.setData(data);

        radarChart.invalidate(); // refresh
    }

    private void fillScatterChart() {
        ArrayList<Entry> valsComp1 = new ArrayList<>();
        ArrayList<Entry> valsComp2 = new ArrayList<>();

        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        Entry c1e3 = new Entry(100.000f, 2); // 2 == quarter 3
        valsComp1.add(c1e3);
        Entry c1e4 = new Entry(50.000f, 3); // 3 == quarter 4 ...
        valsComp1.add(c1e4);

        Entry c2e1 = new Entry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        Entry c2e3 = new Entry(120.000f, 2); // 2 == quarter 3
        valsComp2.add(c2e3);
        Entry c2e4 = new Entry(110.000f, 3); // 3 == quarter 4 ...
        valsComp2.add(c2e4);

        ScatterDataSet setComp1 = new ScatterDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColors(ColorTemplate.LIBERTY_COLORS);
        ScatterDataSet setComp2 = new ScatterDataSet(valsComp2, "Company 2");
        setComp2.setColors(ColorTemplate.VORDIPLOM_COLORS);

        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        // use the interface IScatterDataSet
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("2.Q");
        xVals.add("3.Q");
        xVals.add("4.Q");

        ScatterData data = new ScatterData(xVals, dataSets);
        scatterChart.setData(data);

        scatterChart.invalidate(); // refresh
    }

    private void fillBubbleChart() {
        ArrayList<BubbleEntry> valsComp1 = new ArrayList<>();
        ArrayList<BubbleEntry> valsComp2 = new ArrayList<>();

        BubbleEntry c1e1 = new BubbleEntry(0, 100.000f, 100.000f); // 0 == quarter 1
        valsComp1.add(c1e1);
        BubbleEntry c1e2 = new BubbleEntry(1, 50.000f, 50.000f); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        BubbleEntry c1e3 = new BubbleEntry(2, 100.000f, 100.000f); // 2 == quarter 3
        valsComp1.add(c1e3);
        BubbleEntry c1e4 = new BubbleEntry(3, 50.000f, 50.000f); // 3 == quarter 4 ...
        valsComp1.add(c1e4);

        BubbleEntry c2e1 = new BubbleEntry(0, 120.000f, 120.000f); // 0 == quarter 1
        valsComp2.add(c2e1);
        BubbleEntry c2e2 = new BubbleEntry(1, 110.000f, 110.000f); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        BubbleEntry c2e3 = new BubbleEntry(2, 120.000f, 120.000f); // 2 == quarter 3
        valsComp2.add(c2e3);
        BubbleEntry c2e4 = new BubbleEntry(3, 110.000f, 110.000f); // 3 == quarter 4 ...
        valsComp2.add(c2e4);

        BubbleDataSet setComp1 = new BubbleDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColors(ColorTemplate.LIBERTY_COLORS);
        BubbleDataSet setComp2 = new BubbleDataSet(valsComp2, "Company 2");
        setComp2.setColors(ColorTemplate.VORDIPLOM_COLORS);

        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        // use the interface IBubbleDataSet
        ArrayList<IBubbleDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("2.Q");
        xVals.add("3.Q");
        xVals.add("4.Q");

        BubbleData data = new BubbleData(xVals, dataSets);
        bubbleChart.setData(data);

        bubbleChart.invalidate(); // refresh
    }

    private void fillCandleChart() {
        ArrayList<CandleEntry> valsComp1 = new ArrayList<>();
        ArrayList<CandleEntry> valsComp2 = new ArrayList<>();

        CandleEntry c1e1 = new CandleEntry(0, 100.000f, 100.000f, 100.000f, 100.000f); // 0 == quarter 1
        valsComp1.add(c1e1);
        CandleEntry c1e2 = new CandleEntry(1, 50.000f, 50.000f, 50.000f, 50.000f); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        CandleEntry c1e3 = new CandleEntry(2, 100.000f, 100.000f, 100.000f, 100.000f); // 2 == quarter 3
        valsComp1.add(c1e3);
        CandleEntry c1e4 = new CandleEntry(3, 50.000f, 50.000f, 50.000f, 50.000f); // 3 == quarter 4 ...
        valsComp1.add(c1e4);

        CandleEntry c2e1 = new CandleEntry(0, 120.000f, 120.000f, 120.000f, 120.000f); // 0 == quarter 1
        valsComp2.add(c2e1);
        CandleEntry c2e2 = new CandleEntry(1, 110.000f, 110.000f, 110.000f, 110.000f); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        CandleEntry c2e3 = new CandleEntry(2, 120.000f, 120.000f, 120.000f, 120.000f); // 2 == quarter 3
        valsComp2.add(c2e3);
        CandleEntry c2e4 = new CandleEntry(3, 110.000f, 110.000f, 110.000f, 110.000f); // 3 == quarter 4 ...
        valsComp2.add(c2e4);

        CandleDataSet setComp1 = new CandleDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColors(ColorTemplate.LIBERTY_COLORS);
        CandleDataSet setComp2 = new CandleDataSet(valsComp2, "Company 2");
        setComp2.setColors(ColorTemplate.VORDIPLOM_COLORS);

        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        // use the interface ICandleDataSet
        ArrayList<ICandleDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("2.Q");
        xVals.add("3.Q");
        xVals.add("4.Q");

        CandleData data = new CandleData(xVals, dataSets);
        candleStickChart.setData(data);

        candleStickChart.invalidate(); // refresh
    }

    private void fillPieChart() {
        ArrayList<Entry> valsComp1 = new ArrayList<>();
        ArrayList<Entry> valsComp2 = new ArrayList<>();

        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        Entry c1e3 = new Entry(100.000f, 2); // 2 == quarter 3
        valsComp1.add(c1e3);
        Entry c1e4 = new Entry(50.000f, 3); // 3 == quarter 4 ...
        valsComp1.add(c1e4);

        Entry c2e1 = new Entry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        Entry c2e3 = new Entry(120.000f, 2); // 2 == quarter 3
        valsComp2.add(c2e3);
        Entry c2e4 = new Entry(110.000f, 3); // 3 == quarter 4 ...
        valsComp2.add(c2e4);

        PieDataSet setComp1 = new PieDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColors(ColorTemplate.LIBERTY_COLORS);
        PieDataSet setComp2 = new PieDataSet(valsComp2, "Company 2");
        setComp2.setColors(ColorTemplate.VORDIPLOM_COLORS);

        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        // use the interface IPieDataSet
        ArrayList<IPieDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("2.Q");
        xVals.add("3.Q");
        xVals.add("4.Q");

        PieData data = new PieData(xVals, (IPieDataSet) dataSets);
        pieChart.setData(data);

        pieChart.invalidate(); // refresh
    }


    // Inteface implementation
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
