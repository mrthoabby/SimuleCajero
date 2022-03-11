package com.MOVILES.simulecajero;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

TextView currentMoney,valueOperation;
Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnx,retire,consign;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        currentMoney = findViewById(R.id.txtCurrentMoeny);
        valueOperation = findViewById(R.id.txtvalueOperation);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn0 = findViewById(R.id.btn0);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnx = findViewById(R.id.btnx);
        retire = findViewById(R.id.retire);
        consign = findViewById(R.id.consign);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnx.setOnClickListener(this);
        retire.setOnClickListener(this);
        consign.setOnClickListener(this);

        setCurrentValue();

    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case (R.id.btn0):
             valueOperation.setText(addDisplayText(valueOperation,btn0));
            break;
        case (R.id.btn1):
            valueOperation.setText(addDisplayText(valueOperation,btn1));
            break;
        case(R.id.btn2):
            valueOperation.setText(addDisplayText(valueOperation,btn2));
            break;
        case(R.id.btn3):
            valueOperation.setText(addDisplayText(valueOperation,btn3));
            break;
        case(R.id.btn4):
            valueOperation.setText(addDisplayText(valueOperation,btn4));
            break;
        case(R.id.btn5):
            valueOperation.setText(addDisplayText(valueOperation,btn5));
            break;
        case(R.id.btn6):
            valueOperation.setText(addDisplayText(valueOperation,btn6));
            break;
        case(R.id.btn7):
            valueOperation.setText(addDisplayText(valueOperation,btn7));
            break;
        case(R.id.btn8):
            valueOperation.setText(addDisplayText(valueOperation,btn8));
            break;
        case(R.id.btn9):
            valueOperation.setText(addDisplayText(valueOperation,btn9));
            break;
        case(R.id.btnx):
            valueOperation.setText("");
            break;
        case(R.id.consign):

            if(addMonney()){
                setCurrentValue();
                valueOperation.setText("");
                Toast.makeText(getApplicationContext(),"Consiganción existosa",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Consiganción fallida",Toast.LENGTH_LONG).show();
            }

            break;
        case(R.id.retire):
            if(leesMoney()){
                setCurrentValue();
                valueOperation.setText("");
                Toast.makeText(getApplicationContext(),"Retiro existoso",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"No puedes retirar está cantidad",Toast.LENGTH_LONG).show();
            }

        default:
            break;


    }

    }

    private String addDisplayText(TextView currentText, Button addText){
    return  currentText.getText().toString().equals("0.0") ? addText.getText().toString(): currentText.getText() + addText.getText().toString();
    }

    private void setCurrentValue(){
        AdminDb admin = new AdminDb(this,"dataBase",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();
        Cursor result = database.rawQuery("SELECT currentMoney FROM currentMoney",null);
        if(result.moveToFirst()){
            currentMoney.setText(String.valueOf(result.getDouble(0)));
        }
        else{
            currentMoney.setText("0");
        }
    }


    private boolean addMonney(){
        if(valueOperation.getText().toString().equals(null) || valueOperation.getText().toString().equals("")){
            return  false;
        }
        AdminDb admin = new AdminDb(this,"dataBase",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();
        double addMoney = getDoubleFromTextView(valueOperation);
        ContentValues newValue = new ContentValues();
        newValue.put("id",1);
        Cursor result = database.rawQuery("SELECT currentMoney FROM currentMoney",null);
        if(result.moveToFirst()){
            double currentMoney = result.getDouble(0);
            newValue.put("currentMoney",addMoney+currentMoney);
            database.update("currentMoney",newValue,"id = 1",null);
            return true;
       }
       else{
           database.insert("currentMoney",null,newValue);
           return true;
       }
    }

    private boolean leesMoney(){
        if(valueOperation.getText().toString().equals(null) || valueOperation.getText().toString().equals("")){
            return  false;
        }
        AdminDb admin = new AdminDb(this,"dataBase",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();
        double addMoney = getDoubleFromTextView(valueOperation);
        ContentValues newValue = new ContentValues();
        newValue.put("id",1);
        Cursor result = database.rawQuery("SELECT currentMoney FROM currentMoney",null);
        if(result.moveToFirst()){
            double currentMoney = result.getDouble(0);
            if(currentMoney - addMoney < 0){
                return false;
            }
            else{
                newValue.put("currentMoney",currentMoney-addMoney);
                database.update("currentMoney",newValue,"id = 1",null);
                return true;
            }

        }
        else{
            newValue.put("currentMoney",0);
            database.insert("currentMoney",null,newValue);
            return true;
        }
    }

    private double getDoubleFromTextView(TextView text){
        return  Double.parseDouble(text.getText().toString());
    }
}