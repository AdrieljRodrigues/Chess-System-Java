package boardgame;

public class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() { //set é excluido para não haver alteração pelo tabuleiro
		return board;
	}//protected para somente classes do mesmo pacote poder acessar o tabuleiro de uma peça
	
	
	
	
}
