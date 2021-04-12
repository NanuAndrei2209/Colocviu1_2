package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class Colocviu1_2SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_2_secondary);

        final Intent intent = getIntent();
        int sum = -1;
        if (intent != null) {
            ArrayList<Integer> list = intent.getIntegerArrayListExtra("list");
            if (list != null) {
                sum = 0;
                for (int nr : list) {
                    sum += nr;
                }
            }
        }
        Intent result = new Intent();
        result.putExtra("sum", sum);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}