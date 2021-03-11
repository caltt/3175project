package com.example.a3175.utils;

import android.util.Log;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

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
        String encoded;
        messageDigest.update(plaintext.getBytes());
        encoded = new String(messageDigest.digest());
        return encoded;
    }

    /**
     * Format a double
     */
    public static String formatBigDecimal(BigDecimal n) {
        return n.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
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
