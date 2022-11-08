package exercicis

import java.sql.DriverManager
import java.sql.SQLException

fun main() {
    val url = "jdbc:sqlite:Rutes.sqlite"
    val con = DriverManager.getConnection(url)
    val st = con.createStatement()

    val sentSQL = "CREATE TABLE RUTES(" +
            "num_r INTEGER CONSTRAINT cp_rut PRIMARY KEY, " +
            "nom_r TEXT, " +
            "desn INTEGER, " +
            "desn_ac INTEGER, " +
            ")"

    val sentSQL2 = "CREATE TABLE PUNTS(" +
            "num_r INTEGER CONSTRAINT ce_numr_rutes REFERENCES RUTES , " +
            "num_p INTEGER," +
            "nom_p TEXT," +
            "latitud REAL," +
            "longitud REAL," +
            "CONSTRAINT cp_punt PRIMARY KEY (num_r, num_p)" +
            ")"

    st.executeUpdate(sentSQL)
    st.executeUpdate(sentSQL2)

    st.close();
    con.close()
}
