package com.vinay.chatapp.Adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.vinay.chatapp.Models.MessageModel;
import com.vinay.chatapp.R;
import java.util.ArrayList;
public class MessageAdapter extends RecyclerView.Adapter{
    ArrayList<MessageModel> message;
    Context context;
    final int RECV_ID=1;
    final int SEND_ID=2;
    public MessageAdapter(ArrayList<MessageModel> message, Context context) {
        this.message=message;
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SEND_ID)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sender_sample,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.receiver_sample,parent,false);
            return new ReceiverViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel model=message.get(position);
        if(holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).sendmsg.setText(model.getMessage());
            ((SenderViewHolder)holder).sendtime.setText(model.getTimestamp());
        }
        else
        {
            ((ReceiverViewHolder)holder).recvmsg.setText(model.getMessage());
            ((ReceiverViewHolder)holder).recvtime.setText(model.getTimestamp());
        }
    }
    @Override
    public int getItemCount() {
        return message.size();
    }
    @Override
    public int getItemViewType(int position) {
        if(message.get(position).getUid().equals(FirebaseAuth.getInstance().getUid()))
            return SEND_ID;
        else
            return RECV_ID;
    }
    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView recvmsg,recvtime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            recvmsg=itemView.findViewById(R.id.recvmsg);
            recvtime=itemView.findViewById(R.id.recvtime);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView sendmsg,sendtime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sendmsg=itemView.findViewById(R.id.sendmsg);
            sendtime=itemView.findViewById(R.id.sendtime);
        }
    }
}