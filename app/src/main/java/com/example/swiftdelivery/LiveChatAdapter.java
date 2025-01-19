package com.example.swiftdelivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LiveChatAdapter extends RecyclerView.Adapter<LiveChatAdapter.ChatViewHolder> {

    private List<LiveChat> chats;

    public LiveChatAdapter(List<LiveChat> chats)
    {
        this.chats = chats;
    }

    public int getItemViewType(int pos)
    {
        LiveChat chat = chats.get(pos);
        if ("user".equalsIgnoreCase(chat.getSender())) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_sender, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_receiver, parent, false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        LiveChat chat = chats.get(position);
        holder.chatText.setText(chat.getChat());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chatText;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            chatText = itemView.findViewById(R.id.chatText);
        }
    }
}