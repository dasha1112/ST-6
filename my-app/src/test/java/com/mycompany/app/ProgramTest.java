package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.awt.GridLayout;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;

public class ProgramTest {
    private Game game;
    private Player player;
    
    @BeforeEach
    public void setUp() {
        game = new Game();
        player = new Player();
    }
    
    @Test
    public void testGameConstructor() {
        assertNotNull(game);
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }
    
    @Test
    public void testPlayerInitialization() {
        Player p = new Player();
        assertEquals(0, p.move);
        assertFalse(p.selected);
        assertFalse(p.win);
    }
    
    @Test
    public void testCheckStateXWinRows() {
        char[] board = {
            'X', 'X', 'X',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
        board = new char[]{
            ' ', ' ', ' ',
            'X', 'X', 'X',
            ' ', ' ', ' '
        };
        assertEquals(State.XWIN, game.checkState(board));
        board = new char[]{
            ' ', ' ', ' ',
            ' ', ' ', ' ',
            'X', 'X', 'X'
        };
        assertEquals(State.XWIN, game.checkState(board));
    }
    
    @Test
    public void testCheckStateXWinColumns() {
        char[] board = {
            'X', ' ', ' ',
            'X', ' ', ' ',
            'X', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
        
        board = new char[]{
            ' ', 'X', ' ',
            ' ', 'X', ' ',
            ' ', 'X', ' '
        };
        assertEquals(State.XWIN, game.checkState(board));
        
        board = new char[]{
            ' ', ' ', 'X',
            ' ', ' ', 'X',
            ' ', ' ', 'X'
        };
        assertEquals(State.XWIN, game.checkState(board));
    }
    
    @Test
    public void testCheckStateXWinDiagonals() {
        char[] board = {
            'X', ' ', ' ',
            ' ', 'X', ' ',
            ' ', ' ', 'X'
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
        
        board = new char[]{
            ' ', ' ', 'X',
            ' ', 'X', ' ',
            'X', ' ', ' '
        };
        assertEquals(State.XWIN, game.checkState(board));
    }
    
    @Test
    public void testCheckStateOWinConditions() {
        char[] board = {
            'O', 'O', 'O',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));

        board = new char[]{
            'O', ' ', ' ',
            'O', ' ', ' ',
            'O', ' ', ' '
        };
        assertEquals(State.OWIN, game.checkState(board));
        
        board = new char[]{
            'O', ' ', ' ',
            ' ', 'O', ' ',
            ' ', ' ', 'O'
        };
        assertEquals(State.OWIN, game.checkState(board));
    }
    
    @Test
    public void testCheckStatePlayingAndDraw() {
        char[] board = {
            'X', 'O', 'X',
            'O', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
        
        board = new char[]{
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'O'
        };
        assertEquals(State.DRAW, game.checkState(board));
    }
    
    @Test
    public void testEvaluateDraw() {
        char[] board = {
            'X', 'O', 'X',
            'X', 'X', 'O',
            'O', 'X', 'O'
        };
        assertEquals(0, game.evaluatePosition(board, game.player1));
        assertEquals(0, game.evaluatePosition(board, game.player2));
    }

    @Test
    public void testCheckStateDraw() {
        char[] board = {
            'X', 'O', 'X',
            'X', 'X', 'O',
            'O', 'X', 'O'
        };
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testGenerateMoves2() {
        char[] board = {
            'X', 'O', ' ',
            ' ', 'X', 'O',
            ' ', ' ', ' '
        };
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(5, moves.size());
        assertTrue(moves.contains(2));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
    }

    @Test
    public void testMiniMaxForWinningMove() {
        char[] board = {
            'X', 'O', 'X',
            'O', ' ', ' ',
            ' ', ' ', ' '
        };
        int move = game.MiniMax(board, game.player2);
        assertEquals(5, move);
    }

    @Test
    public void testMinMove() {
        char[] board = {
            'X', 'O', 'X',
            'O', ' ', ' ',
            ' ', ' ', ' '
        };
        assertEquals(-Game.INF, game.MinMove(board, game.player1));
    }

    @Test
    public void testMaxMove() {
        char[] board = {
            'X', 'O', 'X',
            'O', ' ', ' ',
            ' ', ' ', ' '
        };
        assertEquals(Game.INF, game.MaxMove(board, game.player2));
    }

    @Test
    public void testEvaluatePosition() {
        Player xPlayer = new Player();
        xPlayer.symbol = 'X';
        
        Player oPlayer = new Player();
        oPlayer.symbol = 'O';
        
        char[] xWinBoard = {
            'X', 'X', 'X',
            ' ', 'O', ' ',
            ' ', 'O', ' '
        };
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(xWinBoard, xPlayer));
        
        assertEquals(-Game.INF, game.evaluatePosition(xWinBoard, oPlayer));
        
        char[] oWinBoard = {
            'X', 'X', ' ',
            'O', 'O', 'O',
            'X', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(oWinBoard, oPlayer));
        
        assertEquals(-Game.INF, game.evaluatePosition(oWinBoard, xPlayer));
        
        char[] drawBoard = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'O'
        };
        assertEquals(0, game.evaluatePosition(drawBoard, xPlayer));
        assertEquals(0, game.evaluatePosition(drawBoard, oPlayer));
        
        char[] playingBoard = {
            'X', ' ', ' ',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(playingBoard, xPlayer));
    }
    
    @Test
    public void testMinMaxBestMove() {
        char[] board = {
            'X', ' ', 'X',
            'O', 'O', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        
        Player oPlayer = game.player2;
        oPlayer.symbol = 'O';
        game.symbol = oPlayer.symbol;
        
        int bestMove = game.MiniMax(game.board, oPlayer);
        assertEquals(2, bestMove);
    }
    
    @Test
    public void testMinMove2() {
        Player xPlayer = game.player1;
        xPlayer.symbol = 'X';
        
        char[] board = {
            ' ', ' ', ' ',
            'O', 'O', ' ',
            'X', ' ', ' '
        };
        game.board = board.clone();
        
        game.symbol = 'O';
        int minValue = game.MinMove(game.board, xPlayer);
        assertTrue(minValue <= Game.INF);
    }
    
    @Test
    public void testMaxMove2() {
        Player oPlayer = game.player2;
        oPlayer.symbol = 'O';
        
        char[] board = {
            'X', 'X', ' ',
            'O', 'O', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        
        game.symbol = 'O';
        int maxValue = game.MaxMove(game.board, oPlayer);
        assertTrue(maxValue >= -Game.INF);
    }
    
    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(1, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());
        
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
    
    @Test
    public void testTicTacToePanelInitialization() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
    }
    
    @Test
    public void testCompleteGame() {
        Game testGame = new Game();
        testGame.player1.symbol = 'X';
        testGame.player2.symbol = 'O';
        testGame.cplayer = testGame.player1;
        char[] finalBoard = {
            'X', 'O', 'X',
            'O', 'X', 'O',
            ' ', ' ', 'X'
        };
        testGame.board = finalBoard;
        testGame.symbol = 'X';
        testGame.state = testGame.checkState(testGame.board);
        
        assertEquals(State.XWIN, testGame.state);
    }
}