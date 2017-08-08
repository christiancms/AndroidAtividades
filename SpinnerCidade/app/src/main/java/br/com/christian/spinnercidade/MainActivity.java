package br.com.christian.spinnercidade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayAdapter<String> adpCidades;
    private Spinner spnCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnCidade = (Spinner) findViewById(R.id.spnCidade);

        adpCidades = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        adpCidades.add("Pelotas");
        adpCidades.add("Rio Grande");
        adpCidades.add("Porto Alegre");
        adpCidades.add("Caxias do Sul");

        spnCidade.setAdapter(adpCidades);
        adpCidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnCidade.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
        String cidade = adpCidades.getItem(i);
        Toast.makeText(this, "Cidade Selecionada: "+cidade, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView){}
}
