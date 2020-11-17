package ketudom.pakasit.mte_203.it58.findmylosttni;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Lostform extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText edtwhat,edtwhere,edtdate,edtname,edttel,edtsecret,edtdes;
    Button butSend;

    HttpClient hc;
    HttpPost hp;
    HttpResponse hr;
    String url = "", data = "";
    BufferedReader br;
    Calendar myCalendar = Calendar.getInstance();
    //////////////
    /*Bitmap bitmap;
    boolean check = true;
    Button SelectImageGallery, UploadImageServer;
    ImageView imageView;
    EditText imageName;
    ProgressDialog progressDialog ;
    String GetImageNameEditText;
    //String ImageName = "image_name" ;
    String ImagePath = "image_path" ;
    //String ServerUploadPath ="https://k-ketudom.000webhostapp.com/project/img_upload_to_server.php" ;
    Button butShowim;*/
    /////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostform);
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

        edtwhat = (EditText) findViewById(R.id.edtWhat);
        edtwhere = (EditText) findViewById(R.id.edtWhere);
        edtdate = (EditText) findViewById(R.id.edtDate);
        edtname = (EditText) findViewById(R.id.edtName);
        edttel = (EditText) findViewById(R.id.edtTel);
        edtsecret = (EditText) findViewById(R.id.edtSecret);
        edtdes = (EditText) findViewById(R.id.edtdes);


        butSend = (Button) findViewById(R.id.butSend);

        Date cal = (Date) Calendar.getInstance().getTime();
        CharSequence s = DateFormat.format("dd-MM-yyyy", cal.getTime());
        //String dt = cal.toLocaleString();
        final DatePickerDialog.OnDateSetListener dd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edtdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Lostform.this, dd, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edtdate.setText(s);


        ///////////////////////////////////
        //imageView = (ImageView)findViewById(R.id.imageView);


        //SelectImageGallery = (Button)findViewById(R.id.butgetimage);

        //UploadImageServer = (Button)findViewById(R.id.buttonUpload);

        /*butShowim = (Button)findViewById(R.id.butshowim);
        butShowim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sm = new Intent(Lostform.this,Showimage.class);
                startActivity(sm);


            }
        });*/


        butSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress = new ProgressDialog(Lostform.this);
                progress.setTitle("In progress");
                progress.setMessage("Loading...");
                progress.setCancelable(false);
                progress.show();


                hc = new DefaultHttpClient();
                String what = edtwhat.getText().toString();
                String where = edtwhere.getText().toString();

                String date = edtdate.getText().toString();
                String[] cut = date.split("-");
                String Ndate = cut[2] + "-" + cut[1] + "-" + cut[0];


                String des = edtdes.getText().toString();
                String name = edtname.getText().toString();
                String tel = edttel.getText().toString();
                String secret = edtsecret.getText().toString();

                if (what.equals("") || where.equals("") || Ndate.equals("") || des.equals("") || name.equals("") || tel.equals("") || secret.equals("")) {
                    Toast.makeText(Lostform.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                } else {
                    try {

                        url = "https://k-ketudom.000webhostapp.com/project/insertlost.php?whatlost=" + what + "&wherelost=" + where + "&date=" + Ndate + "&description=" + des + "&name=" + name + "&tel=" + tel + "&secret=" + secret;
                        hp = new HttpPost(url);


                        hr = hc.execute(hp);
                        br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                        data = br.readLine();

                        if (data.equals("Error")) {
                            Toast.makeText(Lostform.this, "ไม่สามารถเพิ่มข้อมูลได้", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        } else {
                            Toast.makeText(Lostform.this, "เพิ่มข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                            clearscreen();
                            progress.dismiss();
                        }

                    } catch (Exception e) {
                        Toast.makeText(Lostform.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }



    private void clearscreen(){
        edtwhat.setText("");
        edtwhere.setText("");
        edtdate.setText("");
        edtname.setText("");
        edttel.setText("");
        edtsecret.setText("");
        edtdes.setText("");

    }
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtdate.setText(sdf.format(myCalendar.getTime()));
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
        getMenuInflater().inflate(R.menu.lostform, menu);
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
            Intent ap = new Intent(Lostform.this, Allpost.class);
            startActivity(ap);
        } else if (id == R.id.nav_lost) {
            Intent lo = new Intent(Lostform.this, Lostform.class);
            startActivity(lo);
        } else if (id == R.id.nav_found) {
            Intent fo = new Intent(Lostform.this, Foundform.class);
            startActivity(fo);
        }else if (id==R.id.nav_contact){
            Intent con = new Intent(Lostform.this, Contact.class);
            startActivity(con);
        }else  if (id==R.id.nav_home){
            Intent home = new Intent(Lostform.this, MainActivity.class);
            startActivity(home);
        }
    }catch (Exception e) {
        Toast.makeText(Lostform.this, e.toString(), Toast.LENGTH_SHORT).show();
    }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
