package com.nepalicoders.retrofitdemo;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nepalicoders.retrofitdemo.afilechooser.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

//    private ListView listView;

    private static final int MY_PERMISSIONS_REQUEST = 100;

    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
        }

        Button uploadButton = (Button) findViewById(R.id.btn_upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // show only images, no videos or anything else
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);

//                startActivityForResult(intent, PICK_IMAGE_FROM_GALLERY_REQUEST);
                // Always show the chooser (if there is multiple options available
                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE_FROM_GALLERY_REQUEST);
            }
        });

//        final EditText name = (EditText) findViewById(R.id.input_name);
//        final EditText email = (EditText) findViewById(R.id.input_email);
//        final EditText age = (EditText) findViewById(R.id.input_age);
//        final EditText topics = (EditText) findViewById(R.id.input_topics);
//
//        Button createAccountButton = (Button) findViewById(R.id.btn_signup);
//        createAccountButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                User user = new User(
//                        name.getText().toString(),
//                        email.getText().toString(),
//                        Integer.parseInt(age.getText().toString()),
//                        topics.getText().toString().split(",")
//                );
//
//                sendNetworkRequest(user);
//            }
//        });

//        listView = (ListView) findViewById(R.id.pagination_list);
//
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(GsonConverterFactory.create());
//
//        Retrofit retrofit = builder.build();
//
//        GitHubClient client = retrofit.create(GitHubClient.class);
//        Call<List<GitHubRepo>> call = client.reposForUser("sulavneupane");
//
//        call.enqueue(new Callback<List<GitHubRepo>>() {
//            @Override
//            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
//                List<GitHubRepo> repos = response.body();
//
//                listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, repos));
//            }
//
//            @Override
//            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "error :(", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    private void sendNetworkRequest(User user) {
//
//        //create OkHttp client
//        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        if (BuildConfig.DEBUG) {
//            okHttpClientBuilder.addInterceptor(logging);
//        }
//
//        // create retrofit instance
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8080/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClientBuilder.build());
//
//        Retrofit retrofit = builder.build();
//
//        // get client and call object for the request
//        UserClient userClient = retrofit.create(UserClient.class);
//        Call<User> userCall = userClient.createAccount(user);
//
//        userCall.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Toast.makeText(MainActivity.this, "Registered with ID: " + response.body().getId(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri uri = data.getData();
//            uploadFile(uri);
//        }


        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {

            ClipData clipData = data.getClipData();
            ArrayList<Uri> fileUris = new ArrayList<Uri>();

            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    fileUris.add(uri);
                }

                // uploadTwoPhotos(fileUris.get(0), fileUris.get(1));
                uploadAlbum(fileUris);
            } else {
                Toast.makeText(this, "Please select multiple photos!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void uploadAlbum(ArrayList<Uri> fileUris) {
        final EditText description = (EditText) findViewById(R.id.input_description);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UserClient userClient = retrofit.create(UserClient.class);

        List<MultipartBody.Part> filesPart = new ArrayList<>();
        for (int i = 0; i < fileUris.size(); i++) {
            filesPart.add(prepareFilePart("" + i, fileUris.get(i)));
        }

        Call<ResponseBody> call = userClient.uploadAlbum(
                createPartFromString(description.getText().toString()),
                filesPart
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "Yeah!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Noooo!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadTwoPhotos(Uri photo1, Uri photo2) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UserClient userClient = retrofit.create(UserClient.class);

        Call<ResponseBody> call = userClient.uploadTwoPhotos(
                prepareFilePart("photo1", photo1),
                prepareFilePart("photo2", photo2)
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "Yeah!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Noooo!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted! do the file related task you need to do.

                } else {
                    // permission denied! disable the functionality that depends on this permission.

                }
                return;
        }
    }

    private void uploadFile(Uri fileUri) {
        final EditText description = (EditText) findViewById(R.id.input_description);
        final EditText photographer = (EditText) findViewById(R.id.input_photographer);
        final EditText year = (EditText) findViewById(R.id.input_year);
        final EditText location = (EditText) findViewById(R.id.input_location);

        // create retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        // get client and call object for the request
        UserClient userClient = retrofit.create(UserClient.class);

        Map<String, RequestBody> partMap = new HashMap<>();
        partMap.put("client", createPartFromString("android"));
        partMap.put("secret", createPartFromString("sulavneupane"));

        if (!TextUtils.isEmpty(description.getText().toString())) {
            partMap.put("description", createPartFromString(description.getText().toString()));
        }

        if (!TextUtils.isEmpty(photographer.getText().toString())) {
            partMap.put("photographer", createPartFromString(photographer.getText().toString()));
        }

        if (!TextUtils.isEmpty(year.getText().toString())) {
            partMap.put("year", createPartFromString(year.getText().toString()));
        }

        if (!TextUtils.isEmpty(location.getText().toString())) {
            partMap.put("location", createPartFromString(location.getText().toString()));
        }

        // finally execute the request
        Call<ResponseBody> call = userClient.uploadPhoto(
                partMap,
                prepareFilePart("photo", fileUri)
        );
//        Call<ResponseBody> call = userClient.uploadPhoto(
//                createPartFromString(description.getText().toString()),
//                createPartFromString(photographer.getText().toString()),
//                createPartFromString(year.getText().toString()),
//                createPartFromString(location.getText().toString()),
//                prepareFilePart("photo", fileUri)
//        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "Yeah!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Noooo!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MultipartBody.FORM, descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
