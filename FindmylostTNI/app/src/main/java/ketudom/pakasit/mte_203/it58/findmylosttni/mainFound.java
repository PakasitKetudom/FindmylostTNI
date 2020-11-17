package ketudom.pakasit.mte_203.it58.findmylosttni;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class mainFound extends AppCompatActivity {

    ListView display;
    SimpleAdapter myAdapter;
    List<HashMap<String, String>> fill_data;
    HashMap<String, String> myMap, read_data;

    HttpClient hc;
    HttpPost hp;
    HttpResponse hr;
    String url = "", data = "";
    BufferedReader br;

    Button butFind;
    EditText edtFind;
    Spinner spnFind;
    SearchView search;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_found);

        display=(ListView)findViewById(R.id.lst_display);

        //edtFind=(EditText)findViewById(R.id.edtFind);
        butFind=(Button)findViewById(R.id.butFind);
        spnFind=(Spinner)findViewById(R.id.spnFind);
       /* search=(SearchView)findViewById(R.id.search);
        CharSequence query = search.getQuery();
        CharSequence queryHint = search.getQueryHint();
        boolean isIconfied=search.isIconfiedByDefault();
        search.setIconifiedByDefault(false);
        search.setQueryHint("Search View");*/



        //showfound();
        onResume();
        ////////////////////////





        butFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchAll();

            }


        });
    }


    public void showfound(){

        display.setVisibility(View.INVISIBLE);

        hc = new DefaultHttpClient();

        url = "https://k-ketudom.000webhostapp.com/project/allfound.php";
        hp = new HttpPost(url);

        try {
            hr = hc.execute(hp);
            br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
            data = br.readLine();
            myAdapter = null;
            String[] from = new String[]{"id", "name", "date", "status"};
            int[] to = new int[]{R.id.id, R.id.name, R.id.date, R.id.status};
            fill_data = new ArrayList<HashMap<String, String>>();
            myMap = new HashMap<String, String>();

            myMap.put("id", "ID");
            myMap.put("name", "Name");
            myMap.put("date", "Date");
            myMap.put("status", "Status");
            fill_data.add(myMap);

            String part[] = data.split(";");


            for (int i = 0; i < part.length; i++) {

                String part2[] = part[i].split(",");
                myMap = new HashMap<String, String>();

                myMap.put("id", "" + part2[0]);
                myMap.put("name", "" + part2[1]);

                String indate = part2[2].toString();
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Date olddate = fmt.parse(indate);

                SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
                String newFdate = simpleDate.format(olddate);
                myMap.put("date", "" + newFdate);
                myMap.put("status", "" + part2[3]);

                fill_data.add(myMap);

                display.setVisibility(View.VISIBLE);

            }
            br.close();
            myAdapter = new SimpleAdapter(mainFound.this, fill_data, R.layout.display_layout, from, to);
            display.setAdapter(myAdapter);

        } catch (Exception e) {
            Toast.makeText(mainFound.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent goEfound = new Intent(mainFound.this, Editfound.class);
                String res2 = "";
                read_data = (HashMap<String, String>) display.getItemAtPosition(position);
                res2 = read_data.get("id");

                goEfound.putExtra("id", res2);

                startActivity(goEfound);
            }
        });



    }
    private void SearchAll() {


        /*edtFind.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtFind.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);*/

        if (spnFind.getSelectedItem().toString().equals("ทั้งหมด")) {

            showfound();


        } else if (spnFind.getSelectedItem().toString().equals("ยังไม่ได้คืน")) {
            //display2.setVisibility(View.INVISIBLE);
            hc = new DefaultHttpClient();

            url = "https://k-ketudom.000webhostapp.com/project/foundwherestatus.php?action=no";
            hp = new HttpPost(url);

            try {
                hr = hc.execute(hp);
                br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                data = br.readLine();
                myAdapter = null;
                String[] from = new String[]{"id", "name", "date", "status"};
                int[] to = new int[]{R.id.id, R.id.name, R.id.date, R.id.status};
                fill_data = new ArrayList<HashMap<String, String>>();
                myMap = new HashMap<String, String>();

                myMap.put("id", "ID");
                myMap.put("name", "Name");
                myMap.put("date", "Date");
                myMap.put("status", "Status");
                fill_data.add(myMap);

                String part[] = data.split(";");

                for (int i = 0; i < part.length; i++) {

                    String part2[] = part[i].split(",");
                    myMap = new HashMap<String, String>();

                    myMap.put("id", "" + part2[0]);
                    myMap.put("name", "" + part2[1]);


                    String indate = part2[2].toString();
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    Date olddate = fmt.parse(indate);

                    SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
                    String newFdate = simpleDate.format(olddate);

                    myMap.put("date", "" + newFdate);
                    myMap.put("status", "" + part2[3]);

                    fill_data.add(myMap);

                    display.setVisibility(View.VISIBLE);

                }
                br.close();
                myAdapter = new SimpleAdapter(mainFound.this, fill_data, R.layout.display_layout, from, to);
                display.setAdapter(myAdapter);

            } catch (Exception e) {
                Toast.makeText(mainFound.this, e.toString(), Toast.LENGTH_SHORT).show();
            }


            display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent goElost = new Intent(mainFound.this, EditLost.class);
                    String res = "";
                    read_data = (HashMap<String, String>) display.getItemAtPosition(position);
                    res = read_data.get("id");

                    goElost.putExtra("id", res);

                    startActivity(goElost);
                }
            });
        }else if (spnFind.getSelectedItem().toString().equals("ได้คืนแล้ว")){


            hc = new DefaultHttpClient();

            url = "https://k-ketudom.000webhostapp.com/project/foundwherestatus.php?action=yes";
            hp = new HttpPost(url);

            try {
                hr = hc.execute(hp);
                br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                data = br.readLine();
                myAdapter = null;
                String[] from = new String[]{"id", "name", "date", "status"};
                int[] to = new int[]{R.id.id, R.id.name, R.id.date, R.id.status};
                fill_data = new ArrayList<HashMap<String, String>>();
                myMap = new HashMap<String, String>();

                myMap.put("id", "ID");
                myMap.put("name", "Name");
                myMap.put("date", "Date");
                myMap.put("status", "Status");
                fill_data.add(myMap);

                String part[] = data.split(";");

                for (int i = 0; i < part.length; i++) {

                    String part2[] = part[i].split(",");
                    myMap = new HashMap<String, String>();

                    myMap.put("id", "" + part2[0]);
                    myMap.put("name", "" + part2[1]);

                    String indate = part2[2].toString();
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    Date olddate = fmt.parse(indate);

                    SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
                    String newFdate = simpleDate.format(olddate);


                    myMap.put("date", "" + newFdate);
                    myMap.put("status", "" + part2[3]);

                    fill_data.add(myMap);

                    display.setVisibility(View.VISIBLE);

                }
                br.close();
                myAdapter = new SimpleAdapter(mainFound.this, fill_data, R.layout.display_layout, from, to);
                display.setAdapter(myAdapter);

            } catch (Exception e) {
                Toast.makeText(mainFound.this, e.toString(), Toast.LENGTH_SHORT).show();
            }


            display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent goElost = new Intent(mainFound.this, EditLost.class);
                    String res = "";
                    read_data = (HashMap<String, String>) display.getItemAtPosition(position);
                    res = read_data.get("id");

                    goElost.putExtra("id", res);

                    startActivity(goElost);
                }
            });



        }
    }
    protected void onResume() {
        super.onResume();
        showfound();
        //showlost();
        //SearchAll();

    }

}
