package torres.river.misnotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.nota_layout.view.*
import java.io.File

class AdaptadorNotas : BaseAdapter {
    var context : Context
    var notas = ArrayList<Nota>()

    constructor(context: Context, notas : ArrayList<Nota>){
        this.context = context
        this.notas = notas
    }

    override fun getCount(): Int {
        return notas.size
    }

    override fun getItem(p0: Int): Any {
        return notas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflator = LayoutInflater.from(context)
        var view = inflator.inflate(R.layout.nota_layout, null)
        var note = notas[p0]

        view.tv_titulo_det.text = note.titulo
        view.tv_contenido_det.text = note.contenido

        view.btn_borrar.setOnClickListener {

            eliminar(note.titulo)
            notas.remove(note)
            this.notifyDataSetChanged()

        }

        return view

    }

    private fun eliminar(titulo: String){

        if (titulo == ""){
            Toast.makeText(context, "Error: título vacío", Toast.LENGTH_SHORT).show()
        }else{
            try {

                // Eliminar el archivo con el titulo seleccionado
                val archivo = File(ubicacion(), titulo + ".txt")
                archivo.delete()

                Toast.makeText(context, "Se eliminó el archivo", Toast.LENGTH_SHORT).show()

            }catch (e: Exception){
                Toast.makeText(context, "Error al eliminar el archivo", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun ubicacion(): String{

        val album = File(context?.getExternalFilesDir(null), "notas")
        if(!album.exists()){
            album.mkdir()
        }

        return album.absolutePath

    }

}