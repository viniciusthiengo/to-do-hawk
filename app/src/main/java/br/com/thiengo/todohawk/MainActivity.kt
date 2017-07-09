package br.com.thiengo.todohawk

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import br.com.thiengo.todohawk.data.Mock
import br.com.thiengo.todohawk.domain.ToDo
import br.com.thiengo.todohawk.fragment.TaskDialogFragment
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val list = ArrayList<ToDo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener{
            initTaskDialog()
        }

        initList()
        initRecycler()
    }

    private fun initList() {
        Hawk.init(this).build()
        if (Hawk.count() == 0L) {
            Hawk.put(ToDo.TO_DO_LIST_KEY, Mock().getToDoList())
        }

        list.addAll( Hawk.get(ToDo.TO_DO_LIST_KEY) )
    }

    private fun initRecycler() {
        rv_todo.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(this)
        rv_todo.layoutManager = mLayoutManager

        val divider = DividerItemDecoration(
                this,
                mLayoutManager.orientation)
        rv_todo.addItemDecoration(divider)

        val adapter = ToDoAdapter(this, list)
        rv_todo.adapter = adapter
    }

    private fun initTaskDialog(){
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        val fragAnterior = fm.findFragmentByTag(TaskDialogFragment.KEY)

        if (fragAnterior != null) {
            ft.remove(fragAnterior)
        }
        ft.addToBackStack(null)

        val dialog = TaskDialogFragment()
        dialog.show(ft, TaskDialogFragment.KEY)
    }

    fun addToRecycler( toDo: ToDo ){
        list.add( toDo )
        list.sortWith(compareBy<ToDo>{it.getDateTimeInSeconds()}.thenByDescending{it.priority}.thenBy{it.duration})
        Hawk.put(ToDo.TO_DO_LIST_KEY, list)
        rv_todo.adapter.notifyItemInserted( list.indexOf( toDo ) )
    }

    fun removeFromRecycler( position: Int ){
        list.removeAt( position )
        Hawk.put(ToDo.TO_DO_LIST_KEY, list)
        rv_todo.adapter.notifyItemRemoved( position )
    }

    fun isReclyclerAnimating() = rv_todo.isAnimating || rv_todo.isComputingLayout
}
