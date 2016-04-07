package bestcompany.moviematcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Billy on 2016-04-06.
 */
public class ListViewAdapter extends BaseAdapter {

    private ArrayList<String> in = new ArrayList<>();
    private Activity mActivity;

    public ListViewAdapter(ArrayList<String> info,Activity activity)
    {
        in = info;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return in.size();
    }

    @Override
    public Object getItem(int position) {
        return in.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final View convertView = LayoutInflater.from(mActivity).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(); //Making variable of class type ViewHolder def
        convertView.setTag(holder); //sets the tag

        holder.movieReccomendation = (TextView) convertView.findViewById(R.id.Reccomendationtext);
        holder.movieName = (TextView) convertView.findViewById(R.id.MovieText);
        holder.mapText = (TextView) convertView.findViewById(R.id.LocationText);

        if(position==0)
        {
            holder.movieReccomendation.setText("This is your movie:");
        }

        holder.movieName.setText(in.get(position));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clicking on this leads you to the google maps location
                Intent intent = new Intent(mActivity,MapsActivity.class);
                intent.putExtra("Name",in.get(position));
                mActivity.startActivity(intent);
            }
        });






        // Android functions to determine the screen dimensions.
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        // Storing the screen height into an int variable.
        int height = size.y;
        //Sets the height to 1/3 the screensize.
        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        params.height =  (int)Math.round(height*.3198);



        if(in.get(position).contains("Movie"))
        {
            return null;
        }

        return convertView;
    }



    //A view holder that contain the things that need to be changed for every event
    private static class ViewHolder
    {
        TextView movieReccomendation;
        TextView movieName;
        TextView mapText;
    }


}
