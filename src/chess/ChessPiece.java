package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece { //precisa de construtor

	private Color color;

	public ChessPiece(Board board, Color color) { //repassa ao construtor da classe Piece
		super(board);
		this.color = color;
	}

	public Color getColor() { //delete o set para n�o alterar a cor da pe�a
		return color;
	}

	protected boolean isThereOpponentPiece(Position position) { //verifica se a uma pe�a opnoneten no caminho
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
	
	
}
