package com.example.aitor.chooser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity {
    private  EditText tv;
    private Uri url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url=getIntent().getData();
        leer(url);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.guardar) {
            guardar();

        }

        return super.onOptionsItemSelected(item);
    }
//leo el archivo
    public void leer(Uri ruta){
        tv=(EditText)findViewById(R.id.texto);
        if(isModificable() && isLegible()) {
            try {
                File f = new File(ruta.getPath());
                BufferedReader br = new BufferedReader(new FileReader(f));
                String linea, valor = "";
                while ((linea = br.readLine()) != null) {
                    valor += linea + "\n";
                }
                br.close();
                tv.setText(valor);

            } catch (Exception ex) {
                Toast.makeText(this,"Error al leer fichero",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Para leer este archivo debes ser ROOT", Toast.LENGTH_SHORT).show();
        }
    }
    //guardo el archivo
    public void guardar(){
        if(isLegible() && isModificable()) {
            try {
                File f = new File(url.getPath());
                OutputStreamWriter fout =new OutputStreamWriter(new FileOutputStream(f));
                fout.write(tv.getText().toString());
                fout.close();
                Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
               Toast.makeText(this,"Error al escribir fichero",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Para escribir este archivo debes ser ROOT", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo para ver si se puede leer la memoria
    public boolean isLegible() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    //metodo para ver si se puede escribir la memoria
    public boolean isModificable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
