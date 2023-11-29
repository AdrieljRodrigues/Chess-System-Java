package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece { //precisa de construtor

	private Color color;

	public ChessPiece(Board board, Color color) { //repassa ao construtor da classe Piece
		super(board);
		this.color = color;
	}

	public Color getColor() { //delete o set para não alterar a cor da peça
		return color;
	}


	
	
}
