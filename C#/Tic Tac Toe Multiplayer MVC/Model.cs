using System;
using System.Collections.Generic;
using System.Data;
using System.Windows.Forms;

namespace TicTacToe_Game
{
    class Model
    {
        private View v;

        //  Aby mieć referencję do wszystkich buttonów z form1
        private List<Button> btns;

        //  I do innych elementów..
        private Form1 f1;

        bool canIAdd = true;

        string playerChar;

        private int lastindex = -1;

        private Database database = new Database();

        public Model(Form1 f1)
        {
            if (canIAdd)
            {
                canIAdd = false;
                this.f1 = f1;
            }
        }

        public void registerView(View v,List<Button> btns)
        {
            this.v = v;
            this.btns = btns;
        }

        public void setPlayerChar(string znak)
        {
            this.playerChar = znak;
        }

        public void setLastIndex(int index)
        {
            this.lastindex = index;
        }

        //  Odpala, gdy właściwa forma gry się włącza
        public void setPreLoad()
        {
            DataTable dt;

            string playerNick = "";

            if (playerChar == "X")
            {
                AutoClosingMessageBox.Show("Oczekiwanie na przeciwnika", "Oczekiwanie...", 1000);
                do
                {
                    dt = database.readPlayer2_nick("player2", this.lastindex);

                    DateTime then = DateTime.Now;

                    do { } while (then.AddSeconds(1) > DateTime.Now);

                    playerNick = dt.Rows[0]["player2_name"].ToString();

                } while (dt.Rows.Count == 0 || playerNick == "");     // Wykonuj dopóki player2 nie poda swojego nicku

                AutoClosingMessageBox.Show("Połączono z przeciwnikiem o nicku: " + playerNick, "Połączono!", 1000);

                AutoClosingMessageBox.Show("Grasz jako: X","Informacja", 1000);

                f1.turnLbl.Text = "Twój ruch";

            }
            else if (playerChar == "O")
                {
                AutoClosingMessageBox.Show("Oczekiwanie na przeciwnika", "Oczekiwanie...", 1000);
                do
                {                 
                    dt = database.readPlayer2_nick("player1", this.lastindex);

                    DateTime then = DateTime.Now;

                    do { } while (then.AddSeconds(1) > DateTime.Now);

                    playerNick = dt.Rows[0]["player1_name"].ToString();

                } while (dt.Rows.Count == 0 || playerNick == "");     // Wykonuj dopóki player2 nie poda swojego nicku

                AutoClosingMessageBox.Show("Połączono z przeciwnikiem o nicku: " + playerNick, "Połączono!", 1000);

                AutoClosingMessageBox.Show("Grasz jako: O", "Informacja", 1000);

                f1.turnLbl.Text = "Ruch przeciwnika";

            }
        }

        // Czekamy na to, aż pole w bazie się zmieni na nasz znak
        public void waitForTurn()
        {
            string actualTurn = "";

            actualTurn = database.readWhichTurn(this.lastindex);

            if (playerChar == actualTurn)
            {

                if (playerChar == "X")
                {
                    f1.turnLbl.Text = "Twój ruch";

                    v.myTurn(btns);

                    Button used_button = database.readUsedButtons("player2", lastindex, btns);

                    if (used_button != null)
                    {
                        v.notifyChanges(used_button, "O");
                        checkForWinner();
                    }
                }
                else if (playerChar == "O")
                {
                    f1.turnLbl.Text = "Twój ruch";

                    v.myTurn(btns);

                    Button used_button = database.readUsedButtons("player1", lastindex, btns);

                    if (used_button != null)
                    {
                        v.notifyChanges(used_button, "X");
                        checkForWinner();
                    }
                }
            }
            else
                v.opponentsTurn(btns);
        }

        //  Modyfikuje buttony i zapisuje ostatnio zmodyfikowany do bazy
        public void set(Button btn)
        {
            if (playerChar == "X")
            {
                f1.turnLbl.Text = "";

                Button used_button = database.readUsedButtons("player2", lastindex, btns);

                if (used_button != null)
                v.notifyChanges(used_button,"O");
                
                v.notifyChanges(btn, "X");

                f1.turnLbl.Text = "Ruch przeciwnika.";

                database.writeUsedButton("player1", btn, lastindex);

                database.updateTurn("O", lastindex);

                v.opponentsTurn(btns);
            }
            else if (playerChar == "O")
            {
                f1.turnLbl.Text = "";

                Button used_button = database.readUsedButtons("player1", lastindex, btns);

                if (used_button != null)
                v.notifyChanges(used_button, "X");

                v.notifyChanges(btn, "O");

                f1.turnLbl.Text = "Ruch przeciwnika.";

                database.writeUsedButton("player2", btn, lastindex);

                database.updateTurn("X", lastindex);

                v.opponentsTurn(btns);            
            }

            checkForWinner();
        }

