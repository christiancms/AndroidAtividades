package br.com.christian.listadecompras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private EditText edtProduto;
    private EditText edtLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtProduto = (EditText) findViewById(R.id.edtProduto);
        edtLista = (EditText) findViewById(R.id.edtLista);
    }

    public void Adiciona(View view) throws FileNotFoundException {

        String produto = edtProduto.getText().toString();

        try {
            FileOutputStream fos = openFileOutput("compras.txt", MODE_APPEND);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(produto);
            pw.close();
            Toast.makeText(this, "Ok! Produto Cadastrado " + edtProduto.getText(), Toast.LENGTH_SHORT).show();
            edtProduto.setText(null);
            edtProduto.requestFocus();
        } catch (IOException e) {
            Toast.makeText(this, "Erro..." + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Lista(View view) throws FileNotFoundException {

        try {
            FileInputStream fis = openFileInput("compras.txt");

            Scanner entrada = new Scanner(fis);
            StringBuilder linhas = new StringBuilder();

            while (entrada.hasNextLine()) {
                String linha = entrada.nextLine();
                linhas.append(linha + System.lineSeparator());
            }
            edtLista.setText(linhas);
        } catch (IOException e) {
            Toast.makeText(this, "Erro..." + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
