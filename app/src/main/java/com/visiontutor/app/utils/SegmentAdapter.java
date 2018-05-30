package com.visiontutor.app.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.visiontutor.app.R;

import java.util.List;

public class SegmentAdapter extends ArrayAdapter<Segment> {
    private final List<Segment> list;

    public SegmentAdapter(Context context, int resource, List<Segment> list) {
        super(context, resource, list);
        this.list = list;
    }

    private static class ViewHolder {
        private TextView SegmentName;
        private CheckBox SegmentCheckBox;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.row_segment, null);
            viewHolder = new ViewHolder();
            viewHolder.SegmentName = convertView.findViewById(R.id.row_segment_checkbox);
            viewHolder.SegmentCheckBox = convertView.findViewById(R.id.row_segment_textview);
            viewHolder.SegmentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    list.get(getPosition).setSelected(buttonView.isChecked());
                    if (buttonView.isChecked()) {

                    } else {

                    }
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.row_segment_textview, viewHolder.SegmentName);
            convertView.setTag(R.id.row_segment_checkbox, viewHolder.SegmentCheckBox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.SegmentCheckBox.setTag(position);
        viewHolder.SegmentName.setText(list.get(position).get_class().getName());
        viewHolder.SegmentCheckBox.setChecked(list.get(position).isSelected());

        return convertView;
    }
}