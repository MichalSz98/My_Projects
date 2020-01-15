using System;
using System.Data;
using System.Collections.Generic;
using System.Windows.Forms;
using MySql.Data.MySqlClient;

namespace TicTacToe_Game
{
    class Database
    {
        private MySqlConnection SQLconnetion;

        private string connectionString = "Server = xxxxxx; Database = xxxxx; User id = xxxxxx; Password=xxxxxx";

        private int lastIndex;

        public Database()
        {
            SQLconnetion = new MySqlConnection(connectionString);
        }

        //  Ustaw kto czym gra (ustawia zawsze Player1)
        public string[] setPlayers()
        {
            Random rnd = new Random();
            //int result = rnd.Next(2);  // los z liczb między 0, 1
            
            // zmiana koncepcji :)
            int result = 0;

            string[] random = new string[2];


            if (result == 0)
            {
                random[0] = "X";    //  Dla Playera1
                random[1] = "O";    //  Dla Playera2
            }
            else if (result == 1)
            {
                random[0] = "O";    //  Dla Playera1
                random[1] = "X";    //  Dla Playera2
            }

            return random;
        }

        //  Czytaj użyte buttony
        public Button readUsedButtons(string Player, int lastIndex, List<Button> btns)
        {
            SQLconnetion.Open();

            DataTable dt = new DataTable();
            MySqlDataAdapter da = new MySqlDataAdapter("START TRANSACTION; SELECT " + Player + "_used_buttons " + " FROM `TicTacToeGame` WHERE `id` =" + lastIndex + "; COMMIT;", SQLconnetion);

            da.Fill(dt);

            SQLconnetion.Close();

            Button resultButton = new Button();

            string t = Player + "_used_buttons";

            string res = dt.Rows[0][t].ToString();  // Nazwa buttona, który został użyty

            if (res != "")
            {   
                switch (res)
                {
                    case "A1": return btns[8];
                    case "A2": return btns[7];
                    case "A3": return btns[6];
                    case "B1": return btns[5];
                    case "B2": return btns[4];
                    case "B3": return btns[3];
                    case "C1": return btns[2];
                    case "C2": return btns[1];
                    case "C3": return btns[0];
                }             
            }
            return null;
        }

        //  Dopisz button w tabeli
        public void writeUsedButton(string Player, Button btn, int lastIndex)
        {
            using (var connection = new MySqlConnection(connectionString))
            {
                connection.Open();
                var sqlQuery = "START TRANSACTION; UPDATE `TicTacToeGame` SET `" + Player + "_used_buttons` " + "= @btn WHERE `TicTacToeGame`.`id` = " + lastIndex + "; COMMIT;"; ;
                using (var cmd = new MySqlCommand(sqlQuery, connection))
                {
                    cmd.Parameters.AddWithValue("@btn", btn.Name);
                    cmd.Parameters.AddWithValue("@id", lastIndex);
                    cmd.ExecuteNonQuery();
                }
                connection.Close();
            }
        }

        //  Czyta ostatni index z tabeli TicTacToeGame
        public int readLastIndex()
        {
            SQLconnetion.Open();

            DataTable dt = new DataTable();
            MySqlDataAdapter da = new MySqlDataAdapter("START TRANSACTION; SELECT * FROM `TicTacToeGame` WHERE `id` = (SELECT MAX(`id`) FROM `TicTacToeGame`); COMMIT;", SQLconnetion);

            da.Fill(dt);

            SQLconnetion.Close(); 

            int lastIndex = Convert.ToInt32(dt.Rows[0]["id"]);

            this.lastIndex  = Convert.ToInt32(dt.Rows[0]["id"]);

            return lastIndex;
        }

        //    Czyta nick playera
        public DataTable readPlayer2_nick(string playerNick, int lastIndex)
        {
            SQLconnetion.Open();

            DataTable dt = new DataTable();
            MySqlDataAdapter da = new MySqlDataAdapter("START TRANSACTION; SELECT `" + playerNick + "_name` FROM `TicTacToeGame` WHERE `id` = " + lastIndex + " ; COMMIT;", SQLconnetion);

            da.Fill(dt);

            SQLconnetion.Close();

            return dt;
        }

        //  Czyta, który gracz się teraz rusza
        public string readWhichTurn(int lastIndex)
        {
                SQLconnetion.Open();

                DataTable dt = new DataTable();
                MySqlDataAdapter da = new MySqlDataAdapter("START TRANSACTION; SELECT `turn` FROM `TicTacToeGame` WHERE `id` =" + lastIndex + "; COMMIT;", SQLconnetion);

                da.Fill(dt);

                SQLconnetion.Close();

                string result = dt.Rows[0]["turn"].ToString();

                return result;
        }

        //  Pobierz znak (kółko, lub krzyżyk) dla Playera 2
        public string readPlayer2Char()
        {
            SQLconnetion.Open();

            DataTable dt = new DataTable();
            MySqlDataAdapter da = new MySqlDataAdapter("START TRANSACTION; SELECT `player2` FROM `TicTacToeGame` WHERE `id` =" + lastIndex + "; COMMIT;", SQLconnetion);

            da.Fill(dt);

            SQLconnetion.Close();

            string result = dt.Rows[0]["player2"].ToString();

            return result;
        }

