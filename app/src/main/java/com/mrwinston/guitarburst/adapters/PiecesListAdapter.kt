package com.mrwinston.guitarburst.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.common.flogger.FluentLogger
import com.mrwinston.guitarburst.R
import com.mrwinston.guitarburst.data.model.Piece
import com.mrwinston.guitarburst.viewmodel.PiecesViewModel

class PiecesListAdapter(
    private val context: Context,
    private var pieces: List<Piece>,
    private val piecesViewModel: PiecesViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<PiecesListAdapter.PieceViewHolder>() {

    private val logger = FluentLogger.forEnclosingClass()

    class PieceViewHolder(inflater: LayoutInflater, parent: ViewGroup, val context: Context) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.piece_list_item, parent, false)) {

        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val composerView: TextView = itemView.findViewById(R.id.composer)
        private val favoriteButton: ImageButton = itemView.findViewById(R.id.favorite_button)

        fun bind(piece: Piece, viewModel: PiecesViewModel, lifecycleOwner: LifecycleOwner) {
            favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.favorite_selector
                )
            )
            viewModel.isFavorite(piece).observe(lifecycleOwner, Observer {
                favoriteButton.isSelected = it
            })
            favoriteButton.setOnClickListener {
                if (!favoriteButton.isSelected) {
                    favoriteButton.isSelected = true
                    viewModel.setFavoritePiece(piece)
                } else {
                    favoriteButton.isSelected = false
                    viewModel.removeFavoritePiece(piece)
                }
            }
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
        return PieceViewHolder(inflater, parent, context)
    }

    override fun getItemCount(): Int = pieces.size

    override fun onBindViewHolder(holder: PieceViewHolder, position: Int) {
        val piece: Piece = pieces[position]
        holder.itemView.setOnClickListener {
            piecesViewModel.resultPiece = piece
            it.findNavController().navigate(R.id.action_moveToPieceInfoFragment)
        }
        holder.bind(piece, piecesViewModel, lifecycleOwner)
    }
}