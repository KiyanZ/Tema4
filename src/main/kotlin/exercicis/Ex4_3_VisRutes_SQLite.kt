package exercicis

import javax.swing.JFrame
import java.awt.EventQueue
import java.awt.BorderLayout
import javax.swing.JPanel
import java.awt.FlowLayout
import java.sql.DriverManager
import javax.swing.JComboBox
import javax.swing.JButton
import javax.swing.JTextArea
import javax.swing.JLabel
import javax.swing.text.Element

class Finestra : JFrame() {

    init {
        // Sentències per a fer la connexió
        val url = "jdbc:sqlite:Rutes.sqlite"
        val con = DriverManager.getConnection(url)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setTitle("JDBC: Visualitzar Rutes")
        setSize(450, 450)
        setLayout(BorderLayout())

        val panell1 = JPanel(FlowLayout())
        val panell2 = JPanel(BorderLayout())
        add(panell1, BorderLayout.NORTH)
        add(panell2, BorderLayout.CENTER)

        val llistaRutes = arrayListOf<String>()
        // Sentències per a omplir l'ArrayList amb el nom de les rutes
        val st = con.createStatement()
        val sentenciaSQL = "SELECT nom_r FROM RUTES"
        val rs = st.executeQuery(sentenciaSQL)
        while (rs.next()) {
            llistaRutes.add(rs.getString(1))
        }

        val combo = JComboBox<String>(llistaRutes.toTypedArray())
        panell1.add(combo)
        val eixir = JButton("Eixir")
        panell1.add(eixir)
        val area = JTextArea()
        panell2.add(JLabel("Llista de punts de la ruta:"), BorderLayout.NORTH)
        panell2.add(area, BorderLayout.CENTER)


        combo.addActionListener() {
            // Sentèncis quan s'ha seleccionat un element del JComboBox
            // Han de consistir en omplir el JTextArea
            val st2 = con.createStatement()
            val punt = combo.selectedItem?.toString()
            val sentenciaSQLpunts = "SELECT nom_p, latitud, longitud FROM RUTES, PUNTS WHERE RUTES.num_r = PUNTS.num_r AND nom_r = '$punt'"
            val rs2 = st2.executeQuery(sentenciaSQLpunts)
            area.text = ""
            while (rs2.next()) {
                area.append(rs2.getString(1) + " ")
                area.append("( " + rs2.getFloat(2).toString() + ",")
                area.append(rs2.getFloat(3).toString() + " ) \n")
            }

            rs2.close()
            st2.close()
        }

        eixir.addActionListener() {
            // Sentències per a tancar la connexió i eixir
            rs.close()
            st.close()
            con.close()
        }
    }
}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        Finestra().isVisible = true
    }
}