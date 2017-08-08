package br.com.christian.mostramensagem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnVer;
    private EditText edtNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVer = (Button) findViewById(R.id.btnVer);
        edtNome = (EditText) findViewById(R.id.edtNome);

        btnVer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String nome = edtNome.getText().toString();
        Toast.makeText(this, "Olá "+nome, Toast.LENGTH_SHORT).show();
    }
}
