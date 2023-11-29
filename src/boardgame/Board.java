package boardgame;

public class Board {

	private Integer rows;
	private Integer columns;
	private Piece[][] pieces; //matriz de pe�as 
	
	public Board(Integer rows, Integer columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];//instaciada por linhas e colunas informadas
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) { //metodo que retorna na linha e coluna 
		return pieces[row][column];
	}
	public Piece piece(Position position) { //metodo que retorna a posi��o
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {//pega a posi��o dada a matriz, e insere a pe�a que ser� informada 
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; //pe�a n�o � mais nula
	}
	
}
