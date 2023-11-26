package src.piece;

public class ValidateMoveError {
	public static final String OUTSIDE_BOARD = "Move outside the board.";
	public static final String NO_PIECES_CAN_MOVE = "No pieces can move to this square.";
	public static final String MANY_PIECES_CAN_MOVE = "Many pieces can move to this square.";
	public static final String KING_IN_CHECK = "Can't move there, king is in check.";

	public static final String NOT_PAWN_MOVE = "Not a pawn move.";
	public static final String NOT_KNIGHT_MOVE = "Not a knight move.";
	public static final String NOT_BISHOP_MOVE = "Not a bishop move.";
	public static final String NOT_ROOK_MOVE = "Not a rook move.";
	public static final String NOT_QUEEN_MOVE = "Not a queen move.";
	public static final String NOT_KING_MOVE = "Not a king move.";

	public static final String SQUARE_NOT_FREE = "Can't capture ally.";
	public static final String FILE_NOT_FREE = "Rank is not free.";
	public static final String RANK_NOT_FREE = "Rank is not free.";
	public static final String DIAGONAL_NOT_FREE = "Diagonal is not free.";
}