package com.mrwinston.guitarburst.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.common.flogger.FluentLogger
import com.mrwinston.guitarburst.R
import com.mrwinston.guitarburst.data.model.Piece
import javax.inject.Inject

class PiecesListAdapter(private val context: Context, private var pieces: List<Piece>) :
    RecyclerView.Adapter<PiecesListAdapter.PieceViewHolder>() {

    private val logger = FluentLogger.forEnclosingClass()

    class PieceViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.piece_list_item, parent, false)) {

        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val composerView: TextView = itemView.findViewById(R.id.composer)

        fun bind(piece: Piece) {
            titleView.text = piece.title
            composerView.text = piece.composer
        }
    }

    fun setPieces(inbound_pieces: List<Piece>) {
        logger.atInfo().log("returning ${inbound_pieces.size} pieces")
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