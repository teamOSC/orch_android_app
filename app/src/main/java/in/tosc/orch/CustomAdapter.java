package in.tosc.orch;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.cardsui.Card;
import com.afollestad.cardsui.CardAdapter;
import com.afollestad.cardsui.CardBase;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by omerjerk on 17/6/14.
 */
public class CustomAdapter extends CardAdapter {

    private JSONArray mJsonArray;
    /**
     * Initializes a new CardAdapter instance.
     *
     * @param context        The context used to inflate layouts and retrieve resources.
     * @param accentColorRes The color used for header actions and card titles, 0 will specify black.
     */
    public CustomAdapter(Context context, int accentColorRes, JSONArray mJsonArray) {
        super(context, accentColorRes);
        registerLayout(R.layout.custom_card);
        this.mJsonArray = mJsonArray;
    }

    @Override
    public View onViewCreated(int index, View recycled, CardBase item) {
        View view = super.onViewCreated(index, recycled, item);
        ImageView previewImage = (ImageView) view.findViewById(R.id.preview_image_view);
        try {
            JSONObject data = mJsonArray.getJSONObject(index);
            if(previewImage != null)
                Ion.with(previewImage)
                        .placeholder(R.drawable.ic_launcher)
                        .load(data.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
