package ca.georgebrown.comp3074.uiprototype;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SetupActivity extends AppCompatActivity {

    private Button btnAdd, btnDelete;
    private EditText editTextCategory, editTextName, editTextNumber;
    private ListView listViewMenu;
    private DBHelper dbHelper;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        getSupportActionBar().setTitle("Configuration");

        // Initialize UI elements
        addButton = findViewById(R.id.add);
        deleteButton = findViewById(R.id.delete);
        lvMenuList = findViewById(R.id.lv_menulist);
        etCategory = findViewById(R.id.et_category);
        etName = findViewById(R.id.et_name);
        etNumber = findViewById(R.id.et_number);

        // Initialize DBHelper
        dbHelper = new DBHelper(ConfigurationActivity.this);

        // Retrieve items from the database
        List<MenuModel> items = dbHelper.getAllItems();

        // Create an ArrayAdapter and set it to the ListView
        menuAdapter = new MenuAdapter(ConfigurationActivity.this, items);
        lvMenuList.setAdapter(menuAdapter);

        // Clicking add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Get input values
                    String category = etCategory.getText().toString();
                    String name = etName.getText().toString();
                    int number = Integer.parseInt(etNumber.getText().toString());

                    // Create a new MenuModel
                    MenuModel menuModel = new MenuModel(0, category, name, number);

                    // Add item to the database
                    boolean success = dbHelper.addOne(menuModel);
                    Toast.makeText(ConfigurationActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();

                    // Update the ListView with the new data
                    updateListView();

                } catch (Exception e) {
                    Toast.makeText(ConfigurationActivity.this, "Error creating item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Clicking delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete selected items from the database
                for (int id : menuAdapter.getSelectedIds()) {
                    dbHelper.deleteOne(id);
                }

                // Update the ListView after deletion
                updateListView();

                Toast.makeText(ConfigurationActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Helper method to update the ListView with the latest data from the database
    private void updateListView() {
        List<MenuModel> updatedItems = dbHelper.getAllItems();
        menuAdapter.clear(); // Clear the existing data in the adapter
        menuAdapter.addAll(updatedItems); // Add the updated data to the adapter
        menuAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
}
