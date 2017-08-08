package br.com.christian.trabalhoum;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLogin, edtSenha;
    private Button btnAcesso;
    private TextView txtMensagem;
    private ImageView imgFoto;
    private ProgressBar progBar;
    private static final String caminho = "http://10.0.2.2:8080/android/figuras/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnAcesso = (Button) findViewById(R.id.btnAcesso);
        txtMensagem = (TextView) findViewById(R.id.txtMensagem);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        progBar = (ProgressBar) findViewById(R.id.progBar);
        btnAcesso.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();

        DownloadTask task = new DownloadTask();
        //http://localhost:8080/android/validaws.php?usu=christian&pas=a1b2c3
        task.execute("http://10.0.2.2:8080/android/validaws.php", login, senha);
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String path = params[0];
            String login = params[1];
            String senha = params[2];

            HttpURLConnection urlConnection = null;
            String result = "";

            try {
                urlConnection = connectar(path + "?usu=" + login + "&pas=" + senha);
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
        protected void onPreExecute() {
            imgFoto.setVisibility(View.GONE);
            progBar.setVisibility(View.VISIBLE);
        }

        private HttpURLConnection connectar(String urlArquivo) throws IOException {
            final int SEGUNDOS = 1000;
            URL url = new URL(urlArquivo);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;
        }

        @Override
        protected void onPostExecute(String s) {

            imgFoto.setVisibility(View.VISIBLE);
            progBar.setVisibility(View.GONE);

            String[] partes = s.replace("{","").replace("}","").replace("\"","").split(":");
            String nome = partes[0];
            String pathFoto = partes[1];

            Picasso.with(getApplicationContext()).load(caminho+pathFoto).into(imgFoto);

            txtMensagem.setText(nome.toUpperCase());
            edtLogin.setText("");
            edtSenha.setText("");

        }
    }
}
