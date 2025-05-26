package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;

public class ProgramTest {

    private Game game;
    private Player playerX, playerO;

    @BeforeEach
    void setUp() {
        game = new Game();
        playerX = game.player1;
        playerO = game.player2;
        playerX.symbol = 'X';
        playerO.symbol = 'O';
    }
    
    @Test
    void testCheckStateXWins() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWins() {
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDraw() {
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testGenerateMovesPartiallyFilled() {
        char[] board = {'X', ' ', 'O', ' ', 'X', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(6, moves.size());
    }

    @Test
    void testEvaluatePositionXWins() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, playerX));
    }

    @Test
    void testEvaluatePositionDraw() {
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, playerX));
    }

    @Test
    void testMiniMaxBlocksOpponent() {
        char[] board = {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};
        int bestMove = game.MiniMax(board, playerO);
        assertTrue(bestMove >= 1 && bestMove <= 9);
    }

    @Test
    void testFullBoardNoWinner() {
        char[] board = {'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testMiniMaxEmptyBoard() {
        char[] board = {' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '};
        int move = game.MiniMax(board, playerX);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testBlockOpponentWin() {
        char[] board = {'X', 'X', ' ',
                'O', 'O', ' ',
                ' ', ' ', ' '};
        int move = game.MiniMax(board, playerO);
        assertEquals(3, move); // Должен заблокировать победу X
    }
}