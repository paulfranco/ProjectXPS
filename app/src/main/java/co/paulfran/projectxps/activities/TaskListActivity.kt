package co.paulfran.projectxps.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import co.paulfran.projectxps.R
import co.paulfran.projectxps.adapters.TaskListItemsAdapter
import co.paulfran.projectxps.firebase.FirestoreClass
import co.paulfran.projectxps.models.Board
import co.paulfran.projectxps.models.Task
import co.paulfran.projectxps.utils.Constants
import kotlinx.android.synthetic.main.activity_task_list.*

class TaskListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)


        var boardDocumentId = ""
        if (intent.hasExtra(Constants.DOCUMENT_ID)) {
            boardDocumentId = intent.getStringExtra(Constants.DOCUMENT_ID)!!
        }


        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getBoardDetails(this@TaskListActivity, boardDocumentId)
    }

    /**
     * A function to setup action bar
     */
    private fun setupActionBar(title: String) {

        setSupportActionBar(toolbar_task_list_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = title
        }

        toolbar_task_list_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to get the result of Board Detail.
     */
    fun boardDetails(board: Board) {

        hideProgressDialog()

        // Call the function to setup action bar.
        setupActionBar(board.name)

        // Here we are appending an item view for adding a list task list for the board.
        val addTaskList = Task(resources.getString(R.string.add_list))
        board.taskList.add(addTaskList)

        rv_task_list.layoutManager =
            LinearLayoutManager(this@TaskListActivity, LinearLayoutManager.HORIZONTAL, false)
        rv_task_list.setHasFixedSize(true)

        // Create an instance of TaskListItemsAdapter and pass the task list to it.
        val adapter = TaskListItemsAdapter(this@TaskListActivity, board.taskList)
        rv_task_list.adapter = adapter // Attach the adapter to the recyclerView.


    }
}
