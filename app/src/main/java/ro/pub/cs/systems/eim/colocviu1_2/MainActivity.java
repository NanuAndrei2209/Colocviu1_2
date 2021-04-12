package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> list = new ArrayList<>();
    boolean listModified = false;
    int localSum = -1;
    ServiceBroadcastReceiver sbr;
    IntentFilter serviceIntentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.editText);
        final TextView txtView = findViewById(R.id.txtView);
        final Button add = findViewById(R.id.addBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = editText.getText().toString();
                if (txtView.getText().toString().equals("")) {
                    txtView.setText(value);
                } else {
                    txtView.setText(txtView.getText() + " + " + value);
                }
                list.add(Integer.parseInt(value));
                listModified = true;
            }
        });

        final Button compute = findViewById(R.id.computeBtn);
        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listModified) {
                    Intent intent = new Intent("ro.pub.cs.systems.eim.secondaryactivity");
                    intent.putExtra("list", list);
                    startActivityForResult(intent, 5);
                    listModified = false;
                } else {
                    Toast.makeText(getApplication(), "localsum: " + localSum, Toast.LENGTH_LONG).show();
                }

            }
        });

        sbr = new ServiceBroadcastReceiver(getApplication());
        serviceIntentFilter = new IntentFilter();
        serviceIntentFilter.addAction("myAction");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Integer sum = data.getIntExtra("sum", -1);
            this.localSum = sum;
            Toast.makeText(getApplication(), "sum: " + sum, Toast.LENGTH_LONG).show();

            if (sum > 10) {
                System.out.println("From main: sum > 10");
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.colocviu1_2", "ro.pub.cs.systems.eim.colocviu1_2.Colocviu1_2Service"));
                intent.putExtra("sum", sum);
                startService(intent);
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        final EditText editText = findViewById(R.id.editText);
        final TextView txtView = findViewById(R.id.txtView);

        savedInstanceState.putString("editText", editText.getText().toString());
        savedInstanceState.putString("txtView", txtView.getText().toString());
        savedInstanceState.putIntegerArrayList("list", list);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey("editText")) {
            EditText editText = findViewById(R.id.editText);
            editText.setText(savedInstanceState.getString("editText"));
        }

        if (savedInstanceState.containsKey("txtView")) {
            TextView txtView = findViewById(R.id.txtView);
            txtView.setText(savedInstanceState.getString("txtView"));
        }

        if (savedInstanceState.containsKey("list")) {
            this.list = savedInstanceState.getIntegerArrayList("list");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(sbr, serviceIntentFilter);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.colocviu1_2", "ro.pub.cs.systems.eim.colocviu1_2.Colocviu1_2Service"));
        stopService(intent);
        super.onDestroy();
    }
}