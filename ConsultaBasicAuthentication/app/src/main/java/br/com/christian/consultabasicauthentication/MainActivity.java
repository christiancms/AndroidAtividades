package br.com.christian.consultabasicauthentication;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnAcesso;
    private TextView txtMensa;
    private ImageView imgFoto;
    private ProgressBar progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnAcesso = (Button) findViewById(R.id.btnAcesso);
        txtMensa = (TextView) findViewById(R.id.txtMensa);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        progBar = (ProgressBar) findViewById(R.id.progBar);
        btnAcesso.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();

        DownloadTask task = new DownloadTask();
        /**
         *  ../android/.. é sem necessidade de autenticação
         * task.execute("http://187.7.106.14:9090/aluno/android/apto.txt");
         * ../android2/.. necessita autenticação (login: senac1 e senha:senac1)
         * task.execute("http://187.7.106.14:9090/aluno/android2/venda.txt", login, senha);
         * localhost 10.0.2.2
         */
        task.execute("http://10.0.2.2:8080/android/info.txt", login, senha);
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            imgFoto.setVisibility(View.GONE);
            progBar.setVisibility(View.VISIBLE);
        }

        int code;

        @Override
        protected String doInBackground(String... strings) {
            String path = strings[0];
            String login = strings[1];
            String senha = strings[2];

            HttpURLConnection urlConnection = null;
            String result = "";
            try {
                URL url = new URL(path);
                urlConnection = (HttpURLConnection) url.openConnection();
                // define credenciais
                String credentials = login + ":" + senha;
                String base64EncodedCredentials = Base64
                        .encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                urlConnection.addRequestProperty("Authorization", "Basic " + base64EncodedCredentials);
                int code = urlConnection.getResponseCode();
                if (code == 200) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    if (in != null) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null)
                            result += line;
                    }
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result;

        }


        @Override
        protected void onPostExecute(String texto) {
            progBar.setVisibility(View.GONE);
            imgFoto.setVisibility(View.VISIBLE);

            String[] partes = texto.split(";");
            String nome = partes[0];
            String pathFoto = partes[1];

            Picasso.with(getBaseContext())
                    .load(pathFoto)
                    .into(imgFoto);

            txtMensa.setText("Código: " + code + " - " + nome);
        }
    }
}
