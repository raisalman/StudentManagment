package com.example.assignmentalerts.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentalerts.R;
import com.example.assignmentalerts.ui.Models.WorkList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.ViewHolder> {

    private final ArrayList<WorkList> workLists;
   private String key ;
    DatabaseReference myRef;
    Context context;

    String topic_upd,subject_upd,deadline_upd,type_upd;

    public WorkListAdapter(ArrayList<WorkList> workLists, Context context) {
        this.workLists = workLists;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        WorkList workList = workLists.get(position);

        holder.topic.setText(workList.getTopic());
        holder.subject.setText(workList.getSubject());
        holder.deadLine.setText(workList.getDeadLine());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                topic_upd = workLists.get(position).getTopic();
                subject_upd = workLists.get(position).getSubject();
                deadline_upd = workLists.get(position).getDeadLine();
                type_upd = workLists.get(position).getType();
                key = workLists.get(position).getWork_id();
                customDialog();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return workLists.size();
    }

    private void customDialog() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.update_delete_layout, null);
        AlertDialog dialog = myDialog.create();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        dialog.setView(view);
        dialog.show();

        EditText etTopic, etSubject, etDeadline;
        Button btnUpdate,btnDelete;
        ImageButton btnClose;

        etTopic = view.findViewById(R.id.etTopic_upd);
        etSubject = view.findViewById(R.id.etSubject_upd);
        etDeadline = view.findViewById(R.id.etDeadline_upd);

        etTopic.setText(topic_upd);
        etSubject.setText(subject_upd);
        etDeadline.setText(deadline_upd);

        btnDelete=view.findViewById(R.id.btnDelete_upd);
        btnUpdate=view.findViewById(R.id.btnUpdate_upd);
        btnClose = view.findViewById(R.id.btnClose_upd);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic=etTopic.getText().toString().trim();
                String subject=etSubject.getText().toString().trim();
                String deadline=etDeadline.getText().toString().trim();


                if(type_upd.equals("Quiz")){

                    myRef = database.getReference().child("Quizzes");

                }else if(type_upd.equals("Assignment")) {

                    myRef = database.getReference().child("Assignments");

                }

                HashMap<String, Object> obj = new HashMap<>();
                obj.put("topic", topic);
                obj.put("subject", subject);
                obj.put("deadLine", deadline);

                    myRef.child(key).updateChildren(obj);
                            notifyDataSetChanged();

                    Toast.makeText(context, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type_upd.equals("Quiz")){

                    myRef = database.getReference().child("Quizzes");

                }else if(type_upd.equals("Assignment")) {

                    myRef = database.getReference().child("Assignments");

                }

                myRef.child(key).removeValue();

                        notifyDataSetChanged();

                Toast.makeText(context, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        ConstraintLayout layout;
        TextView topic, subject, deadLine;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.detailRecView);
            topic = itemView.findViewById(R.id.txtTopic);
            subject = itemView.findViewById(R.id.txtSubject);
            deadLine = itemView.findViewById(R.id.txtDeadline);
            layout = itemView.findViewById(R.id.parentLayout);

        }
    }
}


