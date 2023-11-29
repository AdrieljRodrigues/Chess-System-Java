package chess;

import boardgame.Board;

//classe principal para o jogo
public class ChessMatch {

	private Board board;
	
	public ChessMatch() {//definindo a dimenção do tabuleiro
		board = new Board(8, 8);
	}
	
	public ChessPiece[][] getPieces(){//retorna uma matriz de peças de xadrez, não permitindo o program principal acessar a classe piece
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {//
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
}
