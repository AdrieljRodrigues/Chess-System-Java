
package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

//classe principal para o jogo
public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
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
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
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
		Piece capturedPiece = makeMove(source, target);
		
		if (testCheck(currentPlayer)) {//nao deixa se colocar em check
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}

		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		// #specialmove promotion
				promoted = null;
				if (movedPiece instanceof Pawn) {
					if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
						promoted = (ChessPiece)board.piece(target);
						promoted = replacePromotedPiece("Q");
					}
				}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if (testCheckMate(opponent(currentPlayer))) {//testa se o jogo acabou
			checkMate = true;
		}
		else {
			nextTurn();//proximo jogador
		}
		
		// #specialmove en passant
				if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
					enPassantVulnerable = movedPiece;
				}
				else {
					enPassantVulnerable = null;
				}
		
		return (ChessPiece)capturedPiece;
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
			return promoted;
		}

		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);

		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);

		return newPiece;
	}

	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("N")) return new Knight(board, color);
		if (type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color);
	}
		
	
	private Piece makeMove(Position source, Position target) { //mover as peças
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target); //peça capturada
		board.placePiece(p, target);// coloca a peça de origem na posição da capturada
		
		if (capturedPiece != null) {//peça capturada diferente de nulo
			piecesOnTheBoard.remove(capturedPiece);//retira do tabuleiro
			capturedPieces.add(capturedPiece);//adiciona na peças capturadas
		}
		
		// #specialmove castling kingside rook
				if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
					Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
					Position targetT = new Position(source.getRow(), source.getColumn() + 1);
					ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
					board.placePiece(rook, targetT);
					rook.increaseMoveCount();
				}

				// #specialmove castling queenside rook
				if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
					Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
					Position targetT = new Position(source.getRow(), source.getColumn() - 1);
					ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
					board.placePiece(rook, targetT);
					rook.increaseMoveCount();
				}		
				
				// #specialmove en passant
				if (p instanceof Pawn) {
					if (source.getColumn() != target.getColumn() && capturedPiece == null) {
						Position pawnPosition;
						if (p.getColor() == Color.WHITE) {
							pawnPosition = new Position(target.getRow() + 1, target.getColumn());
						}
						else {
							pawnPosition = new Position(target.getRow() - 1, target.getColumn());
						}
						capturedPiece = board.removePiece(pawnPosition);
						capturedPieces.add(capturedPiece);
						piecesOnTheBoard.remove(capturedPiece);
					}
				}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {//desfaz o movimento
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) { //desfaz peça capturada
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// #specialmove castling kingside rook
				if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
					Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
					Position targetT = new Position(source.getRow(), source.getColumn() + 1);
					ChessPiece rook = (ChessPiece)board.removePiece(targetT);
					board.placePiece(rook, sourceT);
					rook.decreaseMoveCount();
				}

				// #specialmove castling queenside rook
				if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
					Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
					Position targetT = new Position(source.getRow(), source.getColumn() - 1);
					ChessPiece rook = (ChessPiece)board.removePiece(targetT);
					board.placePiece(rook, sourceT);
					rook.decreaseMoveCount();
				}
		
				// #specialmove en passant
				if (p instanceof Pawn) {
					if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
						ChessPiece pawn = (ChessPiece)board.removePiece(target);
						Position pawnPosition;
						if (p.getColor() == Color.WHITE) {
							pawnPosition = new Position(3, target.getColumn());
						}
						else {
							pawnPosition = new Position(4, target.getColumn());
						}
						board.placePiece(pawn, pawnPosition);
					}
				}
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

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) {//testa se o rei esta em check
		Position kingPosition = king(color).getChessPosition().toPosition();//pega aposição do rei em formato de matriz
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());//lista de peças do oponente
		for (Piece p : opponentPieces) {//testa os movimentos do adversario
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {//testa se nao estar em check
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());//lista as peças da mesma cor
		for (Piece p : list) {//percorre a lista
			boolean[][] mat = p.possibleMoves();//percorre a matriz
			for (int i=0; i<board.getRows(); i++) {//percorre as linhas da matriz
				for (int j=0; j<board.getColumns(); j++) {//percorre as colunas da matriz
					if (mat[i][j]) {//testa se o movimento o tira d check
						Position source = ((ChessPiece)p).getChessPosition().toPosition();//converte a posição
						Position target = new Position(i, j);//moviemnto possivel
						Piece capturedPiece = makeMove(source, target);//faz o movimento
						boolean testCheck = testCheck(color);//testa o check
						undoMove(source, target, capturedPiece);//desfaz o movimento
						if (!testCheck) {//testa se estava em check
							return false;
						}
					}
				}
			}
		}
		return true;//check mate
	}	
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);//peças no tabuleiro
	}
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
	    placeNewPiece('h', 1, new Rook(board, Color.WHITE));
	    placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
       
	    placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
	    placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
	    placeNewPiece('d', 8, new Queen(board, Color.BLACK));
	    placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}