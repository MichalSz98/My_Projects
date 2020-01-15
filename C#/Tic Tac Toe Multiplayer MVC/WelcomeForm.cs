using System;
using System.Data;
using System.Windows.Forms;

namespace TicTacToe_Game
{
    public partial class WelcomeForm : Form
    {
        private Database database = new Database();

        Form1 f1;

        public WelcomeForm()
        {
            InitializeComponent();
        }

        public WelcomeForm(Form1 f1)
        {
            InitializeComponent();

            this.f1 = f1;
        }

        // Start button
        private void button1_Click(object sender, EventArgs e)
        {
            if (nickTxtBox.Text.Length >= 2)
            {
                string nick = nickTxtBox.Text;

                //Czytaj ile wierszy w tabeli Users
                DataTable dt = database.readFromTable("Users");

                f1 = new Form1();

                if (dt.Rows.Count == 0)         // Pierwszy się dostał
                {
                    database.addUser(nick);

                    string znak = database.addRow(nick);      //  Który pierwszy się dostanie, ten ustawia wszystko dla drugiego

                    f1.setPlayerChar(znak);
                }
                else if (dt.Rows.Count == 1)    //  Drugi tylko robi update to dodanego przez pierwszego wiersza, zmieniając swój nick
                {
                    database.readLastIndex();

                    database.addUser(nick);

                    string znak = database.updatePlayer2Name(nick);

                    f1.setPlayerChar(znak);
                }
                else if (dt.Rows.Count == 2)
                {
                    MessageBox.Show("Aktualnie rozgrywa się partia. Musisz zaczekać...");
                    return;
                }

                int index = database.readLastIndex();
                f1.setLastIndex(index);

                this.Hide();

                f1.preLoad();

                f1.Show();
            }
            else
                MessageBox.Show("Nick musi składać się z conajmniej 2 znaków.");
        }

        private void WelcomeForm_FormClosing(object sender, FormClosingEventArgs e)
        {
                Application.Exit();
        }
    }
}
