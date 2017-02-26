package edu.ualr.cpsc7398.updatechecker.controller.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import edu.ualr.cpsc7398.updatechecker.R;
import edu.ualr.cpsc7398.updatechecker.controller.recyclerviewadapter.utils.EditTextLocker;
import edu.ualr.cpsc7398.updatechecker.model.UrlDataGeneralInformation;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    private static final String TAG = "InformationAdapter";
    List<UrlDataGeneralInformation> listUrlDataGeneralInformation = Collections.emptyList(); //returns immutable empty list
    Context context;

    public InformationAdapter(List<UrlDataGeneralInformation> listUrlDataGeneralInformation, Context applicationContext) {
        this.listUrlDataGeneralInformation = listUrlDataGeneralInformation;
        this.context = applicationContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //I created the viewholder for my custom row

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_information_adapter, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    //this should be called for each objects the list contains because adapter is holding that list
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        final EditText innerEditText = viewHolder.getEditText1();
        final TextView innerTextView = viewHolder.getTextView1();

        //put the models' values in row of recycler view
        innerTextView.setText(listUrlDataGeneralInformation.get(position).getName()); //TextView
        innerEditText.setText(listUrlDataGeneralInformation.get(position).getValue()); //EditText

        switch (position) {
            case 0:
                innerEditText.setRawInputType(InputType.TYPE_TEXT_VARIATION_URI); //different innerEditText
                EditTextLocker editTextLocker1 = new EditTextLocker(innerEditText); //to update editText of EditTextLocker
                editTextLocker1.updateBeanUrl(context);
                break;
            case 1:
                innerEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                EditTextLocker editTextLocker2 = new EditTextLocker(innerEditText); //to update editText of EditTextLocker
                editTextLocker2.range1to600NoLeading01(2); //2 is chars limit
                break;
            case 2:
                innerEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                //control user can only input positive numbers between 1 to 999. It means 1 to 999 seconds. If more than 300 seconds, we don't treat this URI resource as dynamic
                EditTextLocker editTextLocker3 = new EditTextLocker(innerEditText); //to update editText of EditTextLocker
                editTextLocker3.range1to600NoLeading02(1); //1 is chars limit,
                break;

            default:
                viewHolder.getEditText1().setRawInputType(InputType.TYPE_CLASS_TEXT);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    //so to insert data in recycler view, we must use this insert method of here or we can create the equivalent insert method as below
    public void insert(int position, UrlDataGeneralInformation data) {
        listUrlDataGeneralInformation.add(position, data);
//        notifyItemInserted(listUrlDataGeneralInformation.size() - 1);
        notifyItemInserted(position);
    }

    public void update(int position, UrlDataGeneralInformation data) {
        listUrlDataGeneralInformation.remove(position);
        notifyItemRemoved(position);

        insert(position, data);
    }

    //get item from predefined position
    public UrlDataGeneralInformation getItemFromPredefinedPosition(int position) {
//        for(int i = 0;i<3;i++) {
//            Log.i("i: ", listUrlDataGeneralInformation.get(i).getValue());
//        }
        return listUrlDataGeneralInformation.get(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(UrlDataGeneralInformation data) {
        int position = listUrlDataGeneralInformation.indexOf(data);
        listUrlDataGeneralInformation.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return listUrlDataGeneralInformation.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder { //can be as separate class
        private TextView textView1;
        private EditText editText1;


        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            textView1 = (TextView) v.findViewById(R.id.rowRecyclerViewInformationLayoutTextView);
            editText1 = (EditText) v.findViewById(R.id.rowRecyclerViewInformationLayoutEditText);
        }

        public EditText getEditText1() {
            return editText1;
        }

        public TextView getTextView1() {
            return textView1;
        }
    }


}
