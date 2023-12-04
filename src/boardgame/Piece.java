package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() { //set é excluido para não haver alteração pelo tabuleiro
		return board;
	}//protected para somente classes do mesmo pacote poder acessar o tabuleiro de uma peça
	
	public abstract boolean[][] possibleMoves();
		
	public boolean possibleMove(Position position) {//recebe uma posição, ela retorna um valor verdadeiro ou falso, se é possivel essa peça se mover para essa direção
		return possibleMoves()[position.getRow()][position.getColumn()];//metodo retornando uma matriz
	}

	public boolean isThereAnyPossibleMove() {//chama o matodo que mostra a matriz booleana, ele varre a matriz pra ver se pelo menos uma posição é verdadeira
		boolean[][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
