package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

public class BlackPlayer extends Player{
	
	public BlackPlayer(Board board,
			Collection<Move> whiteStandardMoves,
			Collection<Move> blackStandardMoves){
		super(board,blackStandardMoves,whiteStandardMoves);
		
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {

		return Alliance.BLACK;
	}
	
	public Player getOpponent(){
		return this.board.getWhitePlayer();
		
	}
	
	@Override
	protected Collection<Move> calculateKingCastles(
			Collection<Move> playerLegals, Collection<Move> opponentLegals) {
		
		final List<Move> kingCastles=new ArrayList<>();
		
		if(!this.playerKing.isFirstMove() && !this.isInCheck()){
			if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()){
				final Tile rookTile=this.board.getTile(7);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
					
					if(Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
						Player.calculateAttacksOnTile(6,opponentLegals).isEmpty() &&
						rookTile.getPiece().getPieceType().isRook()){
							
						kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 6, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
					
					}
				}
			}
			
			if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() &&
					!this.board.getTile(3).isTileOccupied()){
				
				final Tile rookTile=this.board.getTile(0);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()
					&& Player.calculateAttacksOnTile(2, opponentLegals).isEmpty() &&
					Player.calculateAttacksOnTile(2, opponentLegals).isEmpty()&&
					rookTile.getPiece().getPieceType().isRook()){
					kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
				}
			}
		}
		
		return ImmutableList.copyOf(kingCastles);
	}
	
}