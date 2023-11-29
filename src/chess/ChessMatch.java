package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

//classe principal para o jogo
public class ChessMatch {

	private Board board;
	
	public ChessMatch() {//definindo a dimenção do tabuleiro
		board = new Board(8, 8);
		initialSetup();
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
	
	private void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE),new Position(2, 1));
		board.placePiece(new King(board, Color.BLACK),new Position(0, 4));
		board.placePiece(new King(board, Color.WHITE),new Position(7, 4));
	}
	
}
