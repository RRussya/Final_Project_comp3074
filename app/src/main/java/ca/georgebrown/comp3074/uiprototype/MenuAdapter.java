package ca.georgebrown.comp3074.uiprototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends ArrayAdapter<MenuModel> {

    private List<Integer> selectedIds = new ArrayList<>();

    public MenuAdapter(Context context, List<MenuModel> menuList) {
        super(context, 0, menuList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_menu, parent, false);
        }

        MenuModel menuModel = getItem(position);

        TextView tvCategory = convertView.findViewById(R.id.tvCategory);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);

        tvCategory.setText(menuModel.getCategory());
        tvName.setText(menuModel.getName());
        tvPrice.setText(String.valueOf(menuModel.getPrice()));

        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedIds.add(menuModel.getId());
            } else {
                selectedIds.remove(Integer.valueOf(menuModel.getId()));
            }
        });
        checkBox.setChecked(selectedIds.contains(menuModel.getId()));

        return convertView;
    }

    public List<Integer> getSelectedIds() {
        return selectedIds;
    }
}