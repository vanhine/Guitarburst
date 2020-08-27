package com.mrwinston.guitarburst.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrwinston.guitarburst.R
import com.mrwinston.guitarburst.data.model.Piece
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.zip.Inflater
import javax.inject.Inject

class PiecesAdapter(private val context: Context, private var pieces: List<Piece>) :
    RecyclerView.Adapter<PiecesAdapter.PieceViewHolder>() {
    class PieceViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.piece, parent, false)) {
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val composerView: TextView = itemView.findViewById(R.id.composer)

        fun bind(piece: Piece) {
            titleView.text = piece.title
            composerView.text = piece.composer
        }
    }

    fun setPieces(inbound_pieces: List<Piece>) {
        Log.d("Adapter", "returning ${inbound_pieces.size} pieces")
        pieces = inbound_pieces
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PieceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PieceViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = pieces.size

    override fun onBindViewHolder(holder: PieceViewHolder, position: Int) {
        val piece: Piece = pieces[position]
        holder.bind(piece)
    }
}