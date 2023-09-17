package com.considlia.tictac.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.considlia.tictac.MyViewModel;
import com.considlia.tictac.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {

    public TextView tv_first_last_name, tv_username, tv_email, tv_phone;
    private MyViewModel myViewModel;
    public ImageView userImage;
    int PICK_IMAGE = 200;
    Uri imageUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        tv_first_last_name = v.findViewById(R.id.tv_first_last_name);
        tv_username = (TextView) v.findViewById(R.id.tv_username);
        tv_email = (TextView) v.findViewById(R.id.tv_email);
        tv_phone = (TextView) v.findViewById(R.id.tv_phone);


        userImage = (ImageView) v.findViewById(R.id.userImage);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(getContext());
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        myViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            tv_first_last_name.setText(user.getFullName());
            tv_username.setText(user.getUsername());
            tv_email.setText(user.getEmail());
            tv_phone.setText(user.getPhone());
        });
    }

    public void onStart() {
        super.onStart();
    }

    public static AccountFragment newInstance(String text) {

        AccountFragment f = new AccountFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent i = new Intent();
//                    i.setType("image/*");
//                    i.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(i, "Pick Photo"), PICK_IMAGE);


//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                   startActivityForResult(pickPhoto, PICK_IMAGE);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        userImage.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
                        imageUri = data.getData();
                        userImage.setImageURI(imageUri);


//                         String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                         Log.i("ImageSelected", String.valueOf(selectedImage));
//                         if (selectedImage != null) {
//                             Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                                     filePathColumn, null, null, null);
//                             Log.i("image", String.valueOf(cursor));
//                             if (cursor != null) {
//                                 cursor.moveToFirst();
//
//                                 int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                 Log.i("ImageColumnIndex", String.valueOf(columnIndex));
//                                 String picturePath = cursor.getString(columnIndex);
//                                 cursor.close();
// //                                 Log.i("ImagePicturePAth", String.valueOf(picturePath));
// //                                 userImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                 File file = new File(picturePath);
//
//
//                                 Glide.with(getContext()).load(file).into(userImage);
//
//                             }
//                         }
                        break;
                    }
            }
        }
    }
}