package trungndph39729.fpoly.lab6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import trungndph39729.fpoly.lab6.databinding.ViewholderStudentBinding;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.Viewholder> {
    ArrayList<Student> list;
    Context context;
    private Handle_student listener;

    public StudentAdapter(ArrayList<Student> list,Context context, Handle_student listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderStudentBinding binding = ViewholderStudentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.Viewholder holder, int position) {

        holder.binding.msvTxt.setText("MSV: " + list.get(position).getMsv());
        holder.binding.nameTxt.setText("Name: " + (list.get(position).getFullName()));
        holder.binding.pointTxt.setText("Diem: " + (list.get(position).getPoint()));

        holder.binding.deleteBtn.setOnClickListener(view -> {
            deleteDis(list.get(position).get_id());

        });

        holder.binding.editBtn.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_update);


            EditText edMsv = (EditText) dialog.findViewById(R.id.edMsv);
            EditText edName= (EditText) dialog.findViewById(R.id.edName);
            EditText edPoint = (EditText) dialog.findViewById(R.id.edPoint);
            edMsv.setText(list.get(position).getMsv());
            edName.setText(list.get(position).getFullName());
            edPoint.setText(list.get(position).getPoint());


            Button button = (Button) dialog.findViewById(R.id.addBtn);
            button.setOnClickListener(view1 -> {
                String name = edName.getText().toString();
                String msv = edMsv.getText().toString();
                String point = edPoint.getText().toString();

                Student student = list.get(position);
                student.setFullName(name);
                student.setMsv(msv);
                student.setPoint(point);
                if (listener != null) {
                        listener.onUpdateStudent(student.get_id(),student);
                }
                dialog.dismiss();

            });


            dialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderStudentBinding binding;

        public Viewholder(ViewholderStudentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void deleteDis(String id) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Ban co chac la muon xoa khong?.");
        builder1.setCancelable(true);
        String idP = id;
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onDeleteStudent(idP);
                        }
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


}
