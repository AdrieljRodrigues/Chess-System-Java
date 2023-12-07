
package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

//classe principal para o jogo
public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();//lista de peças no tabuleiro
	private List<Piece> capturedPieces = new ArrayList<>();//lista de peças capturadas
	
	public ChessMatch() {// definindo a dimenção do tabuleiro
		board = new Board(8, 8);
		turn = 1;//inicio da partida vale 1
		currentPlayer = Color.WHITE;//inicio da partida val o branco
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public ChessPiece[][] getPieces() {// retorna uma matriz de peças de xadrez, não permitindo o program principal
										// acessar a classe piece
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();//converter essas duas posições em posições na matriz
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);//valida a posição de origem
		validateTargetPosition(source, target);//valida a posição de destino
		Piece capturePiece = makeMove(source, target);
		nextTurn();//proximo jogador
		return (ChessPiece)capturePiece;
	}
	
	private Piece makeMove(Position source, Position target) { //mover as peças
		Piece p = board.removePiece(source); //retira a peça do locar de origem
		Piece capturedPiece = board.removePiece(target); //peça capturada
		board.placePiece(p, target);// coloca a peça de origem na posição da capturada
		
		if (capturedPiece != null) {//peça capturada diferente de nulo
			piecesOnTheBoard.remove(capturedPiece);//retira do tabuleiro
			capturedPieces.add(capturedPiece);//adiciona na peças capturadas
		}
		
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) { //valida a peça na posição
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {//se for uma peça do adversario, nao pode move-la
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {//se nao tiver nenhum movimento possivel
			throw new ChessException("There is no possible moves for the chosen piece");			
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {//valida a posição de destino
		if (!board.piece(source).possibleMove(target)) {//se praa a peça de destino nao é movimento possivel
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {//proximo jogador
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;//se o jogador for branco ele, ele sera prete, se é preto sera branco
	}

	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);//peças no tabuleiro
	}
	
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}