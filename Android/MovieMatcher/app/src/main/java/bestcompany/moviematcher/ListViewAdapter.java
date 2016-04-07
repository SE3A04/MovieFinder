package bestcompany.moviematcher;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Billy on 2016-04-06.
 */
public class ListViewAdapter extends BaseAdapter {

    ArrayList<String> in = new ArrayList<String>();

    public ListViewAdapter(ArrayList<String> info)
    {
        in = info;
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
    public View getView(int position, View convertView, ViewGroup parent) {

return null;
    }
}
