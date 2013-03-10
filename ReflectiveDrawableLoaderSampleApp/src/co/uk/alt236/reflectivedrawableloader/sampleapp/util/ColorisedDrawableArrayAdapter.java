package co.uk.alt236.reflectivedrawableloader.sampleapp.util;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import co.uk.alt236.reflectivedrawableloader.ReflectiveDrawableLoader;
import co.uk.alt236.reflectivedrawableloader.containers.DrawableResourceContainer;
import co.uk.alt236.reflectivedrawableloader.sampleapp.R;

public class ColorisedDrawableArrayAdapter extends ArrayAdapter<DrawableResourceContainer>{
    final static int mLayout = R.layout.list_item_icon_check;
    final static int mMissingIconId = R.drawable.ic_missing_icon;
    
    final Context mContext;
    final List<DrawableResourceContainer> mItemList;
    final ReflectiveDrawableLoader mReflectiveLoader;
    
    public ColorisedDrawableArrayAdapter(Context context, List<DrawableResourceContainer> itemList) {
        super(context, mLayout);
        mReflectiveLoader = ReflectiveDrawableLoader.getInstance(context);
        mContext = context;
        mItemList = itemList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        Wrapper wrapper = null;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(mLayout, null);
            wrapper = new Wrapper(row);
            row.setTag(wrapper);
        } else {
            wrapper = (Wrapper) row.getTag();
        }

        wrapper.populateFrom(getItem(position));

        return (row);
    }

    @Override
    public DrawableResourceContainer getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    private class Wrapper {

        private TextView name = null;
        private ImageView image1 = null;

        private View row = null;

        public Wrapper(View row) {
            this.row = row;
        }

        public ImageView getImage1() {
            if (image1 == null) {
                image1 = (ImageView) row.findViewById(R.id.image1);
            }
            return(image1);
        }

        public TextView getName() {
            if (name == null) {
                name = (TextView) row.findViewById(R.id.name);
            }
            return(name);
        }

        public void populateFrom(DrawableResourceContainer drawableContainer) {
            if (drawableContainer != null) {        	
                getName().setText(drawableContainer.getDrawableName());
                drawableContainer.setImageWithPorterDuffMultiply(getImage1());
            }
        }

    }
}