package boardgame;

public class Board {

	private Integer rows;
	private Integer columns;
	private Piece[][] pieces; //matriz de peças 
	
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
	public Piece piece(Position position) { //metodo que retorna a posição
		return pieces[position.getRow()][position.getColumn()];
	}
	
	
}