        //  Czytaj wszystkie rekordy z tabeli tableName
        public DataTable readFromTable(string tableName)
        {
            SQLconnetion.Open();

            DataTable dt = new DataTable();
            MySqlDataAdapter da = new MySqlDataAdapter("START TRANSACTION; SELECT * FROM " + "`" + tableName + "`; COMMIT;", SQLconnetion);

            da.Fill(dt);

            SQLconnetion.Close();

            return dt;
        }

        //  Dodaje rekord w tabeli Users (pomocniczna tabela)
        public void addUser(string nick)
        {
            using (var connection = new MySqlConnection(connectionString))
            {
                connection.Open();
                var sqlQuery = "START TRANSACTION; INSERT INTO `Users` (`nick`) VALUES (@nick); COMMIT;";
                using (var cmd = new MySqlCommand(sqlQuery, connection))
                {
                    cmd.Parameters.AddWithValue("@nick", nick);
                    cmd.ExecuteNonQuery();
                }
                connection.Close();
            }
        }

        //  Dodaje rekord w tabeli TicTacToeGame (czyli tabela właściwej rozgrywki)
        public string addRow(string nick)
        {
            string[] players = setPlayers();

            using (var connection = new MySqlConnection(connectionString))
            {
                connection.Open();
                var sqlQuery = "START TRANSACTION; INSERT INTO `TicTacToeGame` (`id`, `turn`, `player1`, `player2`, `player1_name`, `player2_name`) VALUES (NULL, @valueTurn, @player1, @player2, @player1Nick, ''); COMMIT;";
                using (var cmd = new MySqlCommand(sqlQuery, connection))
                {
                    cmd.Parameters.AddWithValue("@valueTurn", players[0]);
                    cmd.Parameters.AddWithValue("@player1", players[0]);
                    cmd.Parameters.AddWithValue("@player2", players[1]);
                    cmd.Parameters.AddWithValue("@player1Nick", nick);
                    cmd.ExecuteNonQuery();
                }
                connection.Close();
            }
            return players[0];      // Zwracamy czym gra Player1
        }
        
        //  Zrób update nicku Playera2
        public string updatePlayer2Name(string nick)
        {
            if (lastIndex != -1)
            {
                using (var connection = new MySqlConnection(connectionString))
                {
                    connection.Open();
                    var sqlQuery = "START TRANSACTION; UPDATE `TicTacToeGame` SET `player2_name` = @player2Nick WHERE `TicTacToeGame`.`id` = @id; COMMIT;"; ;
                    using (var cmd = new MySqlCommand(sqlQuery, connection))
                    {
                        cmd.Parameters.AddWithValue("@player2Nick", nick);
                        cmd.Parameters.AddWithValue("@id", lastIndex);
                        cmd.ExecuteNonQuery();
                    }
                    connection.Close();
                }
            }
            string ch = readPlayer2Char();

            return ch;
        }

        //  Updatuje to, kto się teraz będzie ruszał
        public void updateTurn(string znak,int lastIndex)
        {
            using (var connection = new MySqlConnection(connectionString))
            {
                connection.Open();
                var sqlQuery = "START TRANSACTION; UPDATE `TicTacToeGame` SET `turn` = @znak WHERE `TicTacToeGame`.`id` = @id; COMMIT;"; ;
                using (var cmd = new MySqlCommand(sqlQuery, connection))
                {
                    cmd.Parameters.AddWithValue("@znak", znak);
                    cmd.Parameters.AddWithValue("@id", lastIndex);
                    cmd.ExecuteNonQuery();
                }
                connection.Close();
            }
        }

        public void deleteUsersTable()
        {
            using (var connection = new MySqlConnection(connectionString))
            {
                connection.Open();
                var sqlQuery = "DELETE FROM `Users`"; ;
                using (var cmd = new MySqlCommand(sqlQuery, connection))
                {
                    cmd.ExecuteNonQuery();
                }
                connection.Close();
            }

           /* using (var connection = new MySqlConnection(connectionString))
            {
                connection.Open();
                var sqlQuery = "DELETE FROM `TicTacToeGame`"; ;
                using (var cmd = new MySqlCommand(sqlQuery, connection))
                {
                    cmd.ExecuteNonQuery();
                }
                connection.Close();
            }*/
        }

        public void setWhoWon(string znak, int lastIndex)
        {
            using (var connection = new MySqlConnection(connectionString))
            {
                connection.Open();
                var sqlQuery = "START TRANSACTION; UPDATE `TicTacToeGame` SET `whoWon` = @whoWon WHERE `TicTacToeGame`.`id` = @id; COMMIT;"; ;
                using (var cmd = new MySqlCommand(sqlQuery, connection))
                {
                    cmd.Parameters.AddWithValue("@whoWon", znak);
                    cmd.Parameters.AddWithValue("@id", lastIndex);
                    cmd.ExecuteNonQuery();
                }
                connection.Close();
            }
        }

    }
}
