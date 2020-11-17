package ketudom.pakasit.mte_203.it58.findmylosttni;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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

public class Allpost extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView display;
    SimpleAdapter myAdapter;
    List<HashMap<String, String>> fill_data;
    HashMap<String, String> myMap, read_data;

    HttpClient hc;
    HttpPost hp;
    HttpResponse hr;
    String url = "", data = "";
    BufferedReader br;

    Button butlost,butfound;
    EditText edtFind;
    Spinner spnFind;
    Date d = new Date();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpost);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //display=(ListView)findViewById(R.id.lst_display);
        //display2=(ListView)findViewById(R.id.lst_display2) ;

        //display.setVisibility(View.INVISIBLE);
        //display2.setVisibility(View.INVISIBLE);

        butlost=(Button)findViewById(R.id.butlost);
        butfound=(Button)findViewById(R.id.butfound);

       /* edtFind=(EditText)findViewById(R.id.edtFind);
        butFind=(Button)findViewById(R.id.butFind);
        spnFind=(Spinner)findViewById(R.id.spnFind);*/



        butlost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bl = new Intent(Allpost.this, mainLost.class);
                startActivity(bl);

            }
        });

        butfound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bf = new Intent(Allpost.this, mainFound.class);
                startActivity(bf);
            }
        });

        /*butFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchAll();
            }
        });*/



    }
    /*protected void onResume() {
        super.onResume();
        showfound();
        showlost();
        //SearchAll();


    }*/

    /*public void showlost(){

        //display2.setVisibility(View.INVISIBLE);

        hc = new DefaultHttpClient();

        url = "https://k-ketudom.000webhostapp.com/project/alllost.php";
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
                myMap.put("date", "" +part2[2]);
                myMap.put("status", "" + part2[3]);

                fill_data.add(myMap);

                display.setVisibility(View.VISIBLE);

            }
            br.close();
            myAdapter = new SimpleAdapter(Allpost.this, fill_data, R.layout.display_layout, from, to);
            display.setAdapter(myAdapter);

        } catch (Exception e) {
            Toast.makeText(Allpost.this, e.toString(), Toast.LENGTH_SHORT).show();
        }


        display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                display.getChildAt(0).setEnabled(false);

                Intent goElost = new Intent(Allpost.this, EditLost.class);
                String res = "";
                read_data = (HashMap<String, String>) display.getItemAtPosition(position);
                res = read_data.get("id");

                goElost.putExtra("id", res);

                startActivity(goElost);
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
                myMap.put("date", "" + part2[2]);
                myMap.put("status", "" + part2[3]);

                fill_data.add(myMap);

                display.setVisibility(View.VISIBLE);

            }
            br.close();
            myAdapter = new SimpleAdapter(Allpost.this, fill_data, R.layout.display_layout, from, to);
            display.setAdapter(myAdapter);

        } catch (Exception e) {
            Toast.makeText(Allpost.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent goEfound = new Intent(Allpost.this, Editfound.class);
                String res2 = "";
                read_data = (HashMap<String, String>) display.getItemAtPosition(position);
                res2 = read_data.get("id");

                goEfound.putExtra("id", res2);

                startActivity(goEfound);
            }
        });



    }*/

    /*private void SearchAll(){




        if(spnFind.getSelectedItem().toString().equals("ทั้งหมด")){

            showlost();
            showfound();

        }else if (spnFind.getSelectedItem().toString().equals("ยังไม่ได้คืน")){
            //display2.setVisibility(View.INVISIBLE);
            if(butlost.toString().equals("ของหาย")){
                hc = new DefaultHttpClient();

                url = "https://k-ketudom.000webhostapp.com/project/lostwherestatus.php?action=no";
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
                        myMap.put("date", "" + part2[2]);
                        myMap.put("status", "" + part2[3]);

                        fill_data.add(myMap);

                        display.setVisibility(View.VISIBLE);

                    }
                    br.close();
                    myAdapter = new SimpleAdapter(Allpost.this, fill_data, R.layout.display_layout, from, to);
                    display.setAdapter(myAdapter);

                } catch (Exception e) {
                    Toast.makeText(Allpost.this, e.toString(), Toast.LENGTH_SHORT).show();
                }


                display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent goElost = new Intent(Allpost.this, EditLost.class);
                        String res = "";
                        read_data = (HashMap<String, String>) display.getItemAtPosition(position);
                        res = read_data.get("id");

                        goElost.putExtra("id", res);

                        startActivity(goElost);
                    }
                });
            }else {

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
                        myMap.put("date", "" + part2[2]);
                        myMap.put("status", "" + part2[3]);

                        fill_data.add(myMap);

                        display.setVisibility(View.VISIBLE);

                    }
                    br.close();
                    myAdapter = new SimpleAdapter(Allpost.this, fill_data, R.layout.display_layout, from, to);
                    display.setAdapter(myAdapter);

                } catch (Exception e) {
                    Toast.makeText(Allpost.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent goEfound = new Intent(Allpost.this, Editfound.class);
                        String res2 = "";
                        read_data = (HashMap<String, String>) display.getItemAtPosition(position);
                        res2 = read_data.get("id");

                        goEfound.putExtra("id", res2);

                        startActivity(goEfound);
                    }
                });


            }


            //display.setVisibility(View.INVISIBLE);





        }else if (spnFind.getSelectedItem().toString().equals("ได้คืนแล้ว")){
            //display2.setVisibility(View.INVISIBLE);

            hc = new DefaultHttpClient();

            url = "https://k-ketudom.000webhostapp.com/project/lostwherestatus.php?action=yes";
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
                    myMap.put("date", "" + part2[2]);
                    myMap.put("status", "" + part2[3]);

                    fill_data.add(myMap);

                    display.setVisibility(View.VISIBLE);

                }
                br.close();
                myAdapter = new SimpleAdapter(Allpost.this, fill_data, R.layout.display_layout, from, to);
                display.setAdapter(myAdapter);

            } catch (Exception e) {
                Toast.makeText(Allpost.this, e.toString(), Toast.LENGTH_SHORT).show();
            }


            display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent goElost = new Intent(Allpost.this, EditLost.class);
                    String res = "";
                    read_data = (HashMap<String, String>) display.getItemAtPosition(position);
                    res = read_data.get("id");

                    goElost.putExtra("id", res);

                    startActivity(goElost);
                }
            });
        }
        /////////////////////////
        //display.setVisibility(View.INVISIBLE);

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
                myMap.put("date", "" + part2[2]);
                myMap.put("status", "" + part2[3]);

                fill_data.add(myMap);

                display.setVisibility(View.VISIBLE);

            }
            br.close();
            myAdapter = new SimpleAdapter(Allpost.this, fill_data, R.layout.display_layout, from, to);
            display.setAdapter(myAdapter);

        } catch (Exception e) {
            Toast.makeText(Allpost.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent goEfound = new Intent(Allpost.this, Editfound.class);
                String res2 = "";
                read_data = (HashMap<String, String>) display.getItemAtPosition(position);
                res2 = read_data.get("id");

                goEfound.putExtra("id", res2);

                startActivity(goEfound);
            }
        });






    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.allpost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
try{
        if (id == R.id.nav_Allpost) {
            Intent ap = new Intent(Allpost.this, Allpost.class);
            startActivity(ap);
        } else if (id == R.id.nav_lost) {
            Intent lo = new Intent(Allpost.this, Lostform.class);
            startActivity(lo);
        } else if (id == R.id.nav_found) {
            Intent fo = new Intent(Allpost.this, Foundform.class);
            startActivity(fo);
        }else if (id==R.id.nav_contact){
            Intent con = new Intent(Allpost.this, Contact.class);
            startActivity(con);
        }else  if (id==R.id.nav_home){
            Intent home = new Intent(Allpost.this, MainActivity.class);
            startActivity(home);
        }
    }catch (Exception e) {
        Toast.makeText(Allpost.this, e.toString(), Toast.LENGTH_SHORT).show();
    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
