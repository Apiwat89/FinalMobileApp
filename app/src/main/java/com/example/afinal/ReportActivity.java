package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class ReportActivity extends AppCompatActivity {
    private ListView listView;
    private TextView TextShowTotal;
    private Button btnBack;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = new Database(this);

        listView = findViewById(R.id.listView);
        TextShowTotal = findViewById(R.id.TextShowTotal);
        btnBack = findViewById(R.id.btnBack);

        createListview();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportActivity.this, MenuActivity.class));
                finish();
            }
        });
    }

    private void createListview() {
        Cursor res = database.getAll();

        if (res != null) {
            String [] Drink = new String[res.getCount()];
            String [] Value = new String[res.getCount()];
            Integer [] Logo = new Integer[res.getCount()];
            int [] price = new int[res.getCount()];
            int totalDrink = 0;

            for (int n = 0; n < res.getCount(); n++) {
                res.moveToNext();
                Drink[n] = res.getString(1);
                Value[n] = res.getString(2);
                Logo[n] = checkLogoDrink(res.getString(1));
                price[n] = checkPriceDrink(res.getString(1));
                totalDrink += price[n] * Integer.parseInt(Value[n]);
            }

            CustomListView adap = new CustomListView(this, Drink, Value, Logo, price);
            listView.setAdapter(adap);
            TextShowTotal.setText("ราคารวมทั้งหมด: " + totalDrink + " บาท");
        }
    }

    private int checkPriceDrink(String string) {
        if (string.equals("อเมริกาโน่เย็น")) return 45;
        else if (string.equals("ลาเต้เย็น")) return 40;
        else if (string.equals("คาปูชิโน่เย็น")) return 45;
        else if (string.equals("มอคค่าเย็น")) return 40;
        else if (string.equals("ชาเขียวมัทฉะเย็น")) return 35;
        else if (string.equals("ชานมไข่มุกเย็น")) return 35;
        else if (string.equals("น้ำผึ้งมะนาวเย็น")) return 45;
        return 0;
    }

    private Integer checkLogoDrink(String string) {
        if (string.equals("อเมริกาโน่เย็น")) return R.drawable.item1;
        else if (string.equals("ลาเต้เย็น")) return R.drawable.item2;
        else if (string.equals("คาปูชิโน่เย็น")) return R.drawable.item3;
        else if (string.equals("มอคค่าเย็น")) return R.drawable.item4;
        else if (string.equals("ชาเขียวมัทฉะเย็น")) return R.drawable.item5;
        else if (string.equals("ชานมไข่มุกเย็น")) return R.drawable.item6;
        else if (string.equals("น้ำผึ้งมะนาวเย็น")) return R.drawable.item7;
        return 0;
    }
}

class CustomListView extends ArrayAdapter<String> {
    private Context context;
    private String[] Drink;
    private String[] Value;
    private Integer[] Logo;
    private int[] price;

    public CustomListView(@NonNull Context context, String[] Drink, String[] Value, Integer[] Logo, int[] price) {
        super(context, R.layout.listview, Drink);
        this.context = context;
        this.Drink = Drink;
        this.Value = Value;
        this.Logo = Logo;
        this.price = price;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.listview, null, true);

        TextView Text1 = rowView.findViewById(R.id.Text1);
        TextView Text2 = rowView.findViewById(R.id.Text2);
        ImageView Image = rowView.findViewById(R.id.Image);

        int total = price[position] * Integer.parseInt(Value[position]);
        Text1.setText("เครื่องดื่ม " + Drink[position] + " จำนวน " + Value[position] + " แก้ว");
        Text2.setText("แก้วละ " + price[position] + " บาท รวมทั้งหมดเป็น " + total + " บาท");
        Image.setImageResource(Logo[position]);

        return rowView;
    }
}