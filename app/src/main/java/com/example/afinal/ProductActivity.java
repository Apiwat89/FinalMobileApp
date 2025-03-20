package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class ProductActivity extends AppCompatActivity {
    private TextView TextPopupDrink, TextPopupValue, TextShowlist;
    private Button btnAdd, btnBack;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = new Database(this);

        TextPopupDrink = findViewById(R.id.TextPopupDrink);
        TextPopupValue = findViewById(R.id.TextPopupValue);
        TextShowlist = findViewById(R.id.TextShowlist);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        connectDatabase("LIST");

        TextPopupDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ProductActivity.this, view);
                popupMenu.setOnMenuItemClickListener(new MyPopupInflater());
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_drink, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        TextPopupValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ProductActivity.this, view);
                popupMenu.setOnMenuItemClickListener(new MyPopupInflater());
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_value, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectDatabase("ADD");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductActivity.this, MenuActivity.class));
                finish();
            }
        });
    }

    private void connectDatabase(String val) {
        if (val.equals("ADD")) {
            String drinkStr = TextPopupDrink.getText().toString();
            String valueStr = TextPopupValue.getText().toString();
            if (!drinkStr.equals("Drink") && !valueStr.equals("0")) {
                boolean res = database.insertData(drinkStr, valueStr);
                if (res) {
                    Toast.makeText(this, "สั่งเครื่องดื่ม " + drinkStr + " จำนวน " + valueStr + " แก้ว เรียบร้อย", Toast.LENGTH_LONG).show();
                    connectDatabase("LIST");
                } else {
                    Toast.makeText(this, "สั่งไม่สำเร็จ", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "กรุณาเลือกเครื่องดื่ม หรือ จำนวน", Toast.LENGTH_LONG).show();
            }
        } else if (val.equals("LIST")) {
            int res = database.getCountList();
            TextShowlist.setText("จำนวนที่สั่ง " + String.valueOf(res) + " รายการ");
        }
    }

    private class MyPopupInflater implements PopupMenu.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.Item1) TextPopupDrink.setText("อเมริกาโน่เย็น");
            else if (menuItem.getItemId() == R.id.Item2) TextPopupDrink.setText("ลาเต้เย็น");
            else if (menuItem.getItemId() == R.id.Item3) TextPopupDrink.setText("คาปูชิโน่เย็น");
            else if (menuItem.getItemId() == R.id.Item4) TextPopupDrink.setText("มอคค่าเย็น");
            else if (menuItem.getItemId() == R.id.Item5) TextPopupDrink.setText("ชาเขียวมัทฉะเย็น");
            else if (menuItem.getItemId() == R.id.Item6) TextPopupDrink.setText("ชานมไข่มุกเย็น");
            else if (menuItem.getItemId() == R.id.Item7) TextPopupDrink.setText("น้ำผึ้งมะนาวเย็น");

            if (menuItem.getItemId() == R.id.value1) TextPopupValue.setText("1");
            else if (menuItem.getItemId() == R.id.value2) TextPopupValue.setText("2");
            else if (menuItem.getItemId() == R.id.value3) TextPopupValue.setText("3");
            else if (menuItem.getItemId() == R.id.value4) TextPopupValue.setText("4");
            else if (menuItem.getItemId() == R.id.value5) TextPopupValue.setText("5");
            return false;
        }
    }
}