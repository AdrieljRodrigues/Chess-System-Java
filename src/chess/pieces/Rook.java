package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	public Rook(Board board, Color color) {//repassa achamada para a super classe
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "R"; //onde estiver essa peça, ela ira aparecer no tabuleir por essa letra
	}
	

}
