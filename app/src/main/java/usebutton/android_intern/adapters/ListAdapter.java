package usebutton.android_intern.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usebutton.android_intern.R;


public class ListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> users;
    private Context context;

    public ListAdapter(Context context, int resource, ArrayList<String> jsonStrings) {
        super(context, resource, jsonStrings);
        users = jsonStrings;
        this.context = context;
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public String getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject((String) getItem(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String name = "";
        String email = "";
        String candidate = "";
        try {
            name = jsonObject.getString("name");
            email = jsonObject.getString("email");
            candidate = jsonObject.getString("candidate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView nameView = view.findViewById(R.id.name);
        TextView emailView = view.findViewById(R.id.email);
        TextView candidateView = view.findViewById(R.id.candidate);

        String nameText = "Name: " + name;
        String emailText = "Email: " + email;
        String candidateText = "Candidate: " + candidate;

        nameView.setText(nameText);
        emailView.setText(emailText);
        candidateView.setText(candidateText);

        return view;
    }
}
