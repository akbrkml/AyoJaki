package com.termal.ayojaki.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.termal.ayojaki.R;

public class TukarActivity extends AppCompatActivity {
    ListView item_list;
    String[] itemname ={
            "Es Jeruk",
            "Burger",
            "Air Mineral",
            "Pizza",
            "Pulsa Elektrik"
    };

    String[] description ={
            "100 poin",
            "150 poin",
            "50 poin",
            "200 pon",
            "120 poin"
    };
    Integer[] imgid={
            R.drawable.es,
            R.drawable.burger,
            R.drawable.water,
            R.drawable.pizza,
            R.drawable.pulsa
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tukar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TukarAdapter adapter=new TukarAdapter(this, itemname, imgid, description);
        item_list=(ListView)findViewById(R.id.item_list);
        item_list.setAdapter(adapter);

        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void tukarButton(View view)
    {
        Intent intent = new  Intent(TukarActivity.this, TukarActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

}
