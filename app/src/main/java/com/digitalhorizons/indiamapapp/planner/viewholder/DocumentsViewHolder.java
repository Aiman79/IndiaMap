package com.digitalhorizons.indiamapapp.planner.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;

public class DocumentsViewHolder extends RecyclerView.ViewHolder {
    private AppCompatImageView ivDocument, ivClose;
    private AppCompatTextView tvDocumentName;
    public DocumentsViewHolder(@NonNull View itemView) {
        super(itemView);
        ivDocument = itemView.findViewById(R.id.iv_document);
        ivClose = itemView.findViewById(R.id.iv_close);
        tvDocumentName = itemView.findViewById(R.id.tv_document_name);
    }

    public AppCompatImageView getIvDocument() {
        return ivDocument;
    }

    public void setIvDocument(AppCompatImageView ivDocument) {
        this.ivDocument = ivDocument;
    }

    public AppCompatTextView getTvDocumentName() {
        return tvDocumentName;
    }

    public void setTvDocumentName(AppCompatTextView tvDocumentName) {
        this.tvDocumentName = tvDocumentName;
    }

    public AppCompatImageView getIvClose() {
        return ivClose;
    }

    public void setIvClose(AppCompatImageView ivClose) {
        this.ivClose = ivClose;
    }
}
