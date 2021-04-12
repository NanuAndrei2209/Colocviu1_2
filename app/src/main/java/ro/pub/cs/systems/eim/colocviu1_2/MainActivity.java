package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> list = new ArrayList<>();

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
            }
        });

        final Button compute = findViewById(R.id.computeBtn);
        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("ro.pub.cs.systems.eim.secondaryactivity");
                intent.putExtra("list", list);
                startActivityForResult(intent, 5);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Integer sum = data.getIntExtra("sum", -1);
            Toast.makeText(getApplication(), "sum: " + sum, Toast.LENGTH_LONG).show();
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
}