        //  Sprawdzamy warunki wygranej
        private void checkForWinner()
        {
            bool we_have_winner = false;
            string winner = "";

            // Ułożenie buttonów
            // A1 A2 A3 --> btns[8] btns[7] btns[6] 
            // B1 B2 B3 --> btns[5] btns[4] btns[3]
            // C1 C2 C3 --> btns[2] btns[1] btns[0]

            //horizontal checks
            if ((btns[0].Text == btns[1].Text) && (btns[1].Text == btns[2].Text) && btns[0].Text != "")
            {
                we_have_winner = true;
                winner = btns[0].Text;
            }
            else if ((btns[3].Text == btns[4].Text) && (btns[4].Text == btns[5].Text) && btns[3].Text != "")
            {
                we_have_winner = true;
                winner = btns[3].Text;
            }
            else if ((btns[6].Text == btns[7].Text) && (btns[7].Text == btns[8].Text) && btns[6].Text != "")
            {
                we_have_winner = true;
                winner = btns[6].Text;
            }
            //vertical checks
            else if ((btns[0].Text == btns[3].Text) && (btns[3].Text == btns[6].Text) && btns[0].Text != "")
            {
                we_have_winner = true;
                winner = btns[0].Text;
            }
            else if ((btns[1].Text == btns[4].Text) && (btns[4].Text == btns[7].Text) && btns[1].Text != "")
            {
                we_have_winner = true;
                winner = btns[1].Text;
            }
            else if ((btns[2].Text == btns[5].Text) && (btns[5].Text == btns[8].Text) && btns[2].Text != "")
            {
                we_have_winner = true;
                winner = btns[2].Text;
            }
            //diagonal checks
            else if ((btns[0].Text == btns[4].Text) && (btns[4].Text == btns[8].Text) && btns[0].Text != "")
            {
                we_have_winner = true;
                winner = btns[0].Text;
            }
            else if ((btns[2].Text == btns[4].Text) && (btns[4].Text == btns[6].Text) && btns[2].Text != "")
            {
                we_have_winner = true;
                winner = btns[2].Text;
            }

            if (we_have_winner)
            {
                f1.MouseMove -= new System.Windows.Forms.MouseEventHandler(f1.Form1_MouseMove);
                v.opponentsTurn(btns);
                v.showWinner(winner);
                f1.turnLbl.Text = winner + " jest zwycięzcą!";
                database.setWhoWon(winner, lastindex);
            }
            else if (!we_have_winner && v.ifAllButtonsHaveText(btns))
            {
                f1.MouseMove -= new System.Windows.Forms.MouseEventHandler(f1.Form1_MouseMove);
                v.showNoOneWin();
                f1.turnLbl.Text = "Partia nie roztrzygnięta.";

                database.setWhoWon("No one won.", lastindex);
            }
        }

        //  Nowa gra
        public void setNewGame(ToolStripMenuItem tsmi)
        {
            database.deleteUsersTable();
            Application.Restart();
            Environment.Exit(0);
        }

        //  MessageBox, który znika po określonym czasie
        public class AutoClosingMessageBox
        {
            System.Threading.Timer _timeoutTimer;
            readonly string _caption;
            AutoClosingMessageBox(string text, string caption, int timeout)
            {
                _caption = caption;
                _timeoutTimer = new System.Threading.Timer(OnTimerElapsed,
                    null, timeout, System.Threading.Timeout.Infinite);
                MessageBox.Show(text, caption);
            }
            public static void Show(string text, string caption, int timeout)
            {
                new AutoClosingMessageBox(text, caption, timeout);
            }
            void OnTimerElapsed(object state)
            {
                IntPtr mbWnd = FindWindow(null, _caption);
                if (mbWnd != IntPtr.Zero)
                    SendMessage(mbWnd, WM_CLOSE, IntPtr.Zero, IntPtr.Zero);
                _timeoutTimer.Dispose();
            }
            const int WM_CLOSE = 0x0010;
            [System.Runtime.InteropServices.DllImport("user32.dll", SetLastError = true)]
            static extern IntPtr FindWindow(string lpClassName, string lpWindowName);
            [System.Runtime.InteropServices.DllImport("user32.dll", CharSet = System.Runtime.InteropServices.CharSet.Auto)]
            static extern IntPtr SendMessage(IntPtr hWnd, UInt32 Msg, IntPtr wParam, IntPtr lParam);
        }

        //  Usuwany Userów
        public void deleteTableContent()
        {
            database.deleteUsersTable();
        }


    }
}
