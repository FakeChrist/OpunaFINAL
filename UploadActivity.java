package aspx.ukindex.ac.brighton.httpswww.opuna2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class UploadActivity extends ActionBarActivity implements View.OnClickListener, Spinner.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private Button bBrowse;
    private Button bUpload;

    private TextView tvFileInfo;

    private EditText enTitle;
    private EditText enDescription;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL = "http://opuna.co.uk/Upload%20api/Upload.php";

    private String KEY_IMAGE = "image";
    private String KEY_TITLE = "Title";
    private String KEY_DESC = "Description";
    private String KEY_SUB = "Sub";

    private Spinner spSubject;
    private ArrayList<String> subjects;
    private JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload3);

        bBrowse = (Button) findViewById(R.id.bBrowse);
        bUpload = (Button) findViewById(R.id.bUpload);

        enTitle = (EditText) findViewById(R.id.enTitle);
        enDescription = (EditText) findViewById(R.id.enDescription);

        // imageView  = (ImageView) findViewById(R.id.imageView);
        tvFileInfo = (TextView) findViewById(R.id.tvFileInfo);

        bBrowse.setOnClickListener(this);
        bUpload.setOnClickListener(this);

        subjects = new ArrayList<String>();

        spSubject = (Spinner) findViewById(R.id.spSubject);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener
        spSubject.setOnItemSelectedListener(this);


        //This method will fetch the data from the URL
        getData();
    }

    /*

    SPINNER CODE

     */
    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(SubConfig.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(SubConfig.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getSubjects(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getSubjects(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                subjects.add(json.getString(SubConfig.TAG_SUBJECTS));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spSubject.setAdapter(new ArrayAdapter<String>(UploadActivity.this, android.R.layout.simple_spinner_dropdown_item, subjects));
    }

    /*

    UPLOAD IMAGE CODE

     */

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(UploadActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(UploadActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String Title = enTitle.getText().toString().trim();
                String Description = enDescription.getText().toString().trim();
                String Sub = spSubject.getSelectedItem().toString();
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_TITLE, Title);
                params.put(KEY_DESC, Description);
                params.put(KEY_SUB, Sub);



                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                //imageView.setImageBitmap(bitmap);
                tvFileInfo.setText("File Selected: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == bBrowse) {
            showFileChooser();
        }

        if (v == bUpload) {
            uploadImage();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String Sub = spSubject.getSelectedItem().toString();

        Log.i("Selected item : ", Sub);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}