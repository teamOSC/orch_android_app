package in.tosc.orch;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by omerjerk on 16/6/14.
 */
public class UpdateData extends AsyncTask<Void, Void, String> {

    private Context context;
    private Boolean ranAutomatically;
    private String category;

    public UpdateData(Context context, Boolean ranAutomatically, String category){

        this.context = context;
        this.ranAutomatically = ranAutomatically;
        this.category = category;

        if (!ranAutomatically) {
            ((Activity)context).setProgressBarIndeterminateVisibility(true);
        }
    }

    @Override
    protected String doInBackground(Void... v){

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(
                    new HttpGet("http://162.243.238.19:1000/api/" + this.category + "/20"));
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (Exception e) {
            if (!ranAutomatically) {
                ((Activity)context).setProgressBarIndeterminateVisibility(false);
            }
            Log.d("[GET REQUEST]", "Network exception", e);
            return null;
        }
    }

    protected void onPostExecute(String r) {

        if (!ranAutomatically) {
            ((Activity)context).setProgressBarIndeterminateVisibility(false);
        }

        Log.d("[GET RESPONSE]", r);
        File cacheFile = new File(context.getFilesDir(), category + ".json");


        BufferedWriter bw = null;
        try {
            if (!cacheFile.exists()) {
                cacheFile.createNewFile();
            }
            FileWriter fw = new FileWriter(cacheFile.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(r);

            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Sorry! Something went wrong.", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
                //Should never happen
            }
        }
    }
}
