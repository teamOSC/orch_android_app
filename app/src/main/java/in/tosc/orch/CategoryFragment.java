package in.tosc.orch;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.cardsui.Card;
import com.afollestad.cardsui.CardHeader;
import com.afollestad.cardsui.CardListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by omerjerk on 16/6/14.
 */
public class CategoryFragment extends Fragment {
    private Context mContext;
    private View rootView;

    private String category;

    public CategoryFragment() {}

    //TODO: Need to do Fragment.setArguments here
    public CategoryFragment(String category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sliding, container, false);
        mContext = getActivity();
        new ReadFromJson().execute();
        return rootView;
    }

    private class ReadFromJson extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... v) {

            String jsonString = "";

            try {
                File cacheFile = new File(mContext.getFilesDir(), category + ".json");

                BufferedReader br = new BufferedReader(new FileReader(cacheFile));
                jsonString = br.readLine();

            } catch (IOException e) {
                e.printStackTrace();

            }

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray mJsonArray) {

            CardListView mListView = (CardListView) rootView.findViewById(android.R.id.list);

            CustomAdapter adapter = new CustomAdapter(mContext, android.R.color.holo_blue_dark , mJsonArray);
            mListView.setAdapter(adapter);
            // This sets the color displayed for card titles and header actions by default
            try{
                for(int i = 0; i < mJsonArray.length(); ++i) {
                    JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                    adapter.add(new Card(mJsonObject.getString("title")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
