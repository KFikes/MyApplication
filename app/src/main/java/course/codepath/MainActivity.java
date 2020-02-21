package course.codepath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button bttAdd;
    EditText edItem;
    RecyclerView returns;
    ItemsAdapter itemsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttAdd = findViewById(R.id.bttAdd);
        edItem = findViewById(R.id.edItem);
        returns = findViewById(R.id.returns);


        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was remove", Toast.LENGTH_SHORT).show();
                saveItems();


            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        returns.setAdapter(itemsAdapter);
        returns.setLayoutManager(new LinearLayoutManager(this));

        bttAdd.setOnClickListener(new View.OnClickListener() {
            @Override     public void onClick(View v) {
                String todoItem = edItem.getText().toString();
                items.add(todoItem);
                itemsAdapter.notifyItemInserted(items.size() - 1);
                edItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();



            }
        });
    }
private File getDataFile() {
            return new File(getFilesDir(), "data.txt");
        }
    private void loadItems(){
    try{
        items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
            } catch(IOException e){
    Log.e("MainActivity", "Error reading items", e);
    items = new ArrayList<>();
        }
    }
    private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(),items);
        } catch(IOException e){
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}
