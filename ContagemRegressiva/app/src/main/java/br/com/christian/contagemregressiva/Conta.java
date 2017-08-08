package br.com.christian.contagemregressiva;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by christian on 07/09/16.
 */
public class Conta extends AsyncTask<Integer, Integer, Void> {

    private Button btn;
    private TextView txt;

    public Conta(Button button, TextView textView) {
        this.btn = button;
        this.txt = textView;
    }

    @Override
    protected Void doInBackground(Integer... params) {

        int limite = params[0];

        for (int i = 1; i <= limite; i++) {
            SystemClock.sleep(500); // cinco segundos (ms)
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        btn.setEnabled(false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //super.onPostExecute(aVoid);
        btn.setEnabled(true);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //super.onProgressUpdate(values);

        int n = values[0];
        txt.setText(String.valueOf(n));
    }

}
