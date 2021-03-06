package jajodia.aditya.com.tickernotify;

import android.animation.Animator;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

/**
 * Created by kunalsingh on 13/12/16.
 */

public class ButtonFragmentTwo extends Fragment {

    private static final String TAG = "ButtonFragmentTwo";

    PieChart pieChart;
    public ButtonFragmentTwo() {
    }
    String subject;
    int total,present;
    View view;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int cx = view.getWidth()/2;
        int cy = view.getHeight()/2;

        float finalRadius = (float) Math.hypot(cx,cy);

         Animator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils.createCircularReveal(view,cx,cy,0,finalRadius);
        }
        animator.start();
    }

    public void setSubject(String subject){
        this.subject = subject;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.button_fragment_two,container,false);

        TextView sub = (TextView)view.findViewById(R.id.tv_subject_two);
        TextView pre = (TextView)view.findViewById(R.id.tv_present_number);
        TextView tot = (TextView)view.findViewById(R.id.tv_total_number);
        RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.button_fragment);
        Log.d(TAG,"came in two");
        sub.setText(subject);

        pieChart = (PieChart)view.findViewById(R.id.pie_chart);

        pieChart.setRotationEnabled(true);

        pieChart.setHoleRadius(0);



        Cursor c = DatabaseOpenHelper.readData(getActivity(),subject);
        c.moveToFirst();
        Log.d(TAG,"s"+"  " +c.getColumnName(0)+" "+c.getColumnName(1)+" "+c.getColumnName(2)+" "+c.getColumnName(3));
        total = c.getInt(2);
        present = c.getInt(3);

        addDataSet();

        tot.setText(String.valueOf(c.getInt(2)));
        pre.setText(String.valueOf(c.getInt(3)));
        c.close();
        Log.d(TAG,"came in two");

        return view;
    }

    public void addDataSet(){
        int per;
        per=(int)((float)present/total*100);

        int abs=100-per;

        ArrayList<PieEntry> entry = new ArrayList<>();

        entry.add(new PieEntry(per));
        entry.add(new PieEntry(abs));
        PieDataSet pieDataSet = new PieDataSet(entry,"RECORD");

        pieDataSet.setValueTextSize(20);

        pieDataSet.setValueTextColor(Color.WHITE);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.parseColor("#006600"));
        colors.add(Color.parseColor("#CC0000"));

        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(5);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.setContentDescription("");
        Description description = pieChart.getDescription();
        description.setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        pieChart.invalidate();
    }
}

