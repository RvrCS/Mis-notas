package torres.river.misnotas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nota_layout.view.*
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    var notas = ArrayList<Nota>()
    lateinit var adaptador: AdaptadorNotas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            var intent = Intent(this, AgregarNotaActivity::class.java)
            startActivityForResult(intent, 123)

        }

        leerNotas()

        adaptador = AdaptadorNotas(this, notas)
        listview.adapter = adaptador

    }

    fun notasTest(){

        notas.add(Nota("test 1", "content of note 1"))
        notas.add(Nota("test 2", "content of note 2"))
        notas.add(Nota("test 3", "content of note 3"))

    }

    fun leerNotas(){
        notas.clear()
        var carpeta = File(ubicacion().absolutePath)

        if (carpeta.exists()){
            var archivos = carpeta.listFiles()
            if (archivos != null){
                for (archivo in archivos){
                    leerArchivo(archivo)
                }
            }
        }


    }

    fun leerArchivo(archivo : File){
        val fis = FileInputStream(archivo)
        val di = DataInputStream(fis)
        val br = BufferedReader(InputStreamReader(di))
        var strLine: String? = br.readLine()
        var myData = ""

        // Lee archivos almacenados en memoria
        while (strLine != null){
            myData = myData + strLine
            strLine = br.readLine()
        }

        br.close()
        di.close()
        fis.close()

        // Elimina el .txt
        var nombre = archivo.name.substring(0, archivo.name.length-4)

        // Crea la nota y la agrega a la lista
        var nota = Nota(nombre, myData)
        notas.add(nota)

    }

    private fun ubicacion(): File{
        val folder = File(getExternalFilesDir(null), "notas")

        if(!folder.exists()){
            folder.mkdir()
        }

        return folder

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123){

            leerNotas()
            adaptador.notifyDataSetChanged()

        }

    }

}