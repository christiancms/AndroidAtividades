package br.com.christian.mostraimagem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String PATH_IMG = "http://192.168.200.10/aluno/android/livro.gif";

    private ImageView imgFoto;
    private ProgressBar progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        progBar = (ProgressBar) findViewById(R.id.progBar);
    }

    public void carregaImagem(View view) {

        DownloadTask task = new DownloadTask();
        task.execute(PATH_IMG); //o caminho da imagem


    }

    // Delegar tarefa para a Thread

    private class DownloadTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            imgFoto.setVisibility(View.GONE); // oculta a figura
            progBar.setVisibility(View.VISIBLE); // mostra progressbar
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // recebe o parâmetro enviado para esta AsynTask
            // que é o caminho da imagem
            String path = params[0];

            try {

                URL url = new URL(path);
                HttpsURLConnection conexao = (HttpsURLConnection) url.openConnection();
                conexao.setRequestMethod("GET");

                conexao.connect();

                InputStream is = conexao.getInputStream();
                Bitmap imagem = BitmapFactory.decodeStream(is);
                return imagem;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgFoto.setVisibility(View.VISIBLE);
            progBar.setVisibility(View.GONE);

            if (bitmap != null) {
                imgFoto.setImageBitmap(bitmap);
            } else {
                Toast.makeText(MainActivity.this, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
