package exercicis

import com.squareup.moshi.Moshi
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.sql.DriverManager

fun main() {
    val url = "jdbc:sqlite:Rutes.sqlite"
    val con = DriverManager.getConnection(url)
    val st = con.createStatement()
    val obj = ObjectInputStream(FileInputStream("Rutes.obj"))
    var sentSQL = ""
    var sentSQLpunt = ""
    var pkey = 0;
    try {
        while (true) {
            val r = obj.readObject() as Ruta
            pkey++
            sentSQL = "INSERT INTO RUTES VALUES (" +
                    "$pkey  ," +
                    "'${r.nom}' ," +
                    "${r.desnivell} ," +
                    "${r.desnivellAcumulat} " +
                    ")"

            println(sentSQL)
            st.executeUpdate(sentSQL)

            for (i in 0 until r.llistaDePunts.size) {
                sentSQLpunt = "INSERT INTO PUNTS VALUES (" +
                        "$pkey  ," +
                        "${i + 1}," +
                        "'${r.llistaDePunts[i].nom}' ," +
                        "${r.getPuntLatitud(i)} ," +
                        "${r.getPuntLongitud(i)} " +
                        ")"
                println(sentSQLpunt)
                st.executeUpdate(sentSQLpunt)
            }
        }
    } catch (eof: EOFException) {
        obj.close()
    } finally {
        st.close()
        con.close()
    }
}