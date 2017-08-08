package br.com.christian.contagemregressiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnContar;
    private TextView txtNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnContar = (Button) findViewById(R.id.btnContar);
        txtNum = (TextView) findViewById(R.id.txtNumero);

        btnContar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Conta task = new Conta(btnContar, txtNum);
        task.execute(10);
    }
}
