package com.example.a3175.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3175.db.Overview;
import com.example.a3175.db.User;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

public class Utils {
    private static final String TAG = "test";
    private static MessageDigest messageDigest;

    private static DecimalFormat decimalFormat;

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, "static initializer: " + e.getMessage());
        }

        decimalFormat = new DecimalFormat("#.##");
    }

    /**
     * Encode a password string
     */
    public static String encode(String plaintext) {
        String encoded = "";
        messageDigest.update(plaintext.getBytes());
        encoded = new String(messageDigest.digest());
        return encoded;
    }

    /**
     * Format a double
     *
     * @param n
     * @return
     */
    public static String formatDouble(double n){
        return decimalFormat.format(n);
    }


//    public static <T> ItemTouchHelper getItemTouchHelper(
//            Activity activity,
//            LiveData<List<T>> liveData,
//            AndroidViewModel viewModel){
//
//        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
//                0,
//                ItemTouchHelper.START | ItemTouchHelper.END) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView,
//                                  @NonNull RecyclerView.ViewHolder viewHolder,
//                                  @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                new AlertDialog.Builder(activity)
//                        .setTitle("Delete?")
//                        .setPositiveButton("Yes", (dialog, which) -> {
//                            T itemToDelete = liveData.getValue().get(viewHolder.getAdapterPosition());
//                            viewModel.deleteUsers(itemToDelete);
//
//                        })
//                        .setNegativeButton("No", (dialog, which) -> {
//                            userAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
//                        })
//                        .create()
//                        .show();
//            }
//        });
//    }

}
