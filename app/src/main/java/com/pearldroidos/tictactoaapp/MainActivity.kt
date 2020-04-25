package com.pearldroidos.tictactoaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var activePlayer = 1
    private var playerOneWinCounts = 0
    private var playerTwoWinCounts = 0
    private var playerOne = ArrayList<Int>()
    private var playerTwo = ArrayList<Int>()
    private var notWin = true
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btnClick(view: View?) {
        val btnSelected = view as Button

        if(notWin) {
            var cellID = 0
            when (btnSelected.id) {
                R.id.button -> cellID = 1
                R.id.button2 -> cellID = 2
                R.id.button3 -> cellID = 3
                R.id.button4 -> cellID = 4
                R.id.button5 -> cellID = 5
                R.id.button6 -> cellID = 6
                R.id.button7 -> cellID = 7
                R.id.button8 -> cellID = 8
                R.id.button9 -> cellID = 9
            }
            Log.d("BtnSelected", "Button : ${btnSelected.id}  | CellID : $cellID")

            playGame(cellID, btnSelected)
        }
    }


    private fun playGame(cellId: Int, btnSelected: Button) {
        if (activePlayer == 1) {
            btnSelected.text = "x"
            btnSelected.setBackgroundResource(R.color.colorOrange)
            playerOne.add(cellId)
            activePlayer = 2
            btnSelected.isEnabled = false
            checkWinner()
            if (notWin)
                autoPlay()
        } else {
            btnSelected.text = "o"
            btnSelected.setBackgroundResource(R.color.colorPink)
            playerTwo.add(cellId)
            activePlayer = 1
            btnSelected.isEnabled = false
            checkWinner()
        }
    }

    private fun checkWinner() {
        Log.d("BtnSelected", " Check winner")
        var winner = -1

        //row 1
        if (playerOne.contains(1) && playerOne.contains(2) && playerOne.contains(3)) {
            winner = 1
        } else if (playerTwo.contains(1) && playerTwo.contains(2) && playerTwo.contains(3)) {
            winner = 2
        }

        //row 2
        if (playerOne.contains(4) && playerOne.contains(5) && playerOne.contains(6)) {
            winner = 1
        } else if (playerTwo.contains(4) && playerTwo.contains(5) && playerTwo.contains(6)) {
            winner = 2
        }

        //row 3
        if (playerOne.contains(7) && playerOne.contains(8) && playerOne.contains(9)) {
            winner = 1
        } else if (playerTwo.contains(7) && playerTwo.contains(8) && playerTwo.contains(9)) {
            winner = 2
        }


        //Column1
        if (playerOne.contains(1) && playerOne.contains(4) && playerOne.contains(7)) {
            winner = 1
        } else if (playerTwo.contains(1) && playerTwo.contains(4) && playerTwo.contains(7)) {
            winner = 2
        }

        //Column2
        if (playerOne.contains(2) && playerOne.contains(5) && playerOne.contains(8)) {
            winner = 1
        } else if (playerTwo.contains(2) && playerTwo.contains(5) && playerTwo.contains(8)) {
            winner = 2
        }

        //Column3
        if (playerOne.contains(3) && playerOne.contains(6) && playerOne.contains(9)) {
            winner = 1
        } else if (playerTwo.contains(3) && playerTwo.contains(6) && playerTwo.contains(9)) {
            winner = 2
        }

        //Atilt line to right
        if (playerOne.contains(1) && playerOne.contains(5) && playerOne.contains(9)) {
            winner = 1
        } else if (playerTwo.contains(1) && playerTwo.contains(5) && playerTwo.contains(9)) {
            winner = 2
        }

        //Atilt line to left
        if (playerOne.contains(3) && playerOne.contains(5) && playerOne.contains(7)) {
            winner = 1
        } else if (playerTwo.contains(3) && playerTwo.contains(5) && playerTwo.contains(7)) {
            winner = 2
        }

        when {
            winner == 1 -> {
                Toast.makeText(this, "Player 1 wins the game", Toast.LENGTH_SHORT).show()
                notWin = false
                playerOneWinCounts += 1
                txtPlayerOne.text = playerOneWinCounts.toString()
                handler.postDelayed({
                    Toast.makeText(this, "New Game !!!", Toast.LENGTH_SHORT).show()
                    restartGame()
                }, 1500)
            }
            winner == 2 -> {
                Toast.makeText(this, "Player 2 wins the game", Toast.LENGTH_SHORT).show()
                notWin = false
                playerTwoWinCounts += 1
                txtPlayerTwo.text = playerTwoWinCounts.toString()
                handler.postDelayed({
                    Toast.makeText(this, "New Game !!!", Toast.LENGTH_SHORT).show()
                    restartGame()
                }, 1500)
            }
            playerOne.size + playerTwo.size == 9 -> {
                notWin = false
                Toast.makeText(this, "Player 1 draws Player 2 in the game", Toast.LENGTH_SHORT)
                    .show()
                handler.postDelayed({
                    Toast.makeText(this, "New Game !!!", Toast.LENGTH_SHORT).show()
                    restartGame()
                }, 1500)
            }
        }

    }


    private fun autoPlay() {
        val emptyCells = ArrayList<Int>()

        for (cellID in 1..9) {
            if (!(playerOne.contains(cellID) || playerTwo.contains(cellID))) {
                emptyCells.add(cellID)
            }

        }

        if (emptyCells.size == 0) {
            return
        }

        val r = Random()
        val randIndex = r.nextInt(emptyCells.size)
        val cellId = emptyCells[randIndex]

        val btnSelected: Button = when (cellId) {
            1 -> button
            2 -> button2
            3 -> button3
            4 -> button4
            5 -> button5
            6 -> button6
            7 -> button7
            8 -> button8
            9 -> button9
            else -> button
        }

        playGame(cellId, btnSelected)
    }

    private fun restartGame() {
        activePlayer = 1
        notWin = true
        playerOne.clear()
        playerTwo.clear()

        for (index in 1..9) {
            val btnSelected: Button = when (index) {
                1 -> button
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> button
            }
            btnSelected.text = ""
            btnSelected.isEnabled = true
            btnSelected.setBackgroundResource(R.color.colorWhileBtn)
        }
    }
}
