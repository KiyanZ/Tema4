package exercicis

import java.sql.DriverManager

fun main() {
    val url = "jdbc:sqlite:Rutes.sqlite"
    val con = DriverManager.getConnection(url)
    val st = con.createStatement()

    val sqlSent = "CREATE TABLE RUTES(" +
            "num_r INTEGER, " +
            "nom_r TEXT, " +
            "desn INTEGER, " +
            "desn_ac INTEGER, " +
            "CONSTRAINT cp_rut PRIMARY KEY (num_r)"
            ")"

    val sqlSent2 = "CREATE TABLE PUNTS(" +
            "num_r INTEGER CONSTRAINT ce_numr_rutes REFERENCES RUTES , " +
            "num_p INTEGER," +
            "nom_p TEXT," +
            "latitud REAL," +
            "longitud REAL," +
            "CONSTRAINT cp_punt PRIMARY KEY (num_r, num_p)" +
            ")"

    st.executeUpdate(sqlSent2)
    st.executeUpdate(sqlSent)

    st.close()
    con.close()
}
