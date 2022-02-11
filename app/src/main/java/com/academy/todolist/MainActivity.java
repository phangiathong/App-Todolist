package com.academy.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database=new Database(this,"ghichu.sqlite",null,1);
    ListView lvCongViec;
    ArrayList<CongViec> congViecArrayList;
    CongViecAdapter congViecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCongViec=findViewById(R.id.lvCongViec);
        congViecArrayList=new ArrayList<>();
        congViecAdapter=new CongViecAdapter(this,R.layout.dong_cong_viec,congViecArrayList);
        lvCongViec.setAdapter(congViecAdapter);

        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCv VARCHAR(200))");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'Làm bài tập android.')");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'Đi đá bóng.')");

        getDataCongViec();
    }

    public void DialogXoaCv(String ten, int id){
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa công việc "+ten+" này không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec where Id='"+id+"'");
                Toast.makeText(getApplicationContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                getDataCongViec();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogXoa.show();
    }

    public void DiaLogSua(String ten, int id){
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_cong_viec);
        EditText edtSua=dialog.findViewById(R.id.edtSuaCv);
        Button btnXacNhan=dialog.findViewById(R.id.btnXacNhan);
        Button btnHuy=dialog.findViewById(R.id.btnHuySua);

        edtSua.setText(ten);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi=edtSua.getText().toString();
                database.QueryData("UPDATE CongViec SET TenCv='"+tenMoi+"' WHERE Id='"+id+"'");
                Toast.makeText(getApplicationContext(), "Đã cập nhật.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataCongViec();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DiaLogThem() {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.diglog_them_cong_viec);

        EditText edtThem=dialog.findViewById(R.id.edtThem);
        Button btnThem=dialog.findViewById(R.id.btnThem);
        Button btnHuy=dialog.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCv=edtThem.getText().toString();
                if (tenCv.equals("")){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO CongViec VALUES(null,'"+tenCv+"')");
                    Toast.makeText(getApplicationContext(), "Đã thêm.", Toast.LENGTH_SHORT).show();
                    getDataCongViec();
                }
                dialog.dismiss();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getDataCongViec(){
        Cursor dataCongViec=database.GetData("SELECT * FROM CongViec");
        congViecArrayList.clear();
        while (dataCongViec.moveToNext()) {
            int id=dataCongViec.getInt(0);
            String ten=dataCongViec.getString(1);

            congViecArrayList.add(new CongViec(id,ten));
        }
        congViecAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            DiaLogThem();
        }
        return super.onOptionsItemSelected(item);
    }
